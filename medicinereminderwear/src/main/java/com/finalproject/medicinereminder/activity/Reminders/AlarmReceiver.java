package com.finalproject.medicinereminder.activity.Reminders;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import androidx.core.app.NotificationCompat;

import android.content.pm.PackageManager;
import android.os.Build;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "MedicineReminderChannel";

    @Override
    public void onReceive(Context context, Intent intent) {
        String medicineName = intent.getStringExtra("medicine_name");
        System.out.println("Medicine Reminder: " + medicineName);
        // Create notification
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Permission not granted. You can log, notify, or start an activity to request permission.
                return;
            }
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Medicine Reminder",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Medicine Reminder")
                .setContentText("Time to take your medicine: " + medicineName)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationManager.notify(medicineName.hashCode(), notificationBuilder.build());

        // Add wearable-specific extensions
        NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender()
                .setContentAction(0);
        notificationBuilder.extend(wearableExtender);


    }
}
