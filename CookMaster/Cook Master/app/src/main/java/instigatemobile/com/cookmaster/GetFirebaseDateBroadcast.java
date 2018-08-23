package instigatemobile.com.cookmaster;

import java.util.Date;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;
import android.content.Context;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import instigatemobile.com.cookmaster.fragments.LoginFragment;
import android.support.annotation.NonNull;
import android.annotation.SuppressLint;

public class GetFirebaseDateBroadcast extends BroadcastReceiver {
    public static final String DEF_STATUS = "default_status";
    private static final String DAY_STATUS = "daily_status";
    private static final String RANGE_FROM = "range_from";
    private static final String RANGE_TO = "range_to";
    private static final String USERS = "users";
    private static final String TAG = "tag";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences userPref = PreferenceManager.getDefaultSharedPreferences(context);
        String tmpStr = userPref.getString(LoginFragment.USER_KEY, null);
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().
                child(USERS).child(tmpStr);
//        if(dateIsValid(userRef, context)) {
            checkDefStatus(userRef);
//        } else {
//        userRef.child(DAY_STATUS).setValue("-");
//        }
    }
    private void checkDefStatus(final DatabaseReference userRef) {
        userRef.child(DEF_STATUS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String tmpValue = String.valueOf(dataSnapshot.getValue());
                if (tmpValue.equals("+")) {
                    userRef.child(DAY_STATUS).setValue("+");
                } else if (tmpValue.equals("-")) {
                    userRef.child(DAY_STATUS).setValue("-");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

//    public boolean dateIsValid(DatabaseReference dbRef, final Context context) {
//        final String[] tmpDate = new String[2];
//        dbRef.child(RANGE_FROM).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                tmpDate[0] = String.valueOf(dataSnapshot.getValue());
//                Toast.makeText(context, tmpDate[0], Toast.LENGTH_LONG);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        dbRef.child(RANGE_TO).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                tmpDate[1] = String.valueOf(dataSnapshot.getValue());
//                Toast.makeText(context, tmpDate[1], Toast.LENGTH_LONG);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            dbRef.wait();
//            Date dateFrom = sdf.parse(tmpDate[0]);
//            Date dateTo = sdf.parse(tmpDate[1]);
//            Long dateFromTime = dateFrom.getTime();
//            Long dateToTime = dateTo.getTime();
//            if (dateFromTime < System.currentTimeMillis() && dateToTime > System.currentTimeMillis()) {
//                return true;
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//            Log.d(TAG, "exception");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}