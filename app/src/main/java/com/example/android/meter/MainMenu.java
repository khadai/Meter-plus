package com.example.android.meter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.meter.inputManually.InputManually;
import com.example.android.meter.personalCab.PersonalInfoActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void openInputManuallyMenu(View view) {
        Intent i = new Intent(this, InputManually.class);
        startActivity(i);
    }

    public void openPersonalInfoActivity(View view) {
        Intent i = new Intent(this, PersonalInfoActivity.class);
        startActivity(i);
    }

    public void onClickScanWithCamera(View v){
        Intent intent = new Intent(getApplicationContext(), CameraPage.class);
        startActivity(intent);
        finish();
    }
}