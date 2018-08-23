package instigatemobile.com.cookmaster.fragments;

import android.os.Bundle;
import android.view.View;
import java.util.Calendar;
import java.util.Objects;
import android.widget.Toast;
import android.widget.Button;
import android.text.TextUtils;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.EditText;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import instigatemobile.com.cookmaster.R;
import com.google.android.gms.tasks.Task;
import android.content.SharedPreferences;
import com.google.firebase.auth.AuthResult;
import android.preference.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import instigatemobile.com.cookmaster.CheckStatusService;
import instigatemobile.com.cookmaster.activities.HomePageActivity;
import android.support.annotation.NonNull;

public class LoginFragment extends Fragment  implements  View.OnClickListener {
    public static final String PREF_UNAME = "Username";
    public static final String PREF_PASSWORD = "Password";
    public static final String USER_KEY = "user_key";
    private FirebaseAuth mAuth;
    public static String mUnameValue;
    private String mPasswordValue;
    private EditText mEmailText;
    private EditText mPasswordText;
    private Button mSigninButton;
    private Button mForgotButton;
    public static boolean isLoginPressed = true;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        initElements(view);
        setOnClickListeners();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || !isLoginPressed) {
            mEmailText.setText("");
            mPasswordText.setText("");
            return;
        }
//        login(email,password); //TODO uncomment this
    }

    @Override
    public void onClick(View view) {
        int btn_id = view.getId();
        switch (btn_id) {
            case R.id.forgot_pass_btn:
                goForgotPasswordPage();
                break;
            case R.id.btn_login:
                login(mEmailText.getText().toString(), mPasswordText.getText().toString());
        }

    }

    public void saveUserIdPref() {
        SharedPreferences userPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor userEditor = userPref.edit();
        userEditor.putString(USER_KEY, String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid()));
        userEditor.apply();
    }

    private void savePreferences() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = settings.edit();
        mUnameValue = mEmailText.getText().toString();
        mPasswordValue = mPasswordText.getText().toString();
        editor.putString(PREF_UNAME, mUnameValue);
        editor.putString(PREF_PASSWORD, mPasswordValue);
        editor.apply();
    }

    private void loadPreferences() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String defaultUnameValue = "";
        mUnameValue = settings.getString(PREF_UNAME, defaultUnameValue);
        String defaultPasswordValue = "";
        mPasswordValue = settings.getString(PREF_PASSWORD, defaultPasswordValue);
        mEmailText.setText(mUnameValue);
        mPasswordText.setText(mPasswordValue);
    }

    private void initElements(View view) {
        mEmailText = view.findViewById(R.id.input_email);
        mPasswordText = view.findViewById(R.id.input_password);
        mSigninButton = view.findViewById(R.id.btn_login);
        mForgotButton = view.findViewById(R.id.forgot_pass_btn);
    }

    private void setOnClickListeners() {
        mSigninButton.setOnClickListener(this);
        mForgotButton.setOnClickListener(this);
    }

    private void goForgotPasswordPage() {
        ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.m_container, forgotPasswordFragment);
        transaction.addToBackStack("LOGIN_FRAGMENT");
        transaction.commit();
    }

    public void login(String email, String password) {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        mSigninButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_DARK);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
//                    creatUserInDatabase();
                    saveUserIdPref();
                    Objects.requireNonNull(getActivity()).startService(new Intent(getContext(),CheckStatusService.class));
                    Intent intent = new Intent(getActivity(), HomePageActivity.class);
                    startActivity(intent);
                } else {
                    onLoginFailed();
                }
            }
        });
    }

    public void onLoginSuccess() {
        mSigninButton.setEnabled(true);
//        SharedPreferences rememberPass = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        SharedPreferences.Editor editor = rememberPass.edit();
//        editor.putString("", "");
//        editor.apply();
    }

    public void onLoginFailed() {
        Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_LONG).show();
        mSigninButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailText.setError("enter a valid email address");
            valid = false;
        } else {
            mEmailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 15) {
            mPasswordText.setError("between 6 and 15 alphanumeric characters");
            valid = false;
        } else {
            mPasswordText.setError(null);
        }
        return valid;
    }

    private void creatUserInDatabase() {
        String userID = String.valueOf(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatabaseReference mDbRef = FirebaseDatabase.getInstance().getReference();
        String tmpDateFrom = mDay + "/" + (mMonth + 1) + "/" + mYear;
        String tmpDateTo = mDay + "/" + (mMonth + 1) + "/" + (mYear + 2);
        mDbRef.child("users").child(userID).child("default_status").setValue("-");
        mDbRef.child("users").child(userID).child("user_name").setValue("Name Surname");
        mDbRef.child("users").child(userID).child("current owed").setValue("0");
        mDbRef.child("users").child(userID).child("range_from").setValue(tmpDateFrom);
        mDbRef.child("users").child(userID).child("range_to").setValue(tmpDateTo);
    }
}

