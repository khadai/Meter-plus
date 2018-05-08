package com.example.android.meter.inputManually;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.meter.CameraPage;
import com.example.android.meter.R;
import com.example.android.meter.personalCab.PersonalInfoActivity;

public class ManualGas extends AppCompatActivity {
    private String hasGMeasure;
    TextView textView;
    AlertDialog dialog;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_gas);

        textView = (TextView) findViewById(R.id.store_activity_theme);
        textView.setText(CameraPage.getText());
    }

    public void sentMeasurement(View view) {

        SharedPreferences sharedPref = getSharedPreferences("UsersInfo",
                Context.MODE_PRIVATE);
        String gCode = sharedPref.getString("GCode", "no code");
        String aName = sharedPref.getString("username", "no name");
        EditText gasMeasurement = findViewById(R.id.gas_measurement);

        hasGMeasure = gasMeasurement.getText().toString();

        if ((gCode.length() <= 1)) {
            dialogToInputInfo();
        } else if ((gasMeasurement.length() <= 1)) {
            dialogToInputMeasurement();
        } else {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + "contact@lv.104.ua")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Подання показників. " + aName);
            intent.putExtra(Intent.EXTRA_TEXT, gCode + " " + hasGMeasure);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    public void dialogToInputMeasurement() {
        dialog = new AlertDialog.Builder(this).create();
        editText = new EditText(this);

        dialog.setTitle("Увага! Не введено показ ");

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Спробувати знову",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        dialog.show();
    }

    public void dialogToInputInfo() {
        dialog = new AlertDialog.Builder(this).create();
        editText = new EditText(this);

        dialog.setTitle("Увага! Не введені потрібні дані ");

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Перейти до налаштувань",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openPersonalInfoActivity();
                    }
                });
        dialog.show();
    }

    public void openPersonalInfoActivity() {
        Intent i = new Intent(this, PersonalInfoActivity.class);
        startActivity(i);
    }

    public void onClickScanWithCamera(View v) {
        Intent intent = new Intent(getApplicationContext(), CameraPage.class);
        startActivity(intent);
    }

    public void sendSMS(View view) {
        SharedPreferences sharedPref = getSharedPreferences("UsersInfo",
                Context.MODE_PRIVATE);
        String gCode = sharedPref.getString("GCode", "no code");
        String aName = sharedPref.getString("username", "no name");
        EditText gasMeasurement = findViewById(R.id.gas_measurement);

        hasGMeasure = gasMeasurement.getText().toString();

        if ((gCode.length() <= 1)) {
            dialogToInputInfo();
        } else if ((gasMeasurement.length() <= 1)) {
            dialogToInputMeasurement();
        } else {
            Uri uri = Uri.parse("smsto:7104");
            Intent it = new Intent(Intent.ACTION_SENDTO, uri);
            it.putExtra("sms_body", gCode + " " + hasGMeasure);
            startActivity(it);
        }
    }
}

