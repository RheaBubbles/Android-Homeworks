package com.example.bubbl.packetchinaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

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

        ProvinceInfo provinceInfo = (ProvinceInfo) getItem(position); // 获取当前项的Province实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        Button provinceName = view.findViewById(R.id.province_name);

        provinceName.setText(provinceInfo.getName());
        return view;
    }
}
