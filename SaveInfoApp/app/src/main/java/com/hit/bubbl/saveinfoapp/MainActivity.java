package com.hit.bubbl.saveinfoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText etUser;
    private EditText etPass;
    private CheckBox cbSave;
    private RadioGroup rgMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        cbSave = findViewById(R.id.cbSave);
        rgMode = findViewById(R.id.rgMode);

        showInfo();
    }

    public void loginClick(View view) {
        String user = etUser.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        } else {
            //登录处理：判断是否保存密码
            if (cbSave.isChecked()) {
                //检测用户保存密码后台输出信息 Log 类似于 System.out.println()测试方法
                Log.i(TAG, "需要保存用户名和密码");
                boolean result = false;

                int id = rgMode.getCheckedRadioButtonId();

                switch (id) {
                    case R.id.rb1:
                        result = LoginService.saveUserInfo(user, pass, this, 1);
                        break;
                    case R.id.rb2:
                        result = LoginService.saveUserInfo(user, pass, this, 2);
                        break;
                    case R.id.rb3:
                        result = LoginService.saveUserInfo(user, pass, this, 3);
                        break;
                    case R.id.rb4:
                        result = LoginService.saveUserInfo(user, pass, this, 4);
                        break;
                    default:
                        break;
                }
                if (result) {
                    Toast.makeText(this,
                            "保存用户信息成功...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"保存用户信息失败...", Toast.LENGTH_SHORT).show();
                }
            }
            //登录操作：发送数据到数据库服务器，数据库经过验证后返回是否正确信息
            //这里我们先采用模拟方式
            if ("aaa".equals(user) && "123".equals(pass)) {
                Toast.makeText(this, "登录成功...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "登录失败...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showInfo() {
        Map<String, String> map = LoginService.getSaveUserInfo(this.getFilesDir().toString());

        if(map != null) {
            etUser.setText(map.get("username"));
            etPass.setText(map.get("password"));
        }
    }
}
