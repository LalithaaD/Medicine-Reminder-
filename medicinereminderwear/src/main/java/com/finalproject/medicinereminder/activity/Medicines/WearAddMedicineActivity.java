package com.finalproject.medicinereminder.activity.Medicines;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.finalproject.medicinereminder.MedicineReminderUtility;
import com.finalproject.medicinereminder.Models.MedicinesModel;
import com.finalproject.medicinereminder.R;
import com.finalproject.medicinereminder.databinding.ActivityWearAddMedicineBinding;

import java.util.ArrayList;
import java.util.List;

public class WearAddMedicineActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityWearAddMedicineBinding addMedicineBinding;

    MedicinesModel medicineModel;
    List<MedicinesModel> medicineList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMedicineBinding=ActivityWearAddMedicineBinding.inflate(getLayoutInflater());
        setContentView(addMedicineBinding.getRoot());
        init();
    }
    public void init()
    {
            addMedicineBinding.btnAmSave.setOnClickListener(this);
    }
    public void saveMedicine(String medicineName)
    {
        Context context=getApplicationContext();

        // Retrieve the existing medicine list
        List<MedicinesModel> medicineList = MedicineReminderUtility.getMedicineList(context);

        // Add the new medicine to the list
        MedicinesModel medicineModel = new MedicinesModel(medicineName);
        medicineList.add(medicineModel);

        // Save the updated list
        MedicineReminderUtility.saveMedicineList(context, medicineList);
        startActivity(new Intent(this, WearMedicinesActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==addMedicineBinding.btnAmSave.getId())
        {
            saveMedicine(addMedicineBinding.edtAmMedicineName.getText().toString().trim());
        }

    }
}