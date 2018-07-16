package com.hit.bubbl.sqlitedemo1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDB();
    }

    private void createDB() {
        PersonSqliteOpenHelper helper = new PersonSqliteOpenHelper(this);
        helper.getWritableDatabase();
        msgToast(this, "数据库创建成功");
    }

    private void msgToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
