package com.hit.bubbl.clippingandupload;

import android.graphics.Bitmap;

import java.util.List;

/**
 * @author Bubbles
 * @create 2018/7/24
 * @Describe
 */
class ImageCol {

    private ImageFile[] images;

    public ImageCol(ImageFile[] images) {
        super();
        this.images = images;
    }

    public ImageFile[] getImages() {
        return images;
    }

    public void setImages(ImageFile[] images) {
        this.images = images;
    }
}
