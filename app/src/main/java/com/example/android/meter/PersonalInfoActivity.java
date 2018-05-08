package com.example.android.meter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PersonalInfoActivity extends AppCompatActivity {

    TextView aName;
    TextView aECode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        SharedPreferences sharedPref = getSharedPreferences("UsersInfo",
                Context.MODE_PRIVATE);

        aName=findViewById(R.id.actual_name);
        aECode=findViewById(R.id.actual_e_code);

        aName.setText(sharedPref.getString("username", ""));
        aECode.setText(sharedPref.getString("userECode", ""));
    }
    public void openAccountActivity (View view){
        Intent i = new Intent(this, AccountActivity.class);
        startActivity(i);
    }
}
