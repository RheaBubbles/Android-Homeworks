package com.hit.bubbl.clippingandupload;

import android.os.Bundle;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);

        Timer timer=new Timer();
        timer.schedule(new TimerTask(){
            public void run(){
                WelcomeActivity.this.finish();
                this.cancel();
            }
        },2000);

    }

}
