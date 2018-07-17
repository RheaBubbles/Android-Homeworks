package com.hit.bubbl.listdemo1_myadapter;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private List<String> items;
    private static final int itemSize = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialItems();

        lv = findViewById(R.id.lv);

        lv.setAdapter(new MyAdapter());
    }

    private void initialItems() {
        items = new ArrayList<>();
        for(int i = 0; i< itemSize; i++) {
            items.add("List Item " + i);
        }
    }

    //一般一个接口给出多个方法，都会提供一些抽象适配器子类
    //通常以simpleXXX、defaultXXX、baseXXX
    //如果直接实现ListAdapter接口，其方法特别多
    //因为ListView前台与后台交互特别频繁，Android系统将该控件设计为MVC模式
    /*
     * M相当于List<Person>（数据模型）
     * V相当于listview（视图）
     * C相当于adapter（控制器，即数据适配器）
     * */
    private class MyAdapter extends BaseAdapter {
        private static final String TAG = "MyAdapter ";
        @Override
        public int getCount() {
            //控制lisrview中总共有多少个条目
            //条目个数为集合中元素总数
            return items.size();
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            Log.i(TAG, "返回view对象，位置：" + position); //后台查看滚动条目位置
            TextView tv = new TextView(getApplicationContext());
            tv.setTextSize(20);
            tv.setPadding(48,24,24,24);
            //得到某个位置对应的person对象（条目）
            String item = items.get(position);
            //将获取的条目添加到lisrview控件中显示
            //当条目占满界面，则就是不会再访问和添加条目，
            //在拖动屏幕时，上（或下）条目脱出屏幕，则这时有重新添加条目
            //即只要条目满屏，就停止访问集合内容，反之添加条目到满屏为止
            //被移出的条目不再会重新访问，并被Java垃圾回收器回收
            //就是说：条目被创建条目在屏幕显示，被移出条目垃圾回收
            tv.setText(item);
            return tv;
        }
    }
}
