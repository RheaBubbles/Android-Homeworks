package com.hit.bubbl.intentdemo1_sendemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etAddress, etContent;
    private TextView tvAddressList;
    private List<String> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAddress = findViewById(R.id.address);
        etContent = findViewById(R.id.content);
        tvAddressList = findViewById(R.id.address_list);

        addressList = new ArrayList<>();
    }

    public void addAddress(View view) {
        String address = etAddress.getText().toString().trim();
        etAddress.setText("");
        addressList.add(address);
        String addressListText = tvAddressList.getText().toString();
        addressListText += "," + address;
        tvAddressList.setText(addressListText);
    }

    public void sendEmail(View view) {
        String[] address = addressList.toArray(new String[addressList.size()]);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        /* 设置类型 */
        emailIntent.setType("plain/text");
        String subject = "Email from Yumiko";
        String content = etContent.getText().toString();
        emailIntent.putExtra(Intent.EXTRA_EMAIL, address);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);
        startActivity(emailIntent);

        addressList.clear();
        tvAddressList.setText("");

        Toast.makeText(this, "Send Success", Toast.LENGTH_SHORT).show();
    }
}
