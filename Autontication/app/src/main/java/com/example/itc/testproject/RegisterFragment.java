package com.example.itc.testproject;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    final private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String usernameReg;
    private String passReg;



    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        regButSetOnclick(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void regButSetOnclick(View view) {
        final EditText editTextUser = view.findViewById(R.id.reg_userName);
        final EditText editTextPass = view.findViewById(R.id.reg_password);
        Button register_but = view.findViewById(R.id.reg_but);
        register_but.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameReg = String.valueOf(editTextUser.getText().toString());
                passReg = String.valueOf(editTextPass.getText().toString());
                register(usernameReg, passReg);
            }
        });
    }

    private void register(String userName, String password) {

        mAuth.createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

}
