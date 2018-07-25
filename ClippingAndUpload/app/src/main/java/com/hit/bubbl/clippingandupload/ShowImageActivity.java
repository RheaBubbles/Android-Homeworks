package com.hit.bubbl.clippingandupload;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class ShowImageActivity extends Activity {

    private ImageView image, background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_image);

        // 获取一些信息，比如图片bitmap，图片名name
        Intent intent = getIntent();
        int userFor = intent.getIntExtra("userFor", ImageRowAdapter.USE_FOR_DEFAULT);
        String fileName = intent.getStringExtra("fileName");
        byte [] bis=intent.getByteArrayExtra("bitmap");
        Bitmap bitmap= BitmapFactory.decodeByteArray(bis, 0, bis.length);
        Bitmap backgroundBitmap = zoomImg(bitmap, getLayoutHeight(), getLayoutHeight());
        backgroundBitmap = GraphicsBitmapUtils.rsBlur(this, backgroundBitmap, 20);

        image = findViewById(R.id.image);
        background = findViewById(R.id.backgroud_image);

        image.setImageBitmap(bitmap);
        background.setImageBitmap(backgroundBitmap);

        GraphicsBitmapUtils.changeLight(background, -100);
    }

    public int getLayoutHeight() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        Log.d("MainActivity", "Width:" + dm.widthPixels);
        return dm.heightPixels;
    }

    public Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片   www.2cto.com
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

}
