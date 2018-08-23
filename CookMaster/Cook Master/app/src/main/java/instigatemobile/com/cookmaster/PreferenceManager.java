package instigatemobile.com.cookmaster;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public PreferenceManager(Context mContext) {
        this.mContext = mContext;
        getSharedPreference();
    }

    private void getSharedPreference() {
        mSharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.my_preference), Context.MODE_PRIVATE);
    }

    public void writePrefernce() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(mContext.getString(R.string.my_preference_key), "INIT_OK");
        editor.apply();
    }

    public boolean checkPreference() {
        return !mSharedPreferences.getString(mContext.getString(R.string.my_preference_key), "null").equals("null");
    }
}
