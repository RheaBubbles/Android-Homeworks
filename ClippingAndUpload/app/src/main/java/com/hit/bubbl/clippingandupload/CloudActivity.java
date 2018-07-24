package com.hit.bubbl.clippingandupload;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CloudActivity extends AppCompatActivity {

    private List<Bitmap> images;

    private ListView lsImages;
    private ImageRowAdapter imageRowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud_activty);

        // 暂不做获取服务器文件列表，使用本地记录列表

        // 从云端进行下载，如果本地已有则不保存，或者不发出请求
        images = downloadImages();
        // 然后保存到Cache目录

        // 然后初始化到Adapter 以及 ListView， 基本上复用之前的组件
        if(images.size() != 0) {
            RelativeLayout empty = findViewById(R.id.empty);
            empty.setVisibility(View.INVISIBLE);
            // 生成行对象List
            lsImages = findViewById(R.id.image_row_list);

            // 初始化Adapter
            imageRowAdapter = new ImageRowAdapter(this, images,
                    getAdapterLayoutHeight(), ImageRowAdapter.USE_FOR_CLOUD);

            // 初始化ListView
            lsImages.setAdapter(imageRowAdapter);
        }
        // 不过最好是单独再写写Adapter，因为Item触发的事件不同
    }

    private List<Bitmap> downloadImages() {
        List<Bitmap> images = new ArrayList<>();
        // 检测文件名列表文件是否存在
        File list = new File(getFilesDir(),"list.txt");
        if(!list.exists()) {
            // 列表不存在 暂时不处理
            // 不存在则创建并返回true
            // 存在则进行下载并返回true
            // 下载失败返回false
        } else {
            // 读取文件名列表
            try {
                FileInputStream fis = new FileInputStream(list);

                BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                String str = br.readLine();

                String[] names = str.split("##");

                SocketRequest socketRequest = new SocketRequest(Settings.getIp());
                for(int i = 0;i < names.length;i++) {
                    if(names[i].length() != 0) {
                        // 进行下载
                        String cachePath = getCacheDir()+"/images/";
                        if(socketRequest.download(cachePath, names[i])) {
                            // 下载成功
                            images.add(FilePathTools.decodeFile(cachePath + names[i]));
                        } else {
                            // 下载失败
                            Log.d("CloudActivity", "Download Failed on File:" + cachePath + names[i]);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    public int getAdapterLayoutHeight() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        Log.d("MainActivity", "Width:" + dm.widthPixels);
        return dm.widthPixels/3;
    }
}
