package com.hit.bubbl.clippingandupload;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bubbles
 * @create 2018/7/24
 * @Describe
 */
public class ImageRowAdapter extends BaseAdapter {

    private static final String TAG = "ImageRowAdapter";
    public List<ImageCol> imageCols;
    private Context context;
    private int height;

    public ImageRowAdapter(Context context, List<Bitmap> bitmaps, int height) {
        super();
        this.context = context;
        this.height = height;
        imageCols = new ArrayList<>();
        Bitmap[] bm = null;
        for(int i = 0;i < bitmaps.size();i+=3) {
            if(i + 2 < bitmaps.size()) {
                // 还足够多图片填满一行
                bm = new Bitmap[] {
                    bitmaps.get(i), bitmaps.get(i+1), bitmaps.get(i+2)
                };
            } else {
                // 不够一行时，只加载部分ImageView
                switch (bitmaps.size() - i){
                    case 1:
                        bm = new Bitmap[] { bitmaps.get(i) };
                        break;
                    case 2:
                        bm = new Bitmap[] {
                                bitmaps.get(i), bitmaps.get(i+1)
                        };
                        break;
                }
            }
            ImageCol imageCol = new ImageCol(bm);
            imageCols.add(imageCol);
        }
    }

    @Override
    public int getCount() {
        //控制lisrview中总共有多少个条目
        //条目个数为集合中元素总数
        return imageCols.size();
    }

    @Override
    public ImageCol getItem(int i) {
        return imageCols.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = view.inflate(context, R.layout.image_row, null);

//        Log.i(TAG, "返回view对象，位置：" + position); //后台查看滚动条目位置
        final String[] arrayString = { "保存到本地", "删除该图" };
        Bitmap[] images =  getItem(position).getImages();

        for(int i = 0;i<images.length;i++) {
            String imageViewId = "image_" + (i + 1);
            int resID = context.getResources().getIdentifier(imageViewId, "id",
                    "com.hit.bubbl.clippingandupload");
            Button button = view.findViewById(resID);
            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                            .setItems(arrayString, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            // 保存
                                            Toast.makeText(context, "Save to SD",
                                                    Toast.LENGTH_SHORT).show();
                                            break;
                                        case 1:
                                            // 删除
                                            Toast.makeText(context, "Delete!",
                                                    Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            });
                    dialog.show();
                    return false;
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Show Image",
                            Toast.LENGTH_SHORT).show();
                }
            });
            ViewGroup.LayoutParams params = button.getLayoutParams();
            params.height = height;
            button.setLayoutParams(params);
            Drawable drawable = new BitmapDrawable(context.getResources(), images[i]);
            button.setBackground(drawable);
        }

        return view;
    }

    public void add(Bitmap image) {
        ImageCol imageCol;
        if(imageCols.size() == 0) {
            imageCol = new ImageCol(new Bitmap[0]);
            imageCols.add(imageCol);
        } else {
            imageCol = imageCols.get(imageCols.size() - 1);

            Bitmap[] bitmaps = imageCol.getImages();

            if(bitmaps.length == 3) {
                // 新建一个
                imageCol = new ImageCol(new Bitmap[] {image});
                imageCols.add(imageCol);
            } else {
                switch (bitmaps.length) {
                    case 0:
                        bitmaps = new Bitmap[] {
                                image
                        };
                        break;
                    case 1:
                        bitmaps = new Bitmap[] {
                                bitmaps[0], image
                        };
                        break;
                    case 2:
                        bitmaps = new Bitmap[] {
                                bitmaps[0], bitmaps[1], image
                        };
                        break;
                }
                imageCol.setImages(bitmaps);
            }
        }

        notifyDataSetChanged();
    }
}
