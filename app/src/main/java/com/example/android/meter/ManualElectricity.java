package com.example.android.meter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ManualElectricity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_electricity);
    }

    public void sentMeasurement(View view){
        EditText elecricityCode = (EditText) findViewById(R.id.electricity_code);
        String hasECode = (String) elecricityCode.getText().toString();

        EditText elecricityMeasurement = (EditText) findViewById(R.id.electricity_measurement);
        String hasEMeasure = (String) elecricityMeasurement.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+ "pokaz@loe.lviv.ua")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Подання показників " );
        intent.putExtra(Intent.EXTRA_TEXT, hasECode+" "+hasEMeasure);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
