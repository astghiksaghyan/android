package instigatemobile.com.cookmaster.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import instigatemobile.com.cookmaster.adapters.MPagerAdapter;
import instigatemobile.com.cookmaster.PreferenceManager;
import instigatemobile.com.cookmaster.R;


public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private int[] mLayouts = {R.layout.first_slide, R.layout.second_slide, R.layout.third_slide};
    private Button mSkipBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (new PreferenceManager(this).checkPreference()) {
            loadHome();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_welcome);
        mSkipBtn = findViewById(R.id.welcome_activity_skipBtn);
        final ViewPager mPager = findViewById(R.id.viewPager);
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final MPagerAdapter mPagerAdapter = new MPagerAdapter(mLayouts, this);
        mPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(mPager, true);
        mSkipBtn.setOnClickListener(this);
        pagerClickListener(mPager);
    }

    private void pagerClickListener(ViewPager mPager) {
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mLayouts.length - 1) {
                    mSkipBtn.setText(R.string.start);
                } else {
                    mSkipBtn.setText(R.string.welcome_activity_skip);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        loadHome();
        new PreferenceManager(this).writePrefernce();
    }

    private void loadHome() {
        startActivity(new Intent(this, LoginForgotPasswordActivity.class));
    }
}
