package com.example.android.meter.inputManually;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.meter.R;

public class InputManually extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_manually);
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
