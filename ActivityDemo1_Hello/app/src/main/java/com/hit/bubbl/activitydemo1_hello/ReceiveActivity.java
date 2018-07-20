package com.hit.bubbl.activitydemo1_hello;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiveActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        textView = findViewById(R.id.show);
        editText = findViewById(R.id.edit_text);

        Intent it = getIntent();
        String info = it.getStringExtra("name");
        textView.setText("The text from the Activity:" + info);
    }

    public void sendText(View view) {
        Toast.makeText(this, "Send Back!", Toast.LENGTH_SHORT).show();
        getIntent().putExtra("name", editText.getText().toString());
        setResult(RESULT_OK, getIntent());
        finish();
    }
}
