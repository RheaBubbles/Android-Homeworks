package com.hit.bubbl.listdemo2_arrayadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private String[] names;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        names = new String[]{"功能1", "功能2", "功能3", "功能4", "功能5"};
        lv = findViewById(R.id.lv);
        //设置数据适配器（使用Android提供的数据适配器类）
        //选择需要的ArrayAdapter工作方法：自动提供listview组件的布局和数据的显示方式
        //1.context：this
        //2.resource：布局或其他资源文件,即每个条目所对应的布局文件
        //3.testViewResourceid: 自定义布局组件的id
        //4.object:需要显示的数据文件
        lv.setAdapter(new ArrayAdapter<>(this, R.layout.list_item, R.id.tv, names));
    }
}
