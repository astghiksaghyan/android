package instigatemobile.com.cookmaster;

import android.app.Activity;
import android.content.res.Configuration;

import java.util.Locale;

public class Utils {
    public static void setLangRecreate(String languageCode, Activity activity) {
        if (activity == null) {
            return;
        }
        final Configuration config = activity.getBaseContext().getResources().getConfiguration();
        final Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        config.locale = locale;
        activity.getBaseContext().getResources().updateConfiguration(config,
                activity.getBaseContext().getResources().getDisplayMetrics());
        activity.recreate();
    }

}
