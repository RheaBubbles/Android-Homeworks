package com.hit.bubbl.sharedpreferencesapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Bubbles
 * @create 2018/7/13
 * @Describe
 */
public class LoginService {
    public static void saveInfo(Context context, String username,
                                String password) {
        //config保存的文件名；
        //SharedPreferences接口返回类型
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        //得到一个sp的编辑器
        SharedPreferences.Editor editor = sp.edit();
        //使用编辑器给sp设置参数，类似于Map类型的key-value
        editor.putString("username", username);
        editor.putString("password", password);
        //此提交与对数据库提交操作相同，如果被提交的数据没有完全提交，则回滚。
        editor.commit(); //必须要提交事务
    }
}
