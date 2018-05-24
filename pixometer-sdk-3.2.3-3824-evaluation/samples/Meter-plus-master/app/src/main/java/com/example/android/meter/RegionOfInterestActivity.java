/**
 * RegionOfInterestActivity.java
 * <p>
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2015-2017 pixolus GmbH, Germany
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.example.android.meter;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.android.meter.inputManually.ManualGas;
import com.example.android.meter.personalCab.PersonalInfoActivity;
import com.pixolus.meterreading.Metadata;
import com.pixolus.meterreading.MeterReadingActivity;
import com.pixolus.meterreading.MeterReadingFragment;
import com.pixolus.meterreading.MeterReadingResult;

import java.util.List;
import java.util.Locale;

public class RegionOfInterestActivity extends MeterReadingActivity {

    private AlertDialog alertDialog = null;
    //private String resultToSent;


    @SuppressLint("EmptyMethod")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We do not create the layout here, because the basic layout is provided by the super class
        // We inflate our own layout in onCreateOverlayView()
    }

    @Override
    protected View onCreateOverlayView() {
        // We should not invoke super here.

        /* Load layout from resources.
         * We pass null for the rootView parameter, although the Android Studio code analyzer warns
         * about this and recommends against it. The root view parameter is used to set the layout
         * parameters of the root view of the inflated layout. If we pass null, the layout parameters
         * of the root view of the inflated layout are ignored. However, the documentation of
         * onCreateOverlayView() of the MeterReading SDK states that the returned view will be added
         * to its parent view using MATCH_PARENT parameters for width and height. Therefore, we just
         * use the MATCH_PARENT setting on the root view of the layout to be inflated when designing
         * the layout. This allows us to get a realistic preview of the overlay in the UI designer.
         */
        @SuppressLint("InflateParams")
        View overlayView = getLayoutInflater().inflate(R.layout.activity_region_of_interest, null);

        final FloatingActionButton torchButton = (FloatingActionButton) overlayView.findViewById(R.id.button_torch);
        torchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean torchState = !getMeterReadingFragment().isTorchOn();
                getMeterReadingFragment().setTorchOn(torchState);
                if (torchState) {
                    torchButton.setImageResource(R.drawable.ic_flash_on_black_36dp);
                } else {
                    torchButton.setImageResource(R.drawable.ic_flash_off_black_36dp);
                }
            }
        });

        final FloatingActionButton backButton = (FloatingActionButton) overlayView.findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        return overlayView;
    }

    @Override
    public void onLayoutChanged(MeterReadingFragment meterReadingFragment, boolean changed, int left, int top, int right, int bottom) {
        super.onLayoutChanged(meterReadingFragment, changed, left, top, right, bottom);

        int width = right - left;
        int height = bottom - top;

        int roi_left, roi_right, roi_top, roi_bottom;

        if (width < height) {
            // we are in portrait mode
            //  ROI should cover 70% of portrait mode's display width
            roi_left = Math.round(0.15f * width);
            roi_right = Math.round(0.15f * width);
            int roi_width = Math.round(0.7f * width);
            //  make ROI 1/4 as high as wide
            int roi_height = Math.round(0.25f * roi_width);
            //  ROI should have its lower border at 1/3 of screen height
            roi_bottom = Math.round(0.66f * height);
            roi_top = height - roi_bottom - roi_height;
        } else {
            // we are in landscape mode
            //  ROI should cover 70% of portrait mode's display width
            //  (we give some extra as height is reduced by height of StatusBar in landscape mode
            //   as compared to portrait mode's width)
            int roi_width = Math.round(0.75f * height);
            //  make ROI 1/4 as high as wide
            int roi_height = Math.round(0.25f * roi_width);
            roi_left = (width - roi_width) / 2;
            roi_right = roi_left;
            //  ROI should have its lower border at 1/3 of screen height
            roi_bottom = Math.round(0.66f * height);
            roi_top = height - roi_bottom - roi_height;
        }

        // set the ROI insets (measured from the view edges) accordingly.
        Rect insets = new Rect(roi_left, roi_top, roi_right, roi_bottom);
        meterReadingFragment.setRegionOfInterestInsets(insets);

        // show it as a white rectangle
        meterReadingFragment.setRegionOfInterestBorderColor(Color.WHITE);
        meterReadingFragment.setShowRegionOfInterest(true);

        // light gray translucent background outside of region of interest
        meterReadingFragment.setRegionOfDisinterestFillColor(Color.argb(0x80, 0xcc, 0xcc, 0xcc));

        // do not show detected counter rectangles
        meterReadingFragment.setCounterBorderColor(Color.TRANSPARENT);
    }


    public void onScanReadings(final MeterReadingFragment meterReadingFragment, List<MeterReadingResult> results, Bitmap image, Metadata metadata) {
        // do not call super's implementation in this case, as it would finish the activity
        // super.onScanReadings(meterReadingFragment, results, image, metadata);

        if (alertDialog != null) {
            // only open one result dialog at a time
            return;
        }

        // we expect exactly one result, just make sure we got it
        if (results.size() == 1) {
            // set number of counters to 0 to stop meter reading while dialog is shown
            meterReadingFragment.setNumberOfCounters(0);
            MeterReadingResult readingResult = results.get(0);
            final String metric =  readingResult.cleanReadingString() ;
            alertDialog = new AlertDialog.Builder(this)
                    .setMessage(String.format(Locale.US, "Прочитано: %s%n", readingResult.cleanReadingString()))
                    .setTitle("Показ")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ManualGas.setMetric(metric);
                            openManualGas();
                            dialog.dismiss();
                            alertDialog = null;
                            // re-enable meter reading
                            meterReadingFragment.setNumberOfCounters(MeterReadingFragment.AUTOMATIC);
                        }
                    }).setNegativeButton("Ввести вручну", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            openManualGas();
                        }
                    })
                    .create();
            alertDialog.show();
        }
    }

    public void openManualGas() {
        Intent i = new Intent(this, ManualGas.class);
        startActivity(i);
    }
//    public String getResultToSent() {
//        return resultToSent;
//    }
}
