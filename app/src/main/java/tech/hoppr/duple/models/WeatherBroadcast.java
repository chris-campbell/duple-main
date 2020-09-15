package tech.hoppr.duple.models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import tech.hoppr.duple.R;

public class WeatherBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int num = intent.getIntExtra("TEMP", 25);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "weatherunits")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("MaskSinger")
                .setContentText(String.valueOf(num))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());
    }
}