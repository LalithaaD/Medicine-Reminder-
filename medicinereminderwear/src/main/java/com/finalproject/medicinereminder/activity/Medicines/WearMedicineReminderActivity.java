package com.finalproject.medicinereminder.activity.Medicines;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.finalproject.medicinereminder.R;
import com.finalproject.medicinereminder.databinding.ActivityWearMedicineReminderBinding;

public class WearMedicineReminderActivity extends AppCompatActivity {
    ActivityWearMedicineReminderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityWearMedicineReminderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}