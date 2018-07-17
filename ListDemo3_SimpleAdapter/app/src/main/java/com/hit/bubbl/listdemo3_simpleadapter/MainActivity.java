package com.hit.bubbl.listdemo3_simpleadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        //设置数据适配器,其工作方法参数如下：
        //public SimpleAdapter(android.content.Context context,
        //                     java.util.List<? extends java.util.Map<String, ?>> data,
        //                     @LayoutRes int resource,
        //                     String[] from,
        //                     @IdRes int[] to)
        //2.<? extends java.util.Map<String, ?>> data是一个List<Map>嵌套数据集合
        //其中，List集合代表其元素是每一个ListView列表的item条目；
        //Map集合是代表每个条目中的内容，即图标和文本
        List<Map<String, Object>> data = new ArrayList<>( );
        Map<String, Object> map1 = new HashMap<>( );
        map1.put("nametext", "我们第一个功能"); //每个条目的文本
        map1.put("iconid", R.mipmap.ic_menu_star); //每个条目的图标
        Map<String, Object> map2 = new HashMap<>( );
        map2.put("nametext", "我们第二个功能"); //每个条目的文本
        map2.put("iconid", R.mipmap.ic_menu_stop); //每个条目的图标
        Map<String, Object> map3 = new HashMap<>( );
        map3.put("nametext", "我们第三个功能"); //每个条目的文本
        map3.put("iconid", R.mipmap.ic_menu_today); //每个条目的图标
        Map<String, Object> map4 = new HashMap<>( );
        map4.put("nametext", "我们第四个功能"); //每个条目的文本
        map4.put("iconid", R.mipmap.ic_menu_upload); //每个条目的图标
        Map<String, Object> map5 = new HashMap<>( );
        map5.put("nametext", "我们第五个功能"); //每个条目的文本
        map5.put("iconid", R.mipmap.ic_menu_start_conversation); //每个条目的图标
        //添加到List类型的data集合中
        data.add(map1);
        data.add(map2);
        data.add(map3);
        data.add(map4);
        data.add(map5);
        //3.第三个参数resource是布局文档
        //4.第四个参数String[] from，即map集合key的数组
        //5.第五个参数int[] to，即哪一个控件与from对象相绑定
        //注：如果无法点出R.id组件，重新启动IDE
        lv.setAdapter(new SimpleAdapter(this, data, R.layout.list_item, new String[]{"nametext", "iconid"}, new int[]{R.id.tv, R.id.iv}));
    }
}
