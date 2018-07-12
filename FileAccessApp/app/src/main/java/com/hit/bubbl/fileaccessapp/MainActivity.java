package com.hit.bubbl.fileaccessapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView textView, path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        path = findViewById(R.id.path);

        try {
            FileOutputStream outputStream = this.openFileOutput("itcast.txt", Context.MODE_PRIVATE);
            // path package/files/... need a root level to access the file.
            outputStream.write("Yumiko".getBytes());
            outputStream.close();
            FileInputStream inputStream = this.openFileInput("itcast.txt");
            int length = inputStream.available();
            byte[] buffer = new byte[length];
            inputStream.read(buffer);

            String result = new String(buffer, "UTF-8");
            textView.setText(result);
            path.setText(this.getFilesDir().toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
