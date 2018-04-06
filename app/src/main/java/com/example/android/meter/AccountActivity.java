package com.example.android.meter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class AccountActivity extends AppCompatActivity {

    private EditText codeElectricity;
    private EditText hasName;

    public EditText getHasName() {
        return hasName;
    }

    public EditText getCodeElectricity() {
        return codeElectricity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        hasName = findViewById(R.id.user_name);
        codeElectricity = findViewById(R.id.electricity_code);
    }

    public void saveInfo(View view) {
        SharedPreferences sharedPref = getSharedPreferences("UsersInfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", hasName.getText().toString());
        editor.putString("userECode", codeElectricity.getText().toString());
        editor.apply();

        Toast.makeText(this, "Збережено", Toast.LENGTH_LONG).show();
    }

    public String showInfo(View view) {
        SharedPreferences sharedPref = getSharedPreferences("UsersInfo",
                Context.MODE_PRIVATE);
        String name = sharedPref.getString("username", "");
        String eCode = sharedPref.getString("userECode", "");
        Toast.makeText(this, new StringBuilder().append(name).append(" ").append(eCode).toString(),
                Toast.LENGTH_LONG).show();
        return new StringBuilder().append(name).append(" ").append(eCode).toString();
    }


}
