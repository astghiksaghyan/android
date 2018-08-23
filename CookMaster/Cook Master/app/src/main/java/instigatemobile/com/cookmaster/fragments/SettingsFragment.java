package instigatemobile.com.cookmaster.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.haipq.android.flagkit.FlagImageView;

import java.util.Objects;

import instigatemobile.com.cookmaster.R;
import instigatemobile.com.cookmaster.Utils;


public class SettingsFragment extends Fragment implements View.OnClickListener {
    public static final String PREFS_NAME = "langage_shared_pref";
    public static final String LANGUAGE_KEY = "language_key";

    private static final String ARM_CODE = "hy";
    private static final String US_CODE = "us";
    private static final String AM_COUNTRY_CODE = "AM";
    private static final String US_COUNTRY_CODE = "US";

    private  static boolean defaultEng = true;
    private SharedPreferences sharedPreferences;


    public SettingsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.settings_page_title));
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        final LinearLayout changePassword = view.findViewById(R.id.change_password);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() == null) {
                    return;
                }
                final ChangePasswordFragment settingsFragment = new ChangePasswordFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.container, settingsFragment).commit();
                transaction.addToBackStack("SettingsFragment");
            }
        });

        FlagImageView flag = view.findViewById(R.id.flag_view);

        if (defaultEng) {
            flag.setCountryCode(US_COUNTRY_CODE);
            flag.setImageResource(R.drawable.flag_us);
        } else {
            flag.setCountryCode(AM_COUNTRY_CODE);
            flag.setImageResource(R.drawable.flag_am);
        }

        flag.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        if (defaultEng) {
            defaultEng = false;
            Utils.setLangRecreate(ARM_CODE, getActivity());
            sharedPreferences.edit().putString(LANGUAGE_KEY, ARM_CODE).apply();
        } else {
            defaultEng = true;
            Utils.setLangRecreate(US_CODE, getActivity());
            sharedPreferences.edit().putString(LANGUAGE_KEY, US_CODE).apply();
        }
    }
}
