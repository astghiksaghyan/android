package instigatemobile.com.cookmaster.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import instigatemobile.com.cookmaster.fragments.LoginFragment;
import instigatemobile.com.cookmaster.R;

public class LoginForgotPasswordActivity extends AppCompatActivity{
//    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forgot_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        mAuth=FirebaseAuth.getInstance();
//        if(mAuth.getCurrentUser() != null) {
//            Intent intent = new Intent(this, HomePageActivity.class);
//            startActivity(intent);
//        } else {
        final LoginFragment loginFragment = new LoginFragment();
        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.m_container, loginFragment);
        transaction.commit();
//        }
    }
}
