package com.hit.bubbl.smsapp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Bubbles
 * @create 2018/7/15
 * @Describe
 */

public class IOService {

    public static boolean saveNumber(Context context, String number) {
        // Store the number into the file.
        FileOutputStream fos;
        try {
            fos = context.openFileOutput("numbers.txt", Context.MODE_APPEND);
            fos.write((number + "##").getBytes());
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String[] getNumbers(Context context) {
        File file = new File(context.getFilesDir().toString(), "numbers.txt");
        String[] numbers = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String str = br.readLine();

            numbers = str.split("##");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }
}
