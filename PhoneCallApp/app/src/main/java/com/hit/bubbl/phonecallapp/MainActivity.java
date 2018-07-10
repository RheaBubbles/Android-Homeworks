package com.hit.bubbl.phonecallapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Bubbles
 * @create 2018/7/10
 * @Type Activity
 * @Layout activity_main
 * @Describe 拨打电话简单App的入口Activity，需要在打开时初始化
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // private Button mButton = null;
    // 1.6及其以前 老编程方法里，设置为null防止内存泄漏
    private Button callButton,callButton_1,callButton_3;
    // 1.7以后类成员自动添加null
    private TextView phoneNumber;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callButton = (Button) findViewById(R.id.call_button);
        callButton_1 = (Button) findViewById(R.id.call_button_1);
        callButton_3 = (Button) findViewById(R.id.call_button_3);
        phoneNumber = (TextView) findViewById(R.id.call_edit_text);

        // 设置自定义继承OnClickListener类实体
        callButton.setOnClickListener(new MyClick());

        // 另一种监听 设置匿名内部OnClickListener类
        callButton_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });

        callButton_3.setOnClickListener(this);
    }

    private class MyClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            call();
        }
    }

    private void call () {
        String number = phoneNumber.getText().toString().trim();
        // 可以实现一个数字检测的部分
        number = number.replaceAll("[^\\d]", "");
        Toast.makeText(MainActivity.this, number, Toast.LENGTH_SHORT).show();

        // 打开其他Activity执行任务
        Intent callIntent = new Intent();
        callIntent.setAction(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));

        // 后面这部分怎么执行取决于运行环境
        // 如果是6.0以前的版本，可以直接拨打电话，startActivity
        // 如果是6.0及其以后版本，则需要动态权限申请，然后才能startActivity

        // 检查是否获得了权限（Android6.0运行时权限）
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            // 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CALL_PHONE)) {
                // 返回值：
                //  如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
                //  如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
                //  如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                //  弹窗需要解释为何需要该权限，再次请求授权
                Toast.makeText(MainActivity.this, "请授权！", Toast.LENGTH_LONG).show();

                // 帮跳转到该应用的设置界面，让用户手动授权
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            } else {
                // 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        } else {
            // 已经获得授权，可以打电话
            startActivity(callIntent);
        }
    }

    public void button2Call(View view) {
        call();
    }

    @Override
    public void onClick(View view) {
        // 以前的推荐方式，高聚合低耦合，但不是万金油
        switch (view.getId()) {
            case R.id.call_button_3:
                call();
                break;
        }
    }
}
