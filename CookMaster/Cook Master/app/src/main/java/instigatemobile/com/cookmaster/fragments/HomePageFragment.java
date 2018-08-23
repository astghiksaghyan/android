package instigatemobile.com.cookmaster.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.TimeZone;

import android.view.ViewGroup;
import android.content.Context;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import android.support.v7.widget.GridLayoutManager;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.annotation.SuppressLint;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import instigatemobile.com.cookmaster.GetFirebaseDateBroadcast;
import instigatemobile.com.cookmaster.adapters.AdapterOfTodayMenu;
import instigatemobile.com.cookmaster.R;
import instigatemobile.com.cookmaster.models.TodayMenuItem;

public class HomePageFragment extends Fragment {
    private final List<TodayMenuItem> todayMenuItems = new ArrayList<>();
    private Context mContext;
    private DatabaseReference refTodayMenu = FirebaseDatabase.getInstance().getReference();
    private static final String MENU = "menu";
    private static final String TODAY_MENU = "todayMenu";
    private TextView mTotalExpVal;
    private TextView mDailyExpVal;
    public static Switch mDailySwitch;
    private RecyclerView recyclerView;

    public HomePageFragment() {
    }

    @SuppressLint("ValidFragment")
    public HomePageFragment(Context context) {
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        findElements(view);
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().
                child("users").child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        showMeals();
        checkDailyStatus(userRef);
        getTotalExp(userRef);
        getDailyExp(userRef);
        mDailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    userRef.child("daily_status").setValue("+");
                    getDailyExp(userRef);
                } else {
                    userRef.child("daily_status").setValue("-");
                    mDailyExpVal.setText("0 AMD");
                }
            }
        });
        switchActivated();
        return view;
    }

    private void showMeals() {
        refTodayMenu.child(MENU).child(TODAY_MENU).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                todayMenuItems.clear();
                for(DataSnapshot i: dataSnapshot.getChildren()) {
                    TodayMenuItem todayMenuItem = i.getValue(TodayMenuItem.class);
                    todayMenuItems.add(todayMenuItem);
                }
                AdapterOfTodayMenu adapter = new AdapterOfTodayMenu(todayMenuItems, mContext);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void findElements(View view) {
        recyclerView = view.findViewById(R.id.home_page_rv);
        mTotalExpVal = view.findViewById(R.id.total_exp_value);
        mDailyExpVal = view.findViewById(R.id.daily_exp_value);
        mDailySwitch = view.findViewById(R.id.switch_default);
    }

    private void getDailyExp(DatabaseReference ref) {
        ref.child("daily owed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(mDailySwitch.isChecked()) {
                    mDailyExpVal.setText(String.valueOf(dataSnapshot.getValue() + " AMD"));
                } else {
                    mDailyExpVal.setText("0 AMD");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getTotalExp(DatabaseReference ref) {
        ref.child("current owed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mTotalExpVal.setText(String.valueOf(dataSnapshot.getValue()+ " AMD"));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void checkDailyStatus(final DatabaseReference ref) {
        ref.child("daily_status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String mDefStatus = String.valueOf(dataSnapshot.getValue());
                if(mDefStatus.equals("+")) {
                    mDailySwitch.setChecked(true);
                }else if(mDefStatus.equals("-")){
                    mDailySwitch.setChecked(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void switchActivated() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mHformat = new SimpleDateFormat("HH");
        Integer mHour = Integer.valueOf(mHformat.format(calendar.getTime()));
        if(mHour >= 9 && mHour < 11) {
            mDailySwitch.setClickable(true);
        } else {
            mDailySwitch.setClickable(false);
        }
    }
}