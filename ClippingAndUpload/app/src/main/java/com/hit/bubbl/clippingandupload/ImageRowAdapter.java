package com.hit.bubbl.clippingandupload;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author Bubbles
 * @create 2018/7/24
 * @Describe
 */
public class ImageRowAdapter extends BaseAdapter {

    private static final String TAG = "MyAdapter ";
    private List<ImageCol> imageCols;
    private Context context;

    public ImageRowAdapter(Context context, List<ImageCol> imageCols) {
        super();
        this.imageCols = imageCols;
        this.context = context;
    }

    @Override
    public int getCount() {
        //控制lisrview中总共有多少个条目
        //条目个数为集合中元素总数
        return imageCols.size();
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
        view = view.inflate(context, R.layout.image_row, null);
//        Log.i(TAG, "返回view对象，位置：" + position); //后台查看滚动条目位置
////        TextView tv = new TextView(getApplicationContext());
////        tv.setTextSize(20);
////        tv.setPadding(48,24,24,24);
//        //得到某个位置对应的person对象（条目）
//        ImageCol item = imageCols.get(position);
//        //将获取的条目添加到lisrview控件中显示
//        //当条目占满界面，则就是不会再访问和添加条目，
//        //在拖动屏幕时，上（或下）条目脱出屏幕，则这时有重新添加条目
//        //即只要条目满屏，就停止访问集合内容，反之添加条目到满屏为止
//        //被移出的条目不再会重新访问，并被Java垃圾回收器回收
//        //就是说：条目被创建条目在屏幕显示，被移出条目垃圾回收
////        tv.setText(item);
////        return tv;
        return view;
    }
}
