package com.example.android.meter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.meter.inputManually.InputManually;
import com.example.android.meter.inputManually.ManualElectricity;
import com.example.android.meter.inputManually.ManualGas;
import com.example.android.meter.inputManually.ManualWater;
import com.example.android.meter.personalCab.PersonalInfoActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void openPersonalInfoActivity (View view){
        Intent i = new Intent(this, PersonalInfoActivity.class);
        startActivity(i);
    }

        public void openManualElectricityMenu (View view){
            Intent i = new Intent(this, ManualElectricity.class);
            startActivity(i);
        }

        public void openManualGasMenu(View view) {
            Intent i = new Intent(this, ManualGas.class);
            startActivity(i);
        }

        public void openManualWaterMenu(View view) {
            Intent i = new Intent(this, ManualWater.class);
            startActivity(i);
        }

}
