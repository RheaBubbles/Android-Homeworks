package com.hit.bubbl.sqlitedemo1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Bubbles
 * @create 2018/7/16
 * @Describe
 */
public class PersonSqliteOpenHelper extends SQLiteOpenHelper {

    private static final String name = "person.db";
    private static final int version = 1;

    public PersonSqliteOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
            "create table person(" +
            "id integer primary key autoincrement," +
            "name varchar(20)," +
            "number varchar(20))"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
