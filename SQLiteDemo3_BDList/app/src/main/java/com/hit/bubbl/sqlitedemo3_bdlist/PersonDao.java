package com.hit.bubbl.sqlitedemo3_bdlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bubbles
 * @create 2018/7/17
 * @Describe
 */
public class PersonDao {
    //创建数据库并在工作方法中完成helper的初始化
    private PersonSqliteHelper helper;

    public PersonDao(Context context) {
        helper = new PersonSqliteHelper(context);
    }

    public long add(String newName, int newAge, int account) {
        //获取一个数据库
        SQLiteDatabase db = helper.getWritableDatabase();

        //使用Android系统提供的sql操作方法实现sql语句的功能，这样可以避免查询sql语句错误
        /*
         * SQLiteDatabase还专门提供了对应于添加、删除、更新、查询的操作方法：
         * insert()、delete()、update()和query() 。
         */

        /*
         * insert(table,nullColumnHack,values)
         * 测试vakues是Map集合类型
         * */
        //创建一个values值
        ContentValues values = new ContentValues();
        values.put("name", newName);
        values.put("age", newAge);
        values.put("account", account);
        //对insert使用sql需要打开表或查询遍历才能看到是否添加成功
        //Android系统提供了可以直接查看insert是否成功，其返回类型为long
        //insert返回类型，如果为大于0则添加成功，-1添加失败
        long id = db.insert("person", null, values);
        db.close();
        return id;
    }

    /**
     * 查找是否存在
     * @param name:存在用户姓名
     * @return：返回值，要查找用户是否存在
     */
    public boolean find(String name) {
        SQLiteDatabase db = helper.getReadableDatabase();
        /*
         * query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit)
         * 方法各参数的含义：
         * table：表名。相当于select语句from关键字后面的部分。如果是多表联合查询，可以用逗号将两个表名分开。
         * columns：要查询出来的列名。相当于select语句select关键字后面的部分。设置为null，查询所有列
         * selection：查询条件子句，相当于select语句where关键字后面的部分，在条件子句允许使用占位符“?”
         * selectionArgs：对应于selection语句中占位符的值，值在数组中的位置与占位符在语句中的位置必须一致，
         * 否则就会有异常。
         * groupBy：相当于select语句group by关键字后面的部分having：
         * 相当于select语句having关键字后面的部分;orderBy：
         * 相当于select语句order by关键字后面的部分，如：personId desc, age asc;
         * limit：指定偏移量和获取的记录数，相当于select语句limit关键字后面的部分。
         * */
        //其返回类型为结果集cursor
        Cursor cursor = db.query("person", null, "name=?", new String[]{name}, null, null, null);
        boolean result = cursor.moveToNext();
        cursor.close();
        db.close();
        return result;
    }

    /**
     * 修改一条记录
     * @param name：需要修改记录的name
     * @param age：修改age的新内容
     */
    public int update(String name, int age, int account) {
        SQLiteDatabase db = helper.getWritableDatabase();
        //db.update("person", values, "personId=?", new String[]{"1"});
        ContentValues values = new ContentValues();
        values.put("age", age);
        values.put("account", account);
        int id = db.update("person", values, "name=?", new String[]{name});
        db.close();
        return id;
    }

    public int delete(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int num = db.delete("person", "name=?", new String[]{name});
        db.close();
        return num;
    }

    /**
     * 查找person表中所有记录
     *
     * @return 返回查询到的集合
     */
    public List<Person> findAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Person> list = new ArrayList<>();
        //如果需要显示则指出显示元素new String[]{"personId","name","age","account"}
        Cursor cursor = db.query("person", new String[]{"personId", "name", "age","account"}, null, null, null, null, null);
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
            int account = cursor.getInt(cursor.getColumnIndex("account"));
            //创建查找记录的对象：使用new-set方法，或使用工作方法都可
            Person p = new Person(personId, name, age, account);
            list.add(p);
        }
        cursor.close();
        db.close();
        return list;
    }
}