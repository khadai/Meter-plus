pixometer SDK – Getting Started
===============================

This document is a brief tutorial on how to integrate the pixometer SDK into a
newly created Android Studio project. The tutorial is based on Android Studio
and should work fine when using Android Studio 1.0 or later. See further below
near the end of the document for instructions on integrating the SDK in an
Eclipse-based project. If you have any issues, comments, or suggestions, feel
free to contact us at [developer@pixometer.io](mailto:developer@pixometer.io).

Configure a new project in Android Studio
-----------------------------------------

Create a new project in Android Studio, either from the “File” menu or from the 
startup screen. In the assistant, make sure that the following options are set:

-   When the assistant asks about the “Target Android Devices”, choose “Phone 
    and Tablet” and set the “Minimum SDK” to “API 14: Android 4.0 ("Ice Cream
    Sandwich)”
-   When the assistant asks to “Add an activity to Mobile”, choose “Blank 
    Activity”.

After the assistant finishes, your project will be set up and you can include 
the pixometer SDK as follows.

Adding the meterreading dependency
----------------------------------

The suggested way of integrating the pixometer SDK into your own project is to
add the `com.pixolus.meterreading` package as a dependency to your existing
Android Studio project. The package comes in two variants:

-   An evaluation variant shipped to interested developers.
-   A commercial variant for a final product that can be obtained from 
    pixolus GmbH based on a paid contract.

Both variants share an identical API, so you can develop your app with
evaluation variant without any upfront costs and license the production variant
as a drop-in replacement whenever you are ready to go to the market.

In Android Studio, with your project being open, follow these steps:

1.  Open the project `build.gradle` file.
2.  Add the meterreading repository to the list of your repositories:

        allprojects {
            repositories {
                jcenter()
                maven {
                    url "../../android/meterreading-repo"
                }
            }
        }
    Please note the relative path in the URL. It assumes the root directory of your
    project is on the same level as the pixometer  `android` folder.
3.  In your app `build.gradle` file add a dependency to the meterreading module
    (replacing `X.Y.Z` with the current version number of the pixometer SDK):

        dependencies {
            // ...
            compile ('com.pixolus:meterreading:X.Y.Z')
        }

### Dependencies of the meterreading SDK

The meterreading SDK itself depends on additional modules

    compile ('com.android.support:support-annotations:*')
    compile ('com.google.android.gms:play-services-vision:*')
    compile ('com.google.zxing:core:*')
    
These dependencies are automatically added to your project when you are using
the SDK from the local maven repository. If you are using the provided AAR
directly, you have to add those dependencies manually.  
The Google Play Services are, however, a compile-time only requirement. So on
devices not having the Google Play Services available or operational, a fallback
mechanism is used transparently for the user and developer, to provide the
barcode reading functionality using ZXing.

Invoke the meter reading activity from the app
----------------------------------------------

1.  Navigate to the “MainActivity.java” file.
2.  In the `onCreate()` method, append the following lines at the end:

        Button button = new Button(this);
        button.setText("Start MeterReading");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MeterReadingActivity.class)
                    .putExtra(MeterReadingActivity.EXTRA_INTEGER_DIGITS, MeterReadingFragment.AUTOMATIC)
                    .putExtra(MeterReadingActivity.EXTRA_FRACTION_DIGITS, MeterReadingFragment.AUTOMATIC)
                    .putExtra(MeterReadingActivity.EXTRA_NUMBER_OF_COUNTERS, MeterReadingFragment.AUTOMATIC);
                startActivityForResult(intent, 0);
            }
        });
        ViewGroup contentView = (ViewGroup)findViewById(android.R.id.content);
        contentView.addView(button);
        
3.  Make sure that the imports at the top of the file include the newly required 
    classes. This should be automatically suggested by Android Studio when 
    pasting the code from step 2. If not, copy and paste the following somewhere
    in the imports:

        import android.content.Intent;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import com.pixolus.meterreading.MeterReadingActivity;

4.  Starting from API Level 23, Android introduced a fine grained permission
    system which allows users to decide at runtime of an app to grant or deny
    access to e.g. the camera. Use one of the following methods in order to make
    sure the app can access the camera:
    
    * Set the target Android SDK to API Level 22 or lower.
    * Use the "Settings" app on the device to manually grant the app access to
      the camera. (Of course, you should not take this approach in production
      apps.)
    * Implement the necessary permission request dialogs. The `MainActivity` in
      the "meterreading-demo-android" project provided with the pixometer SDK
      shows how you can do this.

Test the app
------------

Build and run the project. The app should show a large button filling the whole 
screen and labeled “Start MeterReading”. If you tap this button, a fullscreen 
camera preview will be shown. You can now hold the camera in front of a meter 
and should see red rectangles appear around the counter. As soon as the counter 
is successfully read, the screen will briefly flash and the camera preview will 
be dismissed.

