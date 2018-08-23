package instigatemobile.com.cookmaster.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import instigatemobile.com.cookmaster.fragments.AboutFragment;
import instigatemobile.com.cookmaster.R;
import instigatemobile.com.cookmaster.fragments.SettingsFragment;

public class SettingsAndAboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_and_about);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (getIntent() == null) {
            return;
        }
        final int fragmentId = getIntent().getIntExtra(HomePageActivity.LOAD_FRAGMENT_KEY, 0);
        switch (fragmentId) {
            case R.id.action_settings:
                final SettingsFragment settingsFragment = new SettingsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
                break;
            case R.id.action_about:
                final AboutFragment aboutFragment = new AboutFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, aboutFragment).commit();
                break;
        }
    }
}
