package com.example.itc.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    final private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseUser user = mAuth.getCurrentUser();
        String userNameHome = user.getEmail();

        TextView textView = findViewById(R.id.userName_text);
        textView.setText(userNameHome);
        logoutButSetOnclick();
    }

    private void logoutButSetOnclick() {
        Button logOutBut = findViewById(R.id.log_out);
        logOutBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                final FragmentManager fm = getSupportFragmentManager();
                LoginFragment loginFrag = new LoginFragment();
                fm.beginTransaction().add(R.id.container, loginFrag).commit();
            }
        });
    }
}
