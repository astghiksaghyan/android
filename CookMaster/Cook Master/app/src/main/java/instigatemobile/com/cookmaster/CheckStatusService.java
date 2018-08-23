package instigatemobile.com.cookmaster;


import android.app.PendingIntent;
import android.app.AlarmManager;
import android.content.Intent;
import android.app.Service;
import android.os.IBinder;
import java.util.Calendar;

public class CheckStatusService extends Service {

    public CheckStatusService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0,
                new Intent(getApplicationContext(), GetFirebaseDateBroadcast.class), PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}