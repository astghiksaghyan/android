package instigatemobile.com.cookmaster.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import instigatemobile.com.cookmaster.R;

public class ChangePasswordFragment extends Fragment implements View.OnClickListener {
    private EditText oldPassword;
    private EditText newPassword;
    private EditText comfirmedNewPassword;


    public ChangePasswordFragment() {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(getString(R.string.change_password_page_title));

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        oldPassword = view.findViewById(R.id.old_password);
        newPassword = view.findViewById(R.id.new_password);
        comfirmedNewPassword = view.findViewById(R.id.confirm_password);

        AppCompatButton saveBtn = view.findViewById(R.id.btn_change);

        saveBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String oldPass = String.valueOf(oldPassword.getText());
        final String newPass = String.valueOf(newPassword.getText());
        final String confirmedNewPass = String.valueOf(comfirmedNewPassword.getText());

        if (!newPass.equals(confirmedNewPass)) {
            Toast.makeText(getActivity(), R.string.message_for_defferent_new_and_comfirmed_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        if(oldPass.isEmpty() || newPass.isEmpty() || confirmedNewPass.isEmpty()) {
            Toast.makeText(getActivity(), "Fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        AuthCredential credential = EmailAuthProvider
                .getCredential(LoginFragment.mUnameValue, oldPass);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                oldPassword.setText("");
                                newPassword.setText("");
                                comfirmedNewPassword.setText("");
                                Toast.makeText(getActivity(), R.string.message_for_password_updated, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), R.string.message_for_password_not_updated, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), R.string.message_for_error_auth_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
