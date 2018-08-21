package com.instigatemobile.grapes.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.codekidlabs.storagechooser.Content;
import com.codekidlabs.storagechooser.StorageChooser;
import com.instigatemobile.grapes.R;
import com.instigatemobile.grapes.adapters.DataTransferAdapter;
import com.instigatemobile.grapes.models.DataTransfer;
import com.instigatemobile.grapes.util.ReadWriteJson;
import com.instigatemobile.grapes.util.Util;
import com.instigatemobile.grapes.adapters.MainFragmentsAdapter;
import com.instigatemobile.grapes.fragments.HomeFragment;
import com.instigatemobile.grapes.fragments.RemoteFragment;
import com.instigatemobile.grapes.models.FileModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public static final String KEY_DROPDOWN = "passId";
    private static final String TAG_JSON = "Read json";

    private RecyclerView mDatatransferRecyclerView;
    private DataTransferAdapter mDataTransferAdapter;
    private List<DataTransfer> mDatatransferList;
    private Button mBtnExit;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        fillList();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 199);
        }
        setDrawerLayout(getToolbar());
        setSearchView();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //TextView symb = navigationView.getHeaderView(0).findViewById(R.id.name_circle);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.ic_exit) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    prefs.edit().putString(Util.NICKNAME, null).apply();
                    startActivity(new Intent(MainActivity.this, LogInActivity.class));
                    finish();
                }
                return false;
            }
        });


        TextView symb = findViewById(R.id.name_circle);
        final TextView nickName = findViewById(R.id.nick_name);

        /*Button exitButton = findViewById(R.id.button_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                prefs.edit().putString(Util.NICKNAME, null);
                Util.mNickname = null;
                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });*/

        Util.mNickname = prefs.getString(Util.NICKNAME, null);
        if (Util.mNickname != null) {
            symb.setText(String.valueOf(Util.mNickname.charAt(0)));
            nickName.setText(String.valueOf(Util.mNickname));
        }

        final ViewPager viewPager = setViewPagersHomeAndRemote();
        setTabLayout(viewPager);


        mDatatransferRecyclerView = findViewById(R.id.datatransferRecyclerView);
        mDatatransferRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatatransferList = new ArrayList<>();
        mDatatransferList.add(new DataTransfer(true,"music-music","15mb/s",15));
        mDatatransferList.get(0).setProgress(60);
        mDatatransferList.add(new DataTransfer(true,"image","2 mb/s",15));
        mDatatransferList.add(new DataTransfer(false,"music","2 mb/s",90));
        mDatatransferList.add(new DataTransfer(true,"video","1 mb/s",55));
        mDatatransferList.add(new DataTransfer(false,"video","1 mb/s",69));
        mDatatransferList.add(new DataTransfer(false,"pfd_file","3 mb/s",40));
        mDatatransferList.add(new DataTransfer(true,"video1","2 mb/s",33));
        mDatatransferList.add(new DataTransfer(false,"image1","2 mb/s",12));
        mDatatransferList.add(new DataTransfer(false,"book","3 mb/s",88));
        mDatatransferList.add(new DataTransfer(false,"photo","1 mb/s",45));
        mDatatransferList.add(new DataTransfer(true,"video","3 mb/s",14));

        mDataTransferAdapter = new DataTransferAdapter(this,mDatatransferList);
        mDatatransferRecyclerView.setAdapter(mDataTransferAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent(this, DropdownActivity.class);
        intent.putExtra(KEY_DROPDOWN, id);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private Toolbar getToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void setDrawerLayout(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setTabLayout(ViewPager viewPager) {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_remote);
    }

    @NonNull
    private ViewPager setViewPagersHomeAndRemote() {
        final ViewPager viewPager = findViewById(R.id.pager);
        MainFragmentsAdapter adapter = new MainFragmentsAdapter(getSupportFragmentManager());
        HomeFragment homeFragment = new HomeFragment();
        Util.mBuilder = getBuilder();
        adapter.addFragment(homeFragment);
        adapter.addFragment(new RemoteFragment());
        viewPager.setAdapter(adapter);
        return viewPager;
    }


    private StorageChooser.Builder getBuilder() {
        Content c = new Content();
        c.setmCreateLabel("Create");
        c.setmInternalStorageText("My Storage");
        c.setmCancelLabel("Cancel");
        c.setmSelectLabel("Select");
        c.setmOverviewHeading("Choose Drive");
        StorageChooser.Builder builder = new StorageChooser.Builder();
        builder.withActivity(this)
                .withFragmentManager(getFragmentManager())
                .setMemoryBarHeight(1.5f)
                .disableMultiSelect()
                .withContent(c);
        return builder;
    }

    private void fillList() {
        ReadWriteJson read = new ReadWriteJson(this);
        String tmp = read.readJson();
        Util.mFileList.clear();
        JSONArray jsonArray;
        try {
            if (tmp.length() > 4) {
                JSONObject tmpObj = new JSONObject(tmp);
                jsonArray = tmpObj.getJSONArray("mFiles");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject tmpObject = jsonArray.getJSONObject(i);
                    Util.mFileList.add(new FileModel(tmpObject.getString(Util.SIZE),
                            tmpObject.getString(Util.ICON),
                            tmpObject.getString(Util.NAME),
                            tmpObject.getString(Util.PATH),
                            tmpObject.getString(Util.DATE),
                            tmpObject.getString(Util.EXTENSION)));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG_JSON, "Can not read from json file" + e.toString());
        }
        Util.mCurrentList = Util.mFileList;
        Util.mBeforeSearchList = Util.mCurrentList;
    }

    private void setSearchView() {
        SearchView search = findViewById(R.id.search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Util.Search(s);
                HomeFragment.updateList();
                return false;
            }
        });
    }
}