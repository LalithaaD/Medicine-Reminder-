package com.finalproject.medicinereminder.activity.Reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmHelper {

    public static void setAlarm(Context context, String medicineName, String time) {
        try {
            // Parse the time (format: HH:mm)
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(timeFormat.parse(time));
            System.out.println("Medicine Reminder: set alarm " + medicineName);
            // If the time is in the past, schedule for the next day
            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            // Create an intent to trigger the BroadcastReceiver
            Intent intent = new Intent(context, AlarmReceiver.class);
            intent.putExtra("medicine_name", medicineName);

            // Create a PendingIntent
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    medicineName.hashCode(), // Unique ID for each medicine
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            System.out.println("Medicine Reminder: " + medicineName);
            // Set the alarm using AlarmManager
            AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
