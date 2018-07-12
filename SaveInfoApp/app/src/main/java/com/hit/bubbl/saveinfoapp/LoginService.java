package com.hit.bubbl.saveinfoapp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bubbles
 * @create 2018/7/12
 * @Describe
 */
public class LoginService {
    public static boolean saveUserInfo(String username, String password, Context context, int mode) {
        //创建保存文件和保存路径
        try {
            FileOutputStream fos = null;
            switch (mode) {
                case 1:
                    fos = context.openFileOutput("private.txt", Context.MODE_PRIVATE);
                    break;
                case 2:
                    fos = context.openFileOutput("read.txt", Context.MODE_WORLD_READABLE);
                    break;
                case 3:
                    fos = context.openFileOutput("write.text", Context.MODE_WORLD_WRITEABLE);
                    break;
                case 4:
                    fos = context.openFileOutput("public.text", Context.MODE_WORLD_WRITEABLE
                        + Context.MODE_WORLD_READABLE);
                    break;
                default:
                    break;
            }
            fos.write((username + "##" + password).getBytes());
            fos.close();
            //没有出现异常,即保存成功
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常,即保存失败
            return false;
        }
    }

    public static Map<String, String> getSaveUserInfo(String path) {
        File file = new File(path, "info.txt");
        try {
            FileInputStream fis = new FileInputStream(file);

            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String str = br.readLine();

            String[] info = str.split("##");

            Map<String, String> map = new HashMap<>();

            map.put("username", info[0]);
            map.put("password", info[1]);

            return map;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