If you are using the evaluation variant of the pixometer SDK, a text box with 
copyright information will be placed right in the middle of the screen. You can 
just ignore this box: The pixometer SDK will happily read meters, even if the 
text box overlaps the counter. And as the pixometer SDK will find counters 
everywhere in the image, you can also position the camera such that the counter 
does not overlap but is above or below the text box.

Note that by default, the app assumes a roller counter with white digits on
black background and tries to determine the number of digits before and after
the decimal point automatically. If you use a meter with a different appearance,
or require more robustness, read on to “Next Steps” to learn how to configure
the pixometer SDK.

Next Steps
----------

### Configure the meter properties

As noted above, the app assumes a roller counter with white digits printed on 
black rollers and determines the number of digits automatically. You can set
different properties by adding extras to the intent which starts the
`MeterReadingActivity` by means of using the `putExtra()` method on the intent.
Please refer to the documentation for `MeterReadingActivity` and the
“Configuring\_the\_SDK.md” file in the root folder of the pixometer SDK for an
in-depth discussion of how to properly configure the meter reading for your
application scenario.

### Interpret the results

In order to get the results returned by the activity when it is dismissed, you 
need to implement the 
`public void onActivityResult(int requestCode, int resultCode, Intent intent)` 
method in `MainActivity.java`.

In `onActivityResult()`, check the `resultCode`: If it is `Activity.RESULT_OK`, 
you can get the results by invoking 
`getParcelableArrayListExtra(MeterReadingActivity.RESULT_EXTRA_READINGS)` on the
`intent` parameter. It returns an array of type `ArrayList<MeterReadingResult>`.

See the documentation for the `MeterReadingActivity` and `MeterReadingResult` in
order to interpret return codes and meter reading results.

### Roll your own

The demo project set up in this tutorial is only a starting point of course. You
may want to replace the default user interface with your own. See the
documentation for `MeterReadingActivity` on how to create a custom subclass of
the standard activity or use a `MeterReadingFragment` if you need more
flexibility and want to integrate a meter reading fragment into your own
activity. The “meterreading-demo-android" Android Studio project gives some
examples on how to do that.

#### Conflicts with other SDKs

The pixometer SDK is shipped with native libraries for 32- and 64bit
architectures. So, if you are using another SDK in your app, that only provides
a 32-bit native library, Android might fail to load pixometer's
`libmeterreading.so` on ARM64 or x64 devices, as 32- and 64-bit native libraries
cannot be loaded at the same time. In that case `MeterReadingFragment`'s method
`isArchitectureSupported()` returns false and no meter reading can be performed.

If you cannot get a 32- and 64-bit version of the other native libraries, you
can use the following workaround: Exclude pixometer's 64-bit library from your
APK by adding the following lines to your app's `build.gradle`:

    android {
        //...
        packagingOptions {
            exclude "lib/arm64-v8a/libmeterreading.so"
            exclude "lib/x64/libmeterreading.so"
        }
        //...
    }

#### Creating specific APK for each architecture

To reduce the final size of your APK, you can split your app's APK to only
contain support for one ABI. When the APKs are uploaded to Google Play, it is
transparently decided which APK a user downloads when installing your app.

Splitting your APK is especially useful if your app contains other SDKs or large
media files, which bring you close or even above the 100MB size limit for APKs
uploaded to Google Play. But also when you are not close to that limit, it might
be nice to have your users to only download and install things they really need.

    android {
        //...
        splits {

            // Configures multiple APKs based on ABI.
            abi {

                // Enables building multiple APKs per ABI.
                enable true

                // By default all ABIs are included, so use reset() and include 
                // to create only APKs for the ABIs pixometer SDK supports.

                // Resets the list of ABIs that Gradle should create APKs for.
                reset()

                // Specifies a list of ABIs that Gradle should create APKs for.
                include "armeabi", "armeabi-v7a", "arm64-v8a", "x86", "x86_64"

                // Specifies that we do not want to also generate a universal
                // APK that includes all ABIs.
                universalApk false
            }
        }
    }

Permissions, Features, and Activities
-------------------------------------

The pixometer SDK requires access to the device’s camera hardware and needs to
be allowed to display the default activity. By integrating the
`com.pixolus.meterreading` package, the following declarations are added
automatically to the `AndroidManifest.xml` of your app (they are taken from the
`AndroidManifest.xml` file inside the package):

    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.FLASHLIGHT" />

    <uses-feature
        android:name="android.hardware.camera"
        android:required="false" />
    <uses-feature
        android:name="android.hardware.camera.autofocus"
        android:required="false" />
    <uses-feature
        android:name="android.hardware.camera.flash"
        android:required="false" />

    <application>
        <activity android:name="com.pixolus.meterreading.MeterReadingActivity">
        </activity>
    </application>

### Android API Level 23

