package com.example.android.meter.inputManually;

import android.Manifest;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.meter.R;
import com.example.android.meter.RegionOfInterestActivity;
import com.example.android.meter.personalCab.PersonalInfoActivity;
import com.pixolus.meterreading.MeterAppearance;
import com.pixolus.meterreading.MeterReadingActivity;
import com.pixolus.meterreading.MeterReadingFragment;
import com.pixolus.meterreading.MeterReadingResult;

import java.util.ArrayList;

public class ManualGas extends AppCompatActivity {
    private String hasGMeasure;
    TextView textView;
    AlertDialog dialog;
    static EditText editText;

    public static void setMetric(String metric) {
        ManualGas.metric = metric;
    }

    static String metric;


    public void sentMeasurement(View view) {

        SharedPreferences sharedPref = getSharedPreferences("UsersInfo",
                Context.MODE_PRIVATE);
        String gCode = sharedPref.getString("GCode", "1");
        String aName = sharedPref.getString("username", "");
        EditText gasMeasurement = (EditText) findViewById(R.id.gas_measurement);

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


    public void sendSMS(View view) {
        SharedPreferences sharedPref = getSharedPreferences("UsersInfo",
                Context.MODE_PRIVATE);
        String gCode = sharedPref.getString("GCode", "1");
        String aName = sharedPref.getString("username", " ");
        EditText gasMeasurement = (EditText) findViewById(R.id.gas_measurement);

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

    public static class AlertDialogFragment extends DialogFragment {
        private static final String ARGS_KEY_TITLE = "title";
        private static final String ARGS_KEY_MESSAGE = "message";

        public static AlertDialogFragment newInstance(String title, String message) {
            AlertDialogFragment frag = new AlertDialogFragment();
            Bundle args = new Bundle();
            args.putString(ARGS_KEY_TITLE, title);
            args.putString(ARGS_KEY_MESSAGE, message);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String title = getArguments().getString(ARGS_KEY_TITLE);
            String message = getArguments().getString(ARGS_KEY_MESSAGE);

            return new android.app.AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(R.string.title_button_ok, null)
                    .create();
        }
    }

    private static final int PERMISSIONS_REQUEST_CAMERA = 42;
    private Intent mStartActivityIntent;
    private boolean mStartActivityForResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manual_gas);

        editText = (EditText) findViewById(R.id.gas_measurement);
        editText.setText(metric);
        // Capture "this" for use in the button callback classes
        final ManualGas thisActivity = this;

        // Setup the buttons for opening different meter reading variants


        Button regionOfInterestActivityButton = (Button) findViewById(R.id.sdkRegionOfInterestActivityButton);
        if (regionOfInterestActivityButton != null) {
            regionOfInterestActivityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent demoMeterReadingIntent = new Intent(thisActivity, RegionOfInterestActivity.class)
                            .putExtra(MeterReadingActivity.EXTRA_METER_APPEARANCE, MeterAppearance.MECHANICAL_BLACK)
                            .putExtra(MeterReadingActivity.EXTRA_INTEGER_DIGITS, MeterReadingFragment.AUTOMATIC)
                            .putExtra(MeterReadingActivity.EXTRA_FRACTION_DIGITS, MeterReadingFragment.AUTOMATIC)
                            .putExtra(MeterReadingActivity.EXTRA_NUMBER_OF_COUNTERS, 1)
                            .putExtra(MeterReadingActivity.EXTRA_TIMEOUT_UNREADABLE_COUNTER, 0)
                            .putExtra(MeterReadingActivity.EXTRA_TIMEOUT_AFTER_LAST_DETECTION, 0)
                            .putExtra(MeterReadingActivity.EXTRA_TIMEOUT, 0)
                            .putExtra(MeterReadingActivity.EXTRA_ALLOWS_ROTATION, true)
                            .putExtra(MeterReadingActivity.EXTRA_ZOOM, 1.3)
                            .putExtra(MeterReadingActivity.EXTRA_IS_RESULTS_OVERLAY_VISIBLE, true)
                            .putExtra(MeterReadingActivity.EXTRA_IS_DEBUG_OVERLAY_VISIBLE, false);

                    startActivityWithPermissionRequest(demoMeterReadingIntent, false);
                }
            });
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Prepare a title for display in an alert dialog. We will set this to a sensible text later in this method.
        String alertTitle;

        // Prepare a message for display in an alert dialog. We may add more text later on through this method.
        String alertMessage = "Activity result code: " + resultCode;

        if (intent == null) {
            // We should not be here, as the MeterReadingActivity should always return an intent.
            // If we still end up here for some reason, we provide an alert title saying that we did not receive results.
            alertTitle = "MeterReading returned without results.";
        } else {
            switch (resultCode) {
                case RESULT_OK:
                    alertTitle = "MeterReading returned results:";
                    break;
                case RESULT_CANCELED:
                    alertTitle = "MeterReading was cancelled.";
                    break;
                case MeterReadingActivity.RESULT_ERROR_NO_CAMERA_ACCESS:
                    alertTitle = "MeterReading failed.";
                    alertMessage += "\n\nAccess to the camera hardware failed.";
                    break;
                default:
                    // The MeterReadingActivity returns either RESULT_OK, RESULT_CANCELED, or
                    // RESULT_ERROR_NO_CAMERA_ACCESS, so we should not be here. However, you may
                    // want to return a different status from your custom activity.
                    alertTitle = "MeterReading returned with unknown result code.";
            }

            // Get the reading results which are returned via the intent's extras.
            ArrayList<MeterReadingResult> readingResults = intent.getParcelableArrayListExtra(MeterReadingActivity.RESULT_EXTRA_READINGS);
            // They are only available if the resultCode is RESULT_OK, so we check for null here.
            if (readingResults != null) {
                // Add readings to the alert message (could be more than one for multi-tariff meters).
                for (MeterReadingResult readingResult : readingResults) {
                    alertMessage += "\n\nClean reading: " + readingResult.cleanReadingString();
                    alertMessage += "\nRaw reading: " + readingResult.rawReadingString();
                    alertMessage += "\nReading status: " + readingResult.status();
                }
            }
        }

        // Finally, we can show the alert
        showDialog(alertTitle, alertMessage);
    }

    private void showDialog(@NonNull String title, @NonNull String message) {
        DialogFragment newFragment = AlertDialogFragment.newInstance(title, message);
        newFragment.show(getFragmentManager(), "dialog");
    }

    /**
     * Start an activity (for result) that requires additional permissions.
     *
     * @param intent                 The intent used to start the activity.
     * @param startActivityForResult To start an activity for a result, pass true here, otherwise
     *                               false.
     */
    private void startActivityWithPermissionRequest(Intent intent, boolean startActivityForResult) {
        this.mStartActivityIntent = intent;
        mStartActivityForResult = startActivityForResult;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // use this if the usage of the permission is not clear
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

                // Provide an additional rationale to the user if the permission was not granted
                // and the user would benefit from additional context for the use of the permission.
                // For example if the user has previously denied the permission.
                View mLayout = findViewById(android.R.id.content);
                if (mLayout != null) {
                    Snackbar.make(mLayout, R.string.permission_camera_rationale, Snackbar.LENGTH_INDEFINITE)
                            .setAction(android.R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityCompat.requestPermissions(ManualGas.this,
                                            new String[]{Manifest.permission.CAMERA},
                                            PERMISSIONS_REQUEST_CAMERA);
                                }
                            })
                            .show();
                }
            } else {
                // Camera permission has not been granted yet. Request it directly.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSIONS_REQUEST_CAMERA);
            }

            return;
        }

        if (startActivityForResult) {
            startActivityForResult(intent, 0);
        } else {
            startActivity(intent);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mStartActivityForResult) {
                        startActivityForResult(mStartActivityIntent, 0);
                    } else {
                        startActivity(mStartActivityIntent);
                    }
                } else {
                    View mLayout = findViewById(android.R.id.content);
                    if (mLayout != null) {
                        Snackbar.make(mLayout, R.string.permissions_not_granted,
                                Snackbar.LENGTH_SHORT).show();
                    }
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

