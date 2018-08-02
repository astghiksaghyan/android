package com.example.itc.testproject;


import android.content.Intent;
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
import java.util.concurrent.Executor;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    final private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private String username;
    private String pass;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        final EditText editTextUser = view.findViewById(R.id.li_userName);
        final EditText editTextPass = view.findViewById(R.id.li_password);
        LoginButSetOnclick(view, editTextUser, editTextPass);
        textViewSetOnclick(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void LoginButSetOnclick(View view, final EditText editTextUser, final EditText editTextPass) {
        Button logIn = view.findViewById(R.id.li_but);
        logIn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = String.valueOf(editTextUser.getText().toString());
                pass = String.valueOf(editTextPass.getText().toString());
                login(username, pass);
            }
        });
    }


    private void textViewSetOnclick(View view) {
        TextView textView = view.findViewById(R.id.goRegPage);
        textView.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentManager fm = getActivity().getSupportFragmentManager();
                RegisterFragment regFrag = new RegisterFragment();
                fm.beginTransaction().replace(R.id.container, regFrag).addToBackStack(null).commit();
            }
        });
    }

    private void login(final String userName, String password) {

        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getContext(), HomeActivity.class);
                            intent.putExtra(MainActivity.KEY, userName);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

}
