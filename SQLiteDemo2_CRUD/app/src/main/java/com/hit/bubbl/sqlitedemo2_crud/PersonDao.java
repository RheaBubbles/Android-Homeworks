package com.hit.bubbl.sqlitedemo2_crud;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bubbles
 * @create 2018/7/16
 * @Describe
 */
public class PersonDao {

    private PersonSqliteHelper helper;

    public PersonDao(Context context) {
        helper = new PersonSqliteHelper(context);
    }

    /**
     * 添加一条记录
     * @param newName 用户名
     * @param newAge  用户年龄
     */
    public void add(String newName, int newAge) {
        //获取一个数据库
        SQLiteDatabase db = helper.getWritableDatabase();

        //String sqla = "insert into person(name,age) values('bbb',44)";
        //这样写会出现SQL虫问题，所以使用Android系统提供的占位符方式写
        String sql = "insert into person(name,age) values(?,?)";

        //db.execSQL(sql,new Object[]{}):使用占位符语句，将占位符你让他在数组中声明
        db.execSQL(sql, new Object[]{newName, newAge});

        // 记住：对数据库而言，有打开，必须有关闭。
        // 在获取数据库后，先写close，以避免忘记关闭
        db.close();
    }

    /**
     * 查找是否存在
     * @param newName:存在用户姓名
     * @return：返回值，要查找用户是否存在
     */
    public boolean find(String newName) {
        //获取一个可读数据库对象，因为不需要对表内容重写
        SQLiteDatabase db = helper.getReadableDatabase();

        //调用对查找方法
        String sql = "select * from person where name=?";

        //返回结果就是一个使用Cursor表示的结果集
        Cursor cursor = db.rawQuery(sql, new String[]{newName});

        //将结果集指针移动到首行记录：如果指针指移动到下一条向记录存在，则返回true，否则返回false
        boolean result = cursor.moveToNext();

        //查询结束后要释放结果集
        cursor.close();
        db.close();
        return result;
    }

    /**
     * 修改一条记录
     * @param name：需要修改记录的name
     * @param age：修改age的新内容
     */
    public void update(String name, int age) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update person set age=? where name=?";

        //注意：数组元素要依据占位符的排序
        db.execSQL(sql, new Object[]{age, name});
        db.close();
    }

    /**
     * 删除一条记录
     * @param name：需要删除记录的name
     */
    public void delete(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from person where name=?";
        db.execSQL(sql, new Object[]{name});
        db.close();
    }

    /**
     * 查找person表中所有记录
     * @return 返回查询到的集合
     */
    public List<Person> findAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Person> list = new ArrayList<>();
        String sql = "select * from person";
        //获取结果集
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
           /*
            使用这种对表属性排序位置查询，如果表属性位置
            改变时，就会出错
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);*/
           /*
            使用表属性索引名进行查询
            */
            int personId = cursor.getInt(cursor.getColumnIndex("personId"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            //创建查找记录的对象：使用new-set方法，或使用工作方法都可
            Person p = new Person(personId, name, age);
            list.add(p);
        }
        cursor.close();
        db.close();
        return list;
    }
}
