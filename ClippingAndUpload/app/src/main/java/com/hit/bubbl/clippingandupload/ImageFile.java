package com.hit.bubbl.clippingandupload;

import android.graphics.Bitmap;

/**
 * @author Bubbles
 * @create 2018/7/25
 * @Describe
 */
public class ImageFile {

    public String name;
    public Bitmap bitmap;

    public ImageFile(String name, Bitmap bitmap) {
        super();
        this.name = name;
        this.bitmap = bitmap;
    }
}
