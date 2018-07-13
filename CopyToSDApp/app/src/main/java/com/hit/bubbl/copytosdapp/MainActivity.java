package com.hit.bubbl.copytosdapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView tvSelectPath, tvCopyPath;
    private String path, copyPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSelectPath = findViewById(R.id.select_path);
        tvCopyPath = findViewById(R.id.sd_path);

        checkPermission();
    }

    public void selectFile(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            // 这里用的是StackOverflow上大神Paul Burke的代码
            path = FilePathTools.getPath(this, uri);
            tvSelectPath.setText(path);
            Toast.makeText(this,"获取路径成功", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void copyFile(View v) {
        File file = new File(path);
        try {
            FileInputStream fis = new FileInputStream(file);
            int length = fis.available();
            byte temp[] = new byte[length];
            fis.read(temp);
            fis.close();
            Toast.makeText(this, "File Size:" + length/8 + "Bytes"
                    , Toast.LENGTH_SHORT).show();

            @SuppressLint("SdCardPath") File newFile = new File(copyPath);
            FileOutputStream fos = new FileOutputStream(newFile);

            fos.write(temp);
            fos.close();

            Toast.makeText(this,"文件写入成功", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectCopyPath(View v) {
        Toast.makeText(this, "暂未实现，使用默认地址", Toast.LENGTH_SHORT).show();
        String[] temp = path.split("/");
        String fileName = temp[temp.length - 1];
        copyPath = "/sdcard/CopyToSDApp/" + fileName;
        tvCopyPath.setText(copyPath);
    }

    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        } else {
            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "checkPermission: 已经授权！");
        }
    }
}
