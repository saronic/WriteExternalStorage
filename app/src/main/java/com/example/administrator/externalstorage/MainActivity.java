package com.example.administrator.externalstorage;

import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EventListener;
import java.util.jar.Manifest;
import java.util.jar.Pack200;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button mWriteBtn, mReadBtn;
    public static final int REQUEST_EXTERNAL_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWriteBtn = (Button) findViewById(R.id.write_btn);
        mReadBtn = (Button) findViewById(R.id.read_btn);
        mReadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readExternalStorage();
            }
        });
        mWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               
                //Environment.MEDIA_MOUNTED
                
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onClick: denied");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_EXTERNAL_PERMISSION);
                } else {
                    Log.d(TAG, "onClick: granted");
                    writeExternalStorage();
                }
                


            }
        });
    }

    private void readExternalStorage() {
       String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File filePath = Environment.getExternalStorageDirectory();
            File file = new File(filePath, "data.txt");
            FileInputStream fis;
            try {
                fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line = br.readLine();
                Log.d(TAG, "line: " + line);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeExternalStorage() {
        String state = Environment.getExternalStorageState();
        File sdcardPath;
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            sdcardPath = Environment.getExternalStorageDirectory();
            File file = new File(sdcardPath, "data.txt");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write("xxxx".getBytes());
                fos.close();
                Log.d(TAG, "okkkkkkkkkkkkkkkkkkkkk: ");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                writeExternalStorage();
                Log.d(TAG, "write succesfully");
            }
        }
    }
}
