package com.example.android.meter.inputManually;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.android.meter.R;

public class ManualElectricity extends AppCompatActivity {

    private String hasEMeasure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_electricity);
    }

    public void sentMeasurement(View view) {
        SharedPreferences sharedPref = getSharedPreferences("UsersInfo",
                Context.MODE_PRIVATE);
        String eCode = sharedPref.getString("ECode", "");
        EditText elecricityMeasurement = (EditText) findViewById(R.id.electricity_measurement);
        hasEMeasure = elecricityMeasurement.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + "pokaz@loe.lviv.ua")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Подання показників ");
        intent.putExtra(Intent.EXTRA_TEXT, eCode + " " + hasEMeasure);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}
