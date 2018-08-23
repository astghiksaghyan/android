package instigatemobile.com.cookmaster.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import instigatemobile.com.cookmaster.Utils;
import instigatemobile.com.cookmaster.CheckStatusService;
import instigatemobile.com.cookmaster.fragments.CurrentConsultantsFragment;
import instigatemobile.com.cookmaster.fragments.DefaultStatusFragment;
import instigatemobile.com.cookmaster.fragments.HomePageFragment;
import instigatemobile.com.cookmaster.R;
import instigatemobile.com.cookmaster.fragments.LoginFragment;
import instigatemobile.com.cookmaster.fragments.MonthHistoryFragment;
import instigatemobile.com.cookmaster.fragments.PaymentFragment;
import instigatemobile.com.cookmaster.fragments.SettingsFragment;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String LOAD_FRAGMENT_KEY =
            "instigatemobile.com.cookmaster.activities.HomePageActivity.LOAD_FRAGMENT_KEY";
    private final String IMAGE = "image/*";
    TextView nameSurname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final SharedPreferences prefs = getSharedPreferences(SettingsFragment.LANGUAGE_KEY, MODE_PRIVATE);
        final String language = prefs.getString(SettingsFragment.PREFS_NAME, null);
        final HomePageFragment homeFragment = new HomePageFragment(this);
        goToFragment(homeFragment);

        if (language != null) {
            Utils.setLangRecreate(language, this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                assert data != null;
                final Uri imageUri = data.getData();
                assert imageUri != null;
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ImageView image_view = findViewById(R.id.userImage);
                image_view.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.message_you_have_not_picked_image, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        changeUsername();
        chooseImageFromGallery();
        getUsername();

        return true;
    }

    private void chooseImageFromGallery() {
        final ImageView userImage = findViewById(R.id.userImage);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType(IMAGE);
                startActivityForResult(photoPickerIntent, 1);
            }
        });
    }

    private void getUsername() {
        nameSurname = findViewById(R.id.nameSurname);
        final String user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        final DatabaseReference usernameReference = FirebaseDatabase.getInstance().getReference().
                child(getString(R.string.users_str)).child(user).child(getString(R.string.name_surname_str));

        usernameReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameSurname.setText(String.valueOf(dataSnapshot.getValue()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void changeUsername() {
        final ImageButton pencilBtn = findViewById(R.id.pencilButton);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        final EditText editNameUsername = new EditText(this);

        editNameUsername.setTypeface(null, Typeface.BOLD);
        dialog.setTitle(getString(R.string.change_user_name));

        dialog.setView(editNameUsername);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.saveBtnText), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (isValidUsername(editNameUsername)) {
                    nameSurname.setText(editNameUsername.getText());
                }
            }
        });
        pencilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNameUsername.setText(nameSurname.getText());
                dialog.show();
            }
        });
    }

    private boolean isValidUsername(EditText username) {
        if (TextUtils.isEmpty(username.getText())) {
            Toast.makeText(HomePageActivity.this, R.string.messageForEmptyUsername, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!String.valueOf(username.getText()).matches(getString(R.string.reg_exp_for_name))) {
            Toast.makeText(HomePageActivity.this, R.string.message_name_can_not_conteins_symbol,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        final Intent intent = new Intent(this, SettingsAndAboutActivity.class);

        switch (id) {
            case R.id.action_settings:
                intent.putExtra(LOAD_FRAGMENT_KEY, id);
                startActivity(intent);
                return true;
            case R.id.action_about:
                intent.putExtra(LOAD_FRAGMENT_KEY, id);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                final HomePageFragment homeFragment = new HomePageFragment(this);
                goToFragment(homeFragment);
                break;
            case R.id.nav_consultant:
                final CurrentConsultantsFragment consultantsFragment = new CurrentConsultantsFragment(this);
                goToFragment(consultantsFragment);
                break;

            case R.id.nav_status:
                final DefaultStatusFragment defaultStatusFragment = new DefaultStatusFragment(this);
                goToFragment(defaultStatusFragment);
                break;

            case R.id.nav_payment:
                final PaymentFragment paymentFragment = new PaymentFragment();
                goToFragment(paymentFragment);
                break;

            case R.id.nav_history:
                final MonthHistoryFragment monthHistoryFragment = new MonthHistoryFragment();
                goToFragment(monthHistoryFragment);
                break;

            case R.id.nav_logOut:
                LoginFragment.isLoginPressed = false;
                stopService(new Intent(this,CheckStatusService.class));
                Intent intent = new Intent(HomePageActivity.this, LoginForgotPasswordActivity.class);
                startActivity(intent);
                break;
        }

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goToFragment(Fragment fragment) {
        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment).commit();
        transaction.addToBackStack(getString(R.string.home_page_activity_name));
    }
}
