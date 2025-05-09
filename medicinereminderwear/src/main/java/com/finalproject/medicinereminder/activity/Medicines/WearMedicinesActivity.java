package com.finalproject.medicinereminder.activity.Medicines;

import static com.finalproject.medicinereminder.MedicineReminderUtility.CHANNEL_ID;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.medicinereminder.MedicineReminderUtility;
import com.finalproject.medicinereminder.Models.MedicinesModel;
import com.finalproject.medicinereminder.NotificationReceiver;
import com.finalproject.medicinereminder.adapters.MedicineRecyclerViewAdapter;
import com.finalproject.medicinereminder.databinding.ActivityWearMedicinesBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WearMedicinesActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityWearMedicinesBinding medicinesBinding;

    //Data management
    MedicinesModel medicineModel;
    List<MedicinesModel> medicineList = new ArrayList<>();
    MedicineRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Retrieve the existing medicine list
        getAllMedicines();

        medicinesBinding=ActivityWearMedicinesBinding.inflate(getLayoutInflater());

        //RecyclerView adapter
        adapter=new MedicineRecyclerViewAdapter(this,medicineList);
        recyclerView=medicinesBinding.wearMedRv;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setScrollContainer(true);
        recyclerView.setAdapter(adapter);

        //set content view
        setContentView(medicinesBinding.getRoot());
        init();
    }
    public void init()
    {
        medicinesBinding.wearMedBtnAdd.setOnClickListener(this);

    }

    public void setNotification()
    {

       for(MedicinesModel medicine:medicineList)
       {
           scheduleNotification(medicine.getMedicineName(),medicine.getRemindertime());
       }

    }

    // Retrieve the existing medicine list
    public void getAllMedicines()
    {
        Context context=getApplicationContext();
        medicineList = MedicineReminderUtility.getMedicineList(context);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==medicinesBinding.wearMedBtnAdd.getId()){
            Intent intent=new Intent(this, WearAddMedicineActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
            String channelName = MedicineReminderUtility.CHANNEL_NAME;
            String channelDescription = MedicineReminderUtility.CHANNEL_DESCRIPTION;
            //builtin constant that sets the priority of the notification
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,channelName,importance);
            channel.setDescription(channelDescription);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void scheduleNotification(String medicationName, String time) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        String[] timeParts = time.split(":");


        Calendar medicationTime = Calendar.getInstance();
        medicationTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
        medicationTime.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
        medicationTime.set(Calendar.SECOND, 0);

        Intent notificationIntent = new Intent(this, NotificationReceiver.class);
        notificationIntent.putExtra("medicationName", medicationName);
        notificationIntent.putExtra("message", "It's time to take your medication: " + medicationName + ".");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                (int) medicationTime.getTimeInMillis(),
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );


        // Build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Medication Reminder")
                .setContentText("It's time to take your medication: " + medicationName + ".")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        // Add wearable-specific extensions
        NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender()
                .setContentAction(0);
        builder.extend(wearableExtender);

        // Notify
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify((int) medicationTime.getTimeInMillis(), builder.build());

        // Schedule Alarm for Notification
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    medicationTime.getTimeInMillis(),
                    pendingIntent
            );
        }
    }
}