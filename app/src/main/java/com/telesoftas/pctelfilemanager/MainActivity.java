package com.telesoftas.pctelfilemanager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int WRITE_STORAGE_REQUEST = 0;
    TextView statusView;

    // Used to load the 'FileManager' library on application startup.
    static {
        System.loadLibrary("FileManager");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.statusText);
        tv.setText(stringFromJNI());
    }

    public void writeScanData(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    WRITE_STORAGE_REQUEST);
            statusView.setText("Requesting WRITE_EXTERNAL_STORAGE Permission...");
            return;
        }
        writeScanData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (WRITE_STORAGE_REQUEST != requestCode) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 1  ||
                grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            statusView.setText("Error: Permission for WRITE_STORAGE_REQUEST was denied");
            Toast.makeText(getApplicationContext(),
                    "WRITE_EXTERNAL_STORAGE permission needed",
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        statusView.setText("WRITE_STORAGE_REQUEST permission granted");
        writeScanData();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native boolean writeScanData();
}
