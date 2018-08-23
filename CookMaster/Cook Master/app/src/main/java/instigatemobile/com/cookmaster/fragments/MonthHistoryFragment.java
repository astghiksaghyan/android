package instigatemobile.com.cookmaster.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import instigatemobile.com.cookmaster.R;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;
import static com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences;

public class MonthHistoryFragment extends Fragment {
    private List<String> mMonthsList = new ArrayList<>();
    public static final String KEY = "key";

    public MonthHistoryFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.history_page_title));

        View view = inflater.inflate(R.layout.fragment_month_history, container, false);

        final ListView monthList = view.findViewById(R.id.list_view);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_list_item_1, mMonthsList);

        DatabaseReference mMonthReferens = FirebaseDatabase.getInstance().getReference();

        mMonthReferens.child(getString(R.string.history_str)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMonthsList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    mMonthsList.add(getString(getStringIdentifier(item.getKey())));
                    monthList.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        clickListItem(monthList);

        return view;
    }

    private int getStringIdentifier(String name) {
        return getResources().getIdentifier(name, "string", getContext().getPackageName());
    }

    void  clickListItem(final ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                createDayHistoryFragment(i);
            }
        });
    }

    private void createDayHistoryFragment(int position) {
        DayHistoryFragment dayHistoryFragment = new DayHistoryFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(getString(R.string.month_history_fragment));
        Bundle bundle = new Bundle();
        bundle.putString(KEY, mMonthsList.get(position));
        dayHistoryFragment.setArguments(bundle);
        transaction.replace(R.id.container, dayHistoryFragment).commit();
    }
}
