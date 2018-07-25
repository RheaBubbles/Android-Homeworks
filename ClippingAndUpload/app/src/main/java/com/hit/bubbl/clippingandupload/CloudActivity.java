package com.hit.bubbl.clippingandupload;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloudActivity extends AppCompatActivity {

    private List<ImageFile> images;

    private ListView lsImages;
    private ImageRowAdapter imageRowAdapter;

    private FloatingActionButton download;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("CloudActivity","请求结果:" + val);
            initialListView();
            download.show();
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO: http request.
            images = downloadImages();
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", "请求结果");
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud_activty);
        download = findViewById(R.id.download);
        download.hide();
        new Thread(runnable).start();
    }

    public void initialListView() {

        RelativeLayout emptyCloud = findViewById(R.id.empty_cloud);
        RelativeLayout downloading = findViewById(R.id.downloading);
        downloading.setVisibility(View.INVISIBLE);

        // 然后初始化到Adapter 以及 ListView， 基本上复用之前的组件
        if(images.size() != 0) {

            // 生成行对象List
            lsImages = findViewById(R.id.image_row_list);

            // 初始化Adapter
            imageRowAdapter = new ImageRowAdapter(this, images,
                    getAdapterLayoutHeight(), ImageRowAdapter.USE_FOR_CLOUD);

            // 初始化ListView
            lsImages.setAdapter(imageRowAdapter);
        } else {
            emptyCloud.setVisibility(ViewGroup.VISIBLE);
        }
    }

    private List<ImageFile> downloadImages() {
        List<ImageFile> images = new ArrayList<>();

        SocketRequest socketRequest = new SocketRequest(Settings.getIp());
        try {
            // 删除之前的list.txt
            File list = new File(getCacheDir(),"list.txt");
            if(list.exists()) {
                list.delete();
            }

            if(socketRequest.download(getCacheDir()+"/","list.txt")) {
                // 服务器文件列表获取成功
//                Toast.makeText(this, "连接服务器成功", Toast.LENGTH_SHORT).show();
                Log.d("Cloud", "连接服务器成功");
                // 清空缓存文件
                File cache = new File(getCacheDir() + "/images");
                if(cache.isDirectory()) {
                    // 存在则清空
                    File[] imageCaches = cache.listFiles();
                    for(int i=0;i<imageCaches.length;i++) {
                        imageCaches[i].delete();
                    }
                } else if(!cache.exists()){
                    cache.mkdir();
                }
            } else {
                // 服务器文件列表获取失败
                // 提示网络问题
//                Toast.makeText(this, "连接服务器失败，请确认设置", Toast.LENGTH_SHORT).show();
                Log.d("Cloud", "连接服务器失败，请确认设置");
                return images;
            }
        } catch (IOException e) {
            e.printStackTrace();
//            Toast.makeText(this, "出现文件IO错误", Toast.LENGTH_SHORT).show();
            Log.d("Cloud", "出现文件IO错误");
            return images;
        }

        File list = new File(getCacheDir(),"list.txt");

        if(!list.exists()) {
            // 服务器文件列表获取失败或者文件出错
            Toast.makeText(this, "初始化列表失败，请重试", Toast.LENGTH_SHORT).show();
            Log.d("Cloud", "初始化列表失败，请重试");
        } else {
            try {
                FileInputStream fis = new FileInputStream(list);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String str = br.readLine();
                String[] names = str.split("##");

                for(int i = 0;i < names.length;i++) {
                    if(names[i].length() != 0) {
                        // 进行下载
                        String cachePath = getCacheDir()+"/images/";
                        if(socketRequest.download(cachePath, names[i])) {
                            // 下载成功
                            Bitmap bitmap = FilePathTools.decodeFile(cachePath + names[i]);
                            ImageFile image = new ImageFile(names[i], bitmap);
                            images.add(image);
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

    public void saveAll(View view) {
        // 获取两个目录下的列表清单，比对后下载没有保存过的
        File cloudList = new File(getCacheDir(), "list.txt");
        String[] cloudNames = null;
        if (cloudList.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(cloudList);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String str = br.readLine();
                cloudNames = str.split("##");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File localList = new File(getFilesDir(), "list.txt");
        String[] localNames = null;
        if (localList.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(localList);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String str = br.readLine();
                localNames = str.split("##");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(cloudNames == null) {
            // 并没有云端文件列表
            Toast.makeText(this, "初始化云端列表失败，请重试", Toast.LENGTH_SHORT).show();
        } else if(localNames == null) {
            Toast.makeText(this, "初始化本地列表", Toast.LENGTH_SHORT).show();
        } else if(cloudNames.length == 0) {
            Toast.makeText(this, "还没有云端文件", Toast.LENGTH_SHORT).show();
        } else if(localNames.length == 0 || localNames == null) {
            if(!localList.exists()) {
                try {
                    localList.createNewFile();
                    FileOutputStream fos = openFileOutput("list.txt", Context.MODE_APPEND);
                    fos.write(("##").getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // 全部保存并写入文件
                for(int i = 0;i < cloudNames.length;i++) {
                    if(cloudNames[i].length() != 0) {
                        Bitmap bitmap = null;
                        try {
                            bitmap = FilePathTools.decodeFile(getCacheDir() + "/images/" + cloudNames[i]);
                            savePic(bitmap, cloudNames[i]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            Toast.makeText(this, "下载完成", Toast.LENGTH_SHORT).show();
        } else {
            List<String> localNameList = Arrays.asList(localNames);
            for(int i=0;i<cloudNames.length;i++) {
                if(!localNameList.contains(cloudNames[i]) && cloudNames[i].length() != 0) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = FilePathTools.decodeFile(getCacheDir() + "/images/" + cloudNames[i]);
                        savePic(bitmap, cloudNames[i]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Toast.makeText(this, "下载完成", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean savePic(Bitmap image, String fileName) {
        // 先写入文件
        String path = getFilesDir() +"/images/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File imageFile = new File(path, fileName);
        try {
            // 写文件
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imageFile));
            image.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();

            // 写列表
            FileOutputStream fos = openFileOutput("list.txt", Context.MODE_APPEND);
            fos.write((fileName + "##").getBytes());
            fos.close();

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
