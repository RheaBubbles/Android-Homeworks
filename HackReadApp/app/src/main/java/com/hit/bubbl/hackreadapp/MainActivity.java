package com.hit.bubbl.hackreadapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void readFile(View view) {
        //当前其他手机使用文件，不能使用 Context，因为 Context 是给出
        //当前项目路径，必须使用需要读取手机的包路径
        File file = new File("/data/data/com.hit.bubbl.saveinfoapp/private.txt");
        //获取文件输入流
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String result = br.readLine();
            br.close();
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "读取文件失败...", Toast.LENGTH_SHORT).show();
        }
    }

    public void writeFile(View view) {
        File file = new File("/data/data/com.hit.bubbl.saveinfoapp/private.txt");
        //获取文件输入流
        try {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write("xxxxxxxxxxxxx".getBytes());
            fos.close();
            Toast.makeText(this, "写文件成功...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "写私有文件失败...", Toast.LENGTH_SHORT).show();
        }
    }
}

