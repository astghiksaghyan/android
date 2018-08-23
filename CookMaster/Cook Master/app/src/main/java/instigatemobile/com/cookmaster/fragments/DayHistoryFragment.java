package instigatemobile.com.cookmaster.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import instigatemobile.com.cookmaster.R;

public class DayHistoryFragment extends Fragment {
    private List<String> mDaysList = new ArrayList<>();
    private List<String> mExpensesList = new ArrayList<>();
    private ListView mDaysListView = null;
    private ListView mExpensesListView = null;
    private TextView mTotalExpensView = null;
    private Double totalExpens = 0.0;

    public DayHistoryFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.history_page_title));

        View view = inflater.inflate(R.layout.fragment_day_history, container, false);

        Bundle bundle = this.getArguments();
        String month = null;
        if (bundle != null) {
            month = bundle.getString(MonthHistoryFragment.KEY);
        }
        findListViews(view);
        final ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1, mDaysList);
        final ArrayAdapter<String> expensAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mExpensesList);
        final DatabaseReference mDayReferens = FirebaseDatabase.getInstance().getReference();
        assert month != null;
        final DatabaseReference referenceToMonth = mDayReferens.child(getString(R.string.history_str)).child(month);

        referenceToMonth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    mDaysList.add(item.getKey());
                    mDaysListView.setAdapter(dayAdapter);
                }
                for (final String day : mDaysList) {
                        referenceToMonth.child(day).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String dailyExpens = (String) Objects.requireNonNull(dataSnapshot.child(getString(R.string.user_expens_str)).getValue());
                                mExpensesList.add(dailyExpens);
                                mExpensesListView.setAdapter(expensAdapter);
                                totalExpens += Double.parseDouble(dailyExpens);
                                //System.out.println("mDaysList.get(mDaysList.size()) ========== " + mDaysList.get(mDaysList.size()));
//                                if (day.equals(mDaysList.get(mDaysList.size()))) {
                                    mTotalExpensView.setText(String.valueOf(totalExpens));
//                                    System.out.println("1 = " + 1);
//                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return view;
    }

    private void findListViews(View view) {
        mDaysListView = view.findViewById(R.id.days_list);
        mExpensesListView = view.findViewById(R.id.expenses_list);
        mTotalExpensView = view.findViewById(R.id.total_expens);
    }
}
