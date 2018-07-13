package com.hit.bubbl.memosizeapp;

import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv_info);
        textView.setText(this.getSdcardInfo() + "\n" + this.getROMInfo());
    }

    public String getSdcardInfo() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        long availableBlocks = stat.getAvailableBlocks();
        long totalSize = blockSize * totalBlocks;
        long availSize = blockSize * availableBlocks;
        String totalStr = Formatter.formatFileSize(this, totalSize);
        String availStr = Formatter.formatFileSize(this, availSize);
        return "Sd Card总内存：" + totalStr + "\n" + "可用内存：" + availStr;
    }

    public String getROMInfo() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        long availableBlocks = stat.getAvailableBlocks();
        long totalSize = blockSize * totalBlocks;
        long availSize = blockSize * availableBlocks;
        String totalStr = Formatter.formatFileSize(this, totalSize);
        String availStr = Formatter.formatFileSize(this, availSize);
        return "手机总内存：" + totalStr + "\n" + "可用内存：" + availStr;
    }
}
