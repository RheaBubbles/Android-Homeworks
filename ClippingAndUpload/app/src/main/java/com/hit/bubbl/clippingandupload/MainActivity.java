package com.hit.bubbl.clippingandupload;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    final Handler uploadHandler = new Handler(this);

    FloatingActionButton view_btn;
    ListView lsImages;
    List<ImageCol> imageCols;
    File tempFile;

    private static final int PHOTO_REQUEST_TAKE_PHOTO = 1;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private static final int PHOTO_REQUEST_CUT = 3;

    private static final int PHOTO_UPLOAD_SUCCESS = 0;
    private static final int PHOTO_UPLOAD_FAILED = -1;

    private static final int MY_PERMISSIONS_REQUEST = 1;

    // 创建一个以当前时间为名称的文件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        checkPermission();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET
                    },
                    MY_PERMISSIONS_REQUEST);
        }
    }

    private void init() {
        final String[] arrayString = { "拍照", "相册" };
        final String title = "选择上传头像方式";
        view_btn = findViewById(R.id.btn);
        view_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        getPhotoFileName());
                AlertDialog.Builder dialog =
                        new AlertDialog.Builder(MainActivity.this)
                        .setTitle(title)
                        .setItems(arrayString, onDialogClick);
                dialog.show();
            }
        });

        lsImages = findViewById(R.id.image_row_list);
        initialItems();
        ImageRowAdapter imageRowAdapter = new ImageRowAdapter(this, imageCols);
        lsImages.setAdapter(imageRowAdapter);
        // 获取当前的所有图片

        // 生成行对象List

        // 初始化Adapter

        // 初始化ListView
    }

    private void initialItems() {
        imageCols = new ArrayList<>();
        for(int i=0;i<60;i++) {
            ImageCol itemInfo = new ImageCol();
            imageCols.add(itemInfo);
        }
    }

    private boolean downloadImages() {
        // 检测文件名列表文件是否存在
        // 不存在则创建并返回true
        // 存在则进行下载并返回true
        // 下载失败返回false
        return true;
    }

    @Override
    public boolean handleMessage(Message msg) {
        Drawable drawable = new BitmapDrawable(getResources(), (Bitmap) msg.obj);
//        view_pic.setBackground(drawable);
        switch (msg.what) {
            case PHOTO_UPLOAD_SUCCESS:
                Toast.makeText(this, "头像上传成功",
                        Toast.LENGTH_SHORT).show();
                break;
            case PHOTO_UPLOAD_FAILED:
                Toast.makeText(this, "头像上传失败，请配置地址和端口",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    // 对话框
    DialogInterface.OnClickListener onDialogClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    startCameraPicCut(dialog);// 开启照相
                    break;
                case 1:
                    startImageCapture(dialog);// 开启图库
                    break;
                default:
                    break;
            }
        }
    };

    private void startCameraPicCut(DialogInterface dialog) {
        // TODO Auto-generated method stub
        dialog.dismiss();
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Uri imageUri;

        if (Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(MainActivity.this,
                    "com.hit.bubbl.clippingandupload.fileProvider", //可以是任意字符串
                    tempFile);
        }else{
            imageUri = Uri.fromFile(tempFile);
        }

        intent.putExtra("camerasensortype", 2);// 调用前置摄像头
        intent.putExtra("autofocus", true);// 自动对焦
        intent.putExtra("fullScreen", false);// 全屏
        intent.putExtra("showActionIcons", false);
        // 指定调用相机拍照后照片的储存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, PHOTO_REQUEST_TAKE_PHOTO);
    }

    private void startImageCapture(DialogInterface dialog) {
        // TODO Auto-generated method stub
        dialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri;
        switch (requestCode) {
            case PHOTO_REQUEST_TAKE_PHOTO:
                if (Build.VERSION.SDK_INT >= 24){
                    Toast.makeText(this, "这是大于24的SDK", Toast.LENGTH_SHORT).show();
                    imageUri = FileProvider.getUriForFile(MainActivity.this,
                            "com.hit.bubbl.clippingandupload.fileProvider", //可以是任意字符串
                            tempFile);
                }else{
                    imageUri = Uri.fromFile(tempFile);
                }
                startPhotoZoom(imageUri, 150);
                break;
            case PHOTO_REQUEST_GALLERY:
                if (data != null) {
                    String path = FilePathTools.getPath(this, data.getData());
                    File file = new File(path);
                    if (Build.VERSION.SDK_INT >= 24){
                        Toast.makeText(this, "这是大于24的SDK", Toast.LENGTH_SHORT).show();
                        imageUri = FileProvider.getUriForFile(MainActivity.this,
                                "com.hit.bubbl.clippingandupload.fileProvider", //可以是任意字符串
                                file);
                    }else{
                        imageUri = Uri.fromFile(file);
                    }
                    startPhotoZoom(imageUri, 150);
                }
                break;
            case PHOTO_REQUEST_CUT:
                if (data != null) {
                    setPicToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private void setPicToView(Intent picData) {
        Bundle bundle = picData.getExtras();
        if (bundle != null) {
            final Bitmap photo = bundle.getParcelable("data");

            new Thread() {
                @Override
                public void run() {
                    byte[] photoData = GraphicsBitmapUtils.Bitmap2Bytes(photo);
                    UploadFile uploadFile = new UploadFile(Settings.getIp());
                    Map parameters = new HashMap();
                    parameters.put("msg", Settings.getDebugMsg());

                    boolean isUploadSuccess = false;

                    try {
                        isUploadSuccess = uploadFile.defaultUploadMethod(
                                photoData, getPhotoFileName(), parameters);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if (isUploadSuccess) {
                        uploadHandler.obtainMessage(PHOTO_UPLOAD_SUCCESS, photo).sendToTarget();
                    } else {
                        uploadHandler.obtainMessage(PHOTO_UPLOAD_FAILED, photo).sendToTarget();
                    }
                }
            }.start();
        }
    }

    public void openSetting(MenuItem item) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}
