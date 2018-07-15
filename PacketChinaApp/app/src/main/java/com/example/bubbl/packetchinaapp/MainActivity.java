package com.example.bubbl.packetchinaapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ProvinceAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the province data from the service
        List<ProvinceInfo> provinces = DataService.getProvincesInfo(this);

        adapter = new ProvinceAdapter(MainActivity.this,
                R.layout.province_item, provinces);

        listView = findViewById(R.id.province_list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProvinceInfo provinceInfo = (ProvinceInfo) adapter.getItem(i); // 获取当前项的Province实例
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(provinceInfo.getIntroURL());
                intent.setData(content_url);
                startActivity(intent);
            }
        });
    }
}
