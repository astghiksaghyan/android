package instigatemobile.com.cookmaster.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.RemoteMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import instigatemobile.com.cookmaster.R;
import instigatemobile.com.cookmaster.activities.HomePageActivity;

public class FirebaseMessagingServiceClass extends com.google.firebase.messaging.FirebaseMessagingService  {
    private static final int REQUEST_CODE=0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        String title = getTitle(remoteMessage);
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.cook_master_launcher)
                .setContentTitle(title)
                .setContentText(Objects.requireNonNull(remoteMessage.getNotification()).getBody()).setAutoCancel(true).setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            Objects.requireNonNull(manager).createNotificationChannel(channel);
        }
        int id = getId();
        Objects.requireNonNull(manager).notify(id, builder.build());
    }

    private String getTitle(RemoteMessage remoteMessage) {
        String title = "";
        if (Objects.requireNonNull(remoteMessage.getNotification()).getTitle() != null){
            title = remoteMessage.getNotification().getTitle();
        }
        return title;
    }

    private int getId() {
        Date now = new Date();
        return Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));
    }
}