Starting from API Level 23, Android introduced a fine grained permission system which
allows users to decide at runtime of an app to grant or deny access to e.g. the camera.
The pixometer SDK does not handle these permission requests itself to give the hosting app
the chance to request the permission at an appropriate point of time and in a way matching
the app.  
For an example on how to implement this, have a look at the `MainActivity` in the
"meterreading-demo-android" project.

ProGuard settings
-----------------

If you are using “ProGuard” for optimizing or obfuscating your app, you need to
make sure that the classes of the pixometer SDK are kept unmodified. To do this,
add the following line to your ProGuard configuration file (which is usually
named `proguard-rules.pro` in Android Studio projects):

    -keep class com.pixolus.** { *; }

Eclipse
-------

Eclipse does not support `.aar` files as provided by the pixometer SDK Maven
repository directly. However, an `.aar` file is basically just a `.zip` file
which contains all the necessary files. The following steps are needed in order
to integrate the pixometer SDK into an Eclipse-based project.

1.  Quit Eclipse if it is currently running.
2.  Use a Zip utility to extract the `.aar` file located in the
    `meterreading-repo/com/pixolus/meterreading/X.Y.Z/` folder. (On some
    platforms or with some utilities you may have to rename the file to carry a
    `.zip` file extension.)
3.  Rename the extracted `classes.jar` file to `meterreading.jar`.
4.  Copy the `meterreading.jar` file as well as the `armeabi`, `armeabi-v7a` 
    and `x86` folders from the `jni` folder to the `libs` folder of your 
    Eclipse project.
5.  Start Eclipse and open your project.
6.  In the “Package Explorer” on the left side, select the `libs` folder and
    choose the “Refresh” command from the “File” menu. (Alternatively,
    right-click onto the `libs` folder and choose “Refresh” from the
    contextual menu.) The `meterreading.jar` file and, `armeabi`, `armeabi-v7a` 
    and `x86` folders should now be visible in the `libs` folder.
7.  In your `AndroidManifest.xml` file:
    1.  Set `android:minSdkVersion` to `16`.
    2.  Add the following lines to the `<manifest>` section:
        
            <uses-permission android:name="android.permission.CAMERA" />
            <uses-permission android:name="android.permission.FLASHLIGHT" />

            <uses-feature
                android:name="android.hardware.camera"
                android:required="false" />
            <uses-feature
                android:name="android.hardware.camera.autofocus"
                android:required="false" />
            <uses-feature
                android:name="android.hardware.camera.flash"
                android:required="false" />

    3. Add the following line to the `<application>` section:

            <activity android:name="com.pixolus.meterreading.MeterReadingActivity"></activity>

8.  You can now invoke the meter reading functionality from your code. For
    example, add the following lines to an existing Activity (note that you
    may have to add the necessary `import` statements in the file header):
    
        Intent intent = new Intent(this, MeterReadingActivity.class)
            .putExtra(MeterReadingActivity.EXTRA_INTEGER_DIGITS, MeterReadingFragment.AUTOMATIC)
            .putExtra(MeterReadingActivity.EXTRA_FRACTION_DIGITS, MeterReadingFragment.AUTOMATIC)
            .putExtra(MeterReadingActivity.EXTRA_NUMBER_OF_COUNTERS, MeterReadingFragment.AUTOMATIC)
            .putExtra(MeterReadingActivity.EXTRA_TIMEOUT_UNREADABLE_COUNTER, 0)
            .putExtra(MeterReadingActivity.EXTRA_TIMEOUT_AFTER_LAST_DETECTION, 0)
            .putExtra(MeterReadingActivity.EXTRA_TIMEOUT, 0)
            .putExtra(MeterReadingActivity.EXTRA_ALLOWS_ROTATION, true)
            .putExtra(MeterReadingActivity.EXTRA_ZOOM, 1.3)
            .putExtra(MeterReadingActivity.EXTRA_IS_RESULTS_OVERLAY_VISIBLE, true)
            .putExtra(MeterReadingActivity.EXTRA_IS_DEBUG_OVERLAY_VISIBLE, BuildConfig.DEBUG);
        startActivityForResult(intent, 0);

9.  The calling Activity must implement the `public void onActivityResult(int requestCode, int resultCode, Intent intent)`
    function, otherwise it will crash after reading the first meter. You can
    find an example implementation in the Android Studio sample project provided
    with the pixometer SDK (in the file `meterreading-demo-android/app/src/main/java/com/pixolus/meterreading_demo/MainActivity.java`).

Licenses
--------

See the “LICENSE” file for the usage license governing the pixometer SDK.

Note that the pixometer SDK itself makes use of third-party software libraries,
the licenses of which are listed in the file "Acknowledgements.txt" in the main
directory of the SDK. When distributing the pixometer SDK as part of your
software, you accept these third-party license terms and need to reproduce the
copyright notices, conditions, and disclaimers as described.


Copyright
---------

Copyright (c) 2015-2017 pixolus GmbH, Germany
