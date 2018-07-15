package com.example.bubbl.packetchinaapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class ProvinceAdapter extends ArrayAdapter {

    private final int resourceId;

    public ProvinceAdapter(Context context, int textViewResourceId, List<ProvinceInfo> provinces) {
        super(context, textViewResourceId, provinces);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ProvinceInfo provinceInfo = (ProvinceInfo) getItem(position);
        // 获取当前项的Province实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        //实例化一个View对象
        Button provinceName = view.findViewById(R.id.province_name);
        provinceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(provinceInfo.getIntroURL());
                intent.setData(content_url);
                context.startActivity(intent);
            }
        });

        provinceName.setText(provinceInfo.getName());
        return view;
    }
}
