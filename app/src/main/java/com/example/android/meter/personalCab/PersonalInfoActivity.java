package com.example.android.meter.personalCab;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.meter.R;

public class PersonalInfoActivity extends AppCompatActivity {

    TextView aName;
    TextView aECode;
    TextView aGCode;
    TextView aWCode;

    AlertDialog dialog;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        SharedPreferences sharedPref = getSharedPreferences("UsersInfo",
                Context.MODE_PRIVATE);
        aName = (TextView) findViewById(R.id.actual_name);
        aName.setText(sharedPref.getString("username", "Не вказане"));

        aECode = (TextView) findViewById(R.id.actual_e_code);
        aECode.setText(sharedPref.getString("ECode", "Не вказаний"));

        aGCode = (TextView) findViewById(R.id.actual_g_code);
        aGCode.setText(sharedPref.getString("GCode", "Не вказаний"));

        aWCode = (TextView) findViewById(R.id.actual_w_code);
        aWCode.setText(sharedPref.getString("WCode", "Не вказаний"));
    }


    public void changeActualName(View view) {

        aName = (TextView) findViewById(R.id.actual_name);
        dialog = new AlertDialog.Builder(this).create();
        editText = new EditText(this);

        button = (Button) findViewById(R.id.change_name);

        dialog.setTitle("Введіть нове ім'я: ");
        dialog.setView(editText);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Зберегти",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        aName.setText(editText.getText());

                        SharedPreferences sharedPref = getSharedPreferences("UsersInfo",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", aName.getText().toString());
                        editor.apply();
                        Toast.makeText(getApplicationContext(), "Збережено",
                                Toast.LENGTH_LONG).show();
                    }
                });
        editText.setInputType(8192);
        dialog.show();

    }


    public void changeActualECode(View view) {
        aECode = (TextView) findViewById(R.id.actual_e_code);
        dialog = new AlertDialog.Builder(this).create();
        editText = new EditText(this);
        button = (Button) findViewById(R.id.change_e_code);

        dialog.setTitle("Введіть новий код: ");
        dialog.setView(editText);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Зберегти",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        aECode.setText(editText.getText());

                        SharedPreferences sharedPref = getSharedPreferences("UsersInfo",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("ECode", aECode.getText().toString());
                        editor.apply();
                        Toast.makeText(getApplicationContext(), "Збережено",
                                Toast.LENGTH_LONG).show();
                    }
                });
        editText.setInputType(2);
        dialog.show();
    }

    public void changeActualGCode(View view) {
        aGCode = (TextView) findViewById(R.id.actual_g_code);
        dialog = new AlertDialog.Builder(this).create();
        editText = new EditText(this);
        button = (Button) findViewById(R.id.change_g_code);

        dialog.setTitle("Введіть новий код: ");
        dialog.setView(editText);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Зберегти",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        aGCode.setText(editText.getText());

                        SharedPreferences sharedPref = getSharedPreferences("UsersInfo",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("GCode", aGCode.getText().toString());
                        editor.apply();
                        Toast.makeText(getApplicationContext(), "Збережено",
                                Toast.LENGTH_LONG).show();
                    }
                });
        editText.setInputType(2);
        dialog.show();

    }

    public void changeActualWCode(View view) {
        aWCode = (TextView) findViewById(R.id.actual_w_code);
        dialog = new AlertDialog.Builder(this).create();
        editText = new EditText(this);
        button = (Button) findViewById(R.id.change_w_code);

        dialog.setTitle("Введіть новий код: ");
        dialog.setView(editText);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Зберегти",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        aWCode.setText(editText.getText());

                        SharedPreferences sharedPref = getSharedPreferences("UsersInfo",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("WCode", aWCode.getText().toString());
                        editor.apply();
                        Toast.makeText(getApplicationContext(), "Збережено",
                                Toast.LENGTH_LONG).show();
                    }
                });
        editText.setInputType(2);
        dialog.show();

    }
}
