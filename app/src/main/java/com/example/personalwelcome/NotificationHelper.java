package com.example.personalwelcome;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class NotificationHelper {
    private static final String CHANNEL_ID_LOW = "low_importance_channel";
    private static final String CHANNEL_ID_DEFAULT = "default_importance_channel";
    private static final String CHANNEL_ID_HIGH = "high_importance_channel";
    private static final String[] channelArray = {CHANNEL_ID_LOW,CHANNEL_ID_DEFAULT,CHANNEL_ID_HIGH};
    private static final String CHANNEL_NAME = "Cos";

    public static void creatNotificationChannels(AppCompatActivity activity){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            NotificationManager notificationManager = activity.getSystemService(NotificationManager.class);
            NotificationChannel channellow = new NotificationChannel(CHANNEL_ID_LOW, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            NotificationChannel channeldefault = new NotificationChannel(CHANNEL_ID_DEFAULT, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channelhigh = new NotificationChannel(CHANNEL_ID_HIGH, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channellow);
                notificationManager.createNotificationChannel(channeldefault);
                notificationManager.createNotificationChannel(channelhigh);
            }
        }
    }

    public static void sendNotification(int NOTIFICATION_ID, int CHANNEL_ID, AppCompatActivity activity, String title, String message, int styleType, int largeIconId){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
                return;
            }
        }
        NotificationCompat.Builder builder  = new NotificationCompat.Builder(activity, channelArray[CHANNEL_ID])
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

        switch (styleType) {
            case 1:
                builder.setContentText(message);
                break;
            case 2:
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                break;
            case 3:
                Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), largeIconId);
                builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
                break;
            default:
                break;
        }
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(activity);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}