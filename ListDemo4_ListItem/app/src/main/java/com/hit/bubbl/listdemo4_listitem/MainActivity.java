package com.hit.bubbl.listdemo4_listitem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ItemInfo> items;
    private static final int itemSize = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialItems();

        ListView lv = findViewById(R.id.lv);
        //实现ListView接口，使用接口子类适配器；
        //这样只实现需要的接口方法即可
        lv.setAdapter(new MyAdapter());
    }

    private void initialItems() {
        items = new ArrayList<>();
        for(int i=0;i<itemSize;i++) {
            ItemInfo itemInfo = new ItemInfo(i,"Title "+i,
                    "Content "+i+": this is some content...");
            items.add(itemInfo);
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
        private static final String TAG = "MyAdapter";
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
        public View getView(int position, View v, ViewGroup viewGroup) {
            ItemInfo itemInfo = items.get(position);
            //实现list_item布局文件显示方法
            //1.将list_item布局文件转换为对象，
            //2.使用inflate打气筒为需要显示的组件充气
            //（1）context:获取当前环境对象（注意：获取当前内部类的context）
            //（2）给哪个资源文件充气
            // (3) 如果不想为该布局文件指定父窗体，则使用adapter来加载，一般使用null
            // null就是将此布局文件作为独立窗体使用
            // 3.view对象就是使用inflate转化出来的视图对象
            View view = View.inflate(MainActivity.this, R.layout.list_item, null);
            TextView tv_id = view.findViewById(R.id.id);
            tv_id.setText("Id: " + itemInfo.id);
            TextView tv_title = view.findViewById(R.id.title);
            tv_title.setText(itemInfo.title);
            TextView tv_content = view.findViewById(R.id.content);
            tv_content.setText(itemInfo.content);
            return view;
        }
    }
}
