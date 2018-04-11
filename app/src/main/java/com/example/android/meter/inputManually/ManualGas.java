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

public class ManualGas extends AppCompatActivity {
    private String hasGMeasure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_gas);
    }

    public void sentMeasurement(View view) {
        SharedPreferences sharedPref = getSharedPreferences("UsersInfo",
                Context.MODE_PRIVATE);
        String gCode = sharedPref.getString("GCode", "");
        EditText gasMeasurement = findViewById(R.id.gas_measurement);
        hasGMeasure = gasMeasurement.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + "contact@lv.104.ua")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Подання показників ");
        intent.putExtra(Intent.EXTRA_TEXT, gCode + " " + hasGMeasure);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
