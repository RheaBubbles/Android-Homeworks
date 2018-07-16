package com.hit.bubbl.sqlitedemo2_crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Bubbles
 * @create 2018/7/16
 * @Describe
 */
public class PersonSqliteHelper extends SQLiteOpenHelper {

    private static final String name = "person.db";
    private static final int version = 1;


    public PersonSqliteHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS person (" +
                    "personId integer primary key autoincrement," +
                    "name varchar(20)," +
                    "age INTEGER" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
