package com.example.bubbl.packetchinaapp;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataService {

    public static List<ProvinceInfo> getProvincesInfo(Context context) {

        ArrayList<ProvinceInfo> infos = null;
        // read the xml file
//        File file = new File(context.getFilesDir().toString() + "/provinces.xml");

        // Parse the xml
        XmlPullParser parser = Xml.newPullParser();
        try {
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
//            InputStream inputStream = new FileInputStream(file);
            InputStream inputStream = context.getClassLoader()
                    .getResourceAsStream("assets/provinces.xml");
            parser.setInput(inputStream, "utf-8");
            int type = parser.getEventType();
            ProvinceInfo info = null;
            String name = null;
            String url = null;
            int id = 0;
            while (type != XmlPullParser.END_DOCUMENT) {
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if ("infos".equals(parser.getName())) {
                            infos = new ArrayList<>();
                        } else if ("province".equals(parser.getName())) {
                            id = Integer.parseInt(parser.getAttributeValue(0));
                        } else if ("name".equals(parser.getName())) {
                            name = parser.nextText();
                        } else if ("url".equals(parser.getName())) {
                            url = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("province".equals(parser.getName())) {
                            info = new ProvinceInfo(id, name, url);
                            infos.add(info);
                            info = null;
                        }
                        break;
                }
                type = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return infos;
    }
}
