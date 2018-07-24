package com.hit.bubbl.clippingandupload;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
//import android.widget.EditText;

public class SettingActivity extends AppCompatActivity {

    private TextInputEditText etIp, etPort, etDebug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        etIp = findViewById(R.id.ip);
        etPort = findViewById(R.id.port);
        etDebug = findViewById(R.id.debug);

        etIp.setText(Settings.getIp());
        etPort.setText(Settings.getPort());
        etDebug.setText(Settings.getDebugMsg());
    }

    public void saveSetting(View view) {
        String ip = etIp.getText().toString();
        String port = etPort.getText().toString();
        String debugMsg = etDebug.getText().toString();
        Settings.setIp(ip);
        Settings.setPort(port);
        Settings.setDebugMsg(debugMsg);

        finish();
    }
}
