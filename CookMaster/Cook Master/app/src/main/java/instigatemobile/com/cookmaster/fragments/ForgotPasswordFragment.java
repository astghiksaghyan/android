package instigatemobile.com.cookmaster.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import instigatemobile.com.cookmaster.R;

public class ForgotPasswordFragment extends Fragment {

    private EditText mPasswordEmail;
    private FirebaseAuth mFirebaseAuth;
    public ForgotPasswordFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        resetPass(view);
        return view;
    }

    private void resetPass(View view) {
        mPasswordEmail = view.findViewById(R.id.etPasswordEmail);
        final Button resetPassword = view.findViewById(R.id.btnPasswordReset);
        mFirebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userEmail =  mPasswordEmail.getText().toString().trim();
                final ProgressDialog progressDialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_DARK);

                if (TextUtils.isEmpty(userEmail) || !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                    Toast.makeText(getContext(), R.string.enter_email,Toast.LENGTH_LONG).show();
                } else {
                    showProgressDialog(progressDialog);
                    onCompleteListenerFireAuth(userEmail);
                }
            }
        });
    }

    private void onCompleteListenerFireAuth(String userEmail) {
        mFirebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.email_sent,Toast.LENGTH_LONG).show();
                    goLoginPage();
                } else {
                    Toast.makeText(getContext(), R.string.error,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showProgressDialog(final ProgressDialog progressDialog) {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle(getString(R.string.please_wait));
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 1000);
    }

    private void goLoginPage() {
        LoginFragment loginFragment = new LoginFragment();
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.m_container, loginFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
