package com.hit.bubbl.clippingandupload;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    private List<ImageFile> imageFiles;
    public static final int USE_FOR_DEFAULT = 0;
    public static final int USE_FOR_LOCAL = 1;
    public static final int USE_FOR_CLOUD = 2;

    private int useFor;

    public ImageRowAdapter(Context context, List<ImageFile> imageFiles, int height, int useFor) {
        super();
        this.context = context;
        this.height = height;
        this.useFor = useFor;
        imageCols = new ArrayList<>();
        this.imageFiles = imageFiles;
        assignImageCols();
    }

    private void assignImageCols() {
        ImageFile[] images = null;
        imageCols.clear();
        for(int i = 0;i < imageFiles.size();i+=3) {
            if(i + 2 < imageFiles.size()) {
                // 还足够多图片填满一行
                images = new ImageFile[] {
                        imageFiles.get(i), imageFiles.get(i+1), imageFiles.get(i+2)
                };
            } else {
                // 不够一行时，只加载部分ImageView
                switch (imageFiles.size() - i){
                    case 1:
                        images = new ImageFile[] { imageFiles.get(i) };
                        break;
                    case 2:
                        images = new ImageFile[] {
                                imageFiles.get(i), imageFiles.get(i+1)
                        };
                        break;
                }
            }
            ImageCol imageCol = new ImageCol(images);
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
        final String[] arrayString;
//        Log.i(TAG, "返回view对象，位置：" + position); //后台查看滚动条目位置
        if(useFor == USE_FOR_LOCAL) {
            arrayString = new String[] { "删除该图" };
        } else {
            arrayString = new String[] { "下载该图" };
        }
        final ImageFile[] images =  getItem(position).getImages();

        for(int i = 0;i<images.length;i++) {
            String imageViewId = "image_" + (i + 1);
            String buttonId = "button_" + (i + 1);
            int resImageViewID = context.getResources().getIdentifier(imageViewId, "id",
                    "com.hit.bubbl.clippingandupload");
            int resButtonID = context.getResources().getIdentifier(buttonId, "id",
                    "com.hit.bubbl.clippingandupload");
            ImageView imageView = view.findViewById(resImageViewID);
            Button button = view.findViewById(resButtonID);
            if(useFor == USE_FOR_LOCAL) {
                final int index  = i;
                button.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                                .setItems(arrayString, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                // 删除
                                                Toast.makeText(context, "Delete!",
                                                        Toast.LENGTH_SHORT).show();
                                                ImageFile imageFile = images[index];
                                                String fileName = imageFile.name;
                                                File file = new File(context.getFilesDir()+"/images", fileName);
                                                if(file.exists()) {
                                                    file.delete();
                                                    // 这里删除还要注意重写list.txt的问题
                                                    File localList = new File(context.getFilesDir(), "list.txt");
                                                    String[] localNames = null;
                                                    if (localList.exists()) {
                                                        FileInputStream fis = null;
                                                        try {
                                                            fis = new FileInputStream(localList);
                                                            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                                                            String str = br.readLine();
                                                            localNames = str.split("##");
                                                        } catch (FileNotFoundException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    List<String> localNameList = Arrays.asList(localNames);
                                                    FileOutputStream fos = null;
                                                    try {
                                                        fos = context.openFileOutput("list.txt", Context.MODE_PRIVATE);
                                                        for(int i=0;i<localNameList.size();i++) {
                                                            if(!localNameList.get(i).equals(fileName)) {
                                                                fos.write((localNameList.get(i) + "##").getBytes());
                                                            }
                                                        }
                                                        fos.close();
                                                    } catch (FileNotFoundException e) {
                                                        e.printStackTrace();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                imageFiles.remove(imageFile);
                                                assignImageCols();
                                                notifyDataSetChanged();
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
                        Intent intent = new Intent(context, ShowImageActivity.class);
                        intent.putExtra("userFor", USE_FOR_LOCAL);
                        intent.putExtra("fileName", images[index].name);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        images[index].bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte [] bitmapByte =baos.toByteArray();
                        intent.putExtra("bitmap", bitmapByte);
//                        Bundle bundle = new Bundle();
//                        bundle.putParcelable("bitmap", images[index].bitmap);
//                        intent.putExtra("bundle", bundle);
                        context.startActivity(intent);
                    }
                });
            } else {
                button.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                                .setItems(arrayString, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                // 下载
                                                Toast.makeText(context, "Save to Local",
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
                final int index  = i;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Show Image",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, ShowImageActivity.class);
                        intent.putExtra("userFor", USE_FOR_CLOUD);
                        intent.putExtra("fileName", images[index].name);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        images[index].bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte [] bitmapByte =baos.toByteArray();
                        intent.putExtra("bitmap", bitmapByte);
//                        Bundle bundle = new Bundle();
//                        bundle.putParcelable("bitmap", images[index].bitmap);
//                        intent.putExtra("bundle", bundle);
                        context.startActivity(intent);
                    }
                });
            }
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.height = height;
            params.width = height;
            imageView.setLayoutParams(params);
            button.setLayoutParams(params);
            imageView.setImageBitmap(images[i].bitmap);
        }

        return view;
    }

    public void add(ImageFile image) {
        ImageCol imageCol;
        if(imageCols.size() == 0) {
            imageCol = new ImageCol(new ImageFile[0]);
            imageCols.add(imageCol);
        } else {
            imageCol = imageCols.get(imageCols.size() - 1);

            ImageFile[] imageFiles = imageCol.getImages();

            if(imageFiles.length == 3) {
                // 新建一个
                imageCol = new ImageCol(new ImageFile[] {image});
                imageCols.add(imageCol);
            } else {
                switch (imageFiles.length) {
                    case 0:
                        imageFiles = new ImageFile[] {
                                image
                        };
                        break;
                    case 1:
                        imageFiles = new ImageFile[] {
                                imageFiles[0], image
                        };
                        break;
                    case 2:
                        imageFiles = new ImageFile[] {
                                imageFiles[0], imageFiles[1], image
                        };
                        break;
                }
                imageCol.setImages(imageFiles);
            }
        }
        notifyDataSetChanged();
    }
}
