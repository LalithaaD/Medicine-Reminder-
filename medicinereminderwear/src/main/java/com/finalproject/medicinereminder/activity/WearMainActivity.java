package com.finalproject.medicinereminder.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.finalproject.medicinereminder.MedicineReminderUtility;
import com.finalproject.medicinereminder.Models.MedicinesModel;
import com.finalproject.medicinereminder.activity.Medicines.WearMedicinesActivity;
import com.finalproject.medicinereminder.databinding.ActivityWearMainBinding;

import java.util.ArrayList;
import java.util.List;

public class WearMainActivity extends AppCompatActivity implements View.OnClickListener {

    //Binding
    ActivityWearMainBinding mainBinding;

    //Data management


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = getApplicationContext();
        super.onCreate(savedInstanceState);
        mainBinding=ActivityWearMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        init();

    }

    //To initialize the views
    public void init()
    {
        mainBinding.wearHomeBtnReminder.setOnClickListener(this);
        mainBinding.wearHomeBtnMedications.setOnClickListener(this);
        mainBinding.wearHomeBtnCalendar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==mainBinding.wearHomeBtnMedications.getId()){
        Intent intent=new Intent(this, WearMedicinesActivity.class);
        startActivity(intent);
        }


    }
}