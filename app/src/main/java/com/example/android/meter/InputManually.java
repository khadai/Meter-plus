package com.example.android.meter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InputManually extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_manually);
    }

    public void openManuallElectricityMenu (View view){
        Intent i = new Intent(this, ManualElectricity.class);
        startActivity(i);
    }
}
