package com.doctorrokter.bb10dialer;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;

import com.doctorrokter.bb10dialer.activities.CallActivity;

import java.util.Arrays;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.telecom.TelecomManager.ACTION_CHANGE_DEFAULT_DIALER;
import static android.telecom.TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME;
import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    private int REQUEST_PERMISSION = 0;

    public void goToCaller(View view) {
        Intent intent = new Intent(this, CallActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        offerReplacingDefaultDialer();
    }

    private void makeCall() {
        if (checkSelfPermission(CALL_PHONE) == PERMISSION_GRANTED) {

        } else {
            String[] permissions = {CALL_PHONE};
            requestPermissions(permissions, REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION && asList(grantResults).contains(PERMISSION_GRANTED)) {
            makeCall();
            makeCall();
        }
    }

    private void offerReplacingDefaultDialer() {
        if (!getSystemService(TelecomManager.class).getDefaultDialerPackage().equals(getPackageName())) {
            Intent intent = new Intent(ACTION_CHANGE_DEFAULT_DIALER)
                    .putExtra(EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, getPackageName());
            startActivity(intent);
        }
    }
}
