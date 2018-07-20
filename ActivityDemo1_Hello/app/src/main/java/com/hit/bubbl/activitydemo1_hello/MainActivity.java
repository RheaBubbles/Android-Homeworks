package com.hit.bubbl.activitydemo1_hello;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        textView = findViewById(R.id.show);
    }

    public void sendText(View view) {
        Intent it = new Intent(this,ReceiveActivity.class);

        it.putExtra("name", editText.getText().toString());

        startActivityForResult(it, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 老师给的文档把这里的resultCode写成了requestCode从而功能无法实现
        switch(resultCode) {
            case RESULT_OK:
                textView.setText("Return text is: " + data.getStringExtra("name"));
                break;
            case RESULT_CANCELED:
                textView.setText("Operation is canceled");
                break;
        }
    }
}
