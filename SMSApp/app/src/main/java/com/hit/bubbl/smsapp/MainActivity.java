package com.hit.bubbl.smsapp;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText phoneNumber, smsText;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumber = findViewById(R.id.phone_number);
        smsText = findViewById(R.id.sms_text);
    }

    public void send(View view) {
        String number = phoneNumber.getText().toString().trim();
        String text = smsText.getText().toString();
        if(TextUtils.isEmpty(number) || TextUtils.isEmpty(text)) {
            Toast.makeText(this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:" + number));
            intent.putExtra("sms_body", text);
            startActivity(intent);
        }
    }

    public void addNumber(View view) {
        String number = phoneNumber.getText().toString().trim();
        number = number.replaceAll("[^\\d]", "");
        if(TextUtils.isEmpty(number)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
        } else {
            // Call a service to save the number in the file
            if(!IOService.saveNumber(this, number)){
                Toast.makeText(this, "保存手机号失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "成功保存", Toast.LENGTH_SHORT).show();
                // Clean the text of the editText
                phoneNumber.setText("");
            }
        }

    }

    public void sendDirect(View view) {
        String text = smsText.getText().toString();
        if(TextUtils.isEmpty(text)) {
            Toast.makeText(this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
        }
        else if (!confirmPermissions()) {
            Toast.makeText(this, "请通过授权", Toast.LENGTH_SHORT).show();
        } else {
            // Get the numbers from the file by service
            String[] numbers = IOService.getNumbers(this);
            if(numbers == null) {
                Toast.makeText(this, "获取号码文件失败", Toast.LENGTH_SHORT).show();
            } else if (numbers.length == 0) {
                Toast.makeText(this, "还没有输入任何号码", Toast.LENGTH_SHORT).show();
            } else {
                for(String number :numbers) {
                    sendMessage(number, text);
                }
                Toast.makeText(this, "成功发送", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean confirmPermissions() {
        // 检查是否获得了权限（Android6.0运行时权限）
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            // 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.SEND_SMS)) {
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
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } else {
            return true;
        }
        return false;
    }

    public void sendMessage(String number, String text) {
        // 已经获得授权，可以发送短信
        //获取短信管理器
        SmsManager smsManager = SmsManager.getDefault();

        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent,
                0);

        //处理返回的接收状态
        String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
        // create the deilverIntent parameter
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0,
                deliverIntent, 0);

        //拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(text);
        for (String dText : divideContents) {
            smsManager.sendTextMessage(number, null, dText, sentPI, deliverPI);
        }
    }
}
