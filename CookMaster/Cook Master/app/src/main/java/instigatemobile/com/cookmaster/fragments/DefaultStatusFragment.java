package instigatemobile.com.cookmaster.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import instigatemobile.com.cookmaster.R;

public class DefaultStatusFragment extends Fragment implements View.OnClickListener {
    private TextView mFromDate, mToDate;
    private Button mFromBtn, mToBtn;
    private AppCompatButton mAcceptBtn;
    private Switch mDefaultSwitch;
    private LinearLayout mFromLayout;
    private LinearLayout mToLayout;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private DatabaseReference userRef;
    public static final String DEF_STATUS = "default_status";
    private static final String USERS = "users";
    private static final String RANGE_FROM = "range_from";
    private static final String RANGE_TO = "range_to";
    private static final String TAG = "tag";

    public DefaultStatusFragment() {
    }

    @SuppressLint("ValidFragment")
    public DefaultStatusFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.default_status_page_title));
        View view = inflater.inflate(R.layout.fragment_default_status, container, false);
        findElements(view);
        String currentUser = String.valueOf(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        userRef = FirebaseDatabase.getInstance().getReference().
                child(USERS).child(currentUser);
        getDateRange();
        getSwitchStatus();

        return view;
    }

    private void findElements(View view) {
        mFromDate = view.findViewById(R.id.from_date_tv);
        mToDate = view.findViewById(R.id.to_date_tv);
        mFromBtn = view.findViewById(R.id.from_date_btn);
        mToBtn = view.findViewById(R.id.to_date_btn);
        mAcceptBtn = view.findViewById(R.id.accept_btn);
        mDefaultSwitch = view.findViewById(R.id.default_switch);
        mFromLayout = view.findViewById(R.id.from_layout);
        mToLayout = view.findViewById(R.id.to_layout);
        mFromLayout.setOnClickListener(this);
        mToLayout.setOnClickListener(this);
        mFromBtn.setOnClickListener(this);
        mToBtn.setOnClickListener(this);
        mAcceptBtn.setOnClickListener(this);
    }

    private void getSwitchStatus() {
        userRef.child(DEF_STATUS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String defaultStatusValue = dataSnapshot.getValue(String.class);
                if (Objects.requireNonNull(defaultStatusValue).equals("-")) {
                    mDefaultSwitch.setChecked(false);
                } else if (defaultStatusValue.equals("+")) {
                    mDefaultSwitch.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getDateRange() {
        userRef.child(RANGE_FROM).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mFromDate.setText(String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userRef.child(RANGE_TO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mToDate.setText(String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.from_date_btn:
                chooseDate(mFromDate);
                break;
            case R.id.to_date_btn:
                chooseDate(mToDate);
                break;
            case R.id.accept_btn:
                acceptStatus();
                break;
            case R.id.from_layout:
                chooseDate(mFromDate);
                break;
            case R.id.to_layout:
                chooseDate(mToDate);
            default:
                break;
        }
    }

    private void acceptStatus() {
        if (dateIsValid()) {
            userRef.child(RANGE_FROM).setValue(mFromDate.getText());
            userRef.child(RANGE_TO).setValue(mToDate.getText());
            if(mDefaultSwitch.isChecked()) {
                userRef.child(DEF_STATUS).setValue("+");
            } else {
                userRef.child(DEF_STATUS).setValue("-");
            }
            Toast.makeText(getContext(), "Date set", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(getContext(), "Please select valid dates", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void chooseDate(final TextView textView) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, AlertDialog.THEME_HOLO_DARK,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        textView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private boolean dateIsValid() {
        String tmpFromDate = String.valueOf(mFromDate.getText());
        String tmpToDate = String.valueOf(mToDate.getText());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dateFrom = sdf.parse(tmpFromDate);
            Date dateTo = sdf.parse(tmpToDate);
            Long dateFromTime = dateFrom.getTime();
            Long dateToTime = dateTo.getTime();
            if (dateFromTime < dateToTime && System.currentTimeMillis() <= dateFromTime) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d(TAG, "exception");
        }
        return false;
    }
}
