package com.hit.bubbl.weatherapp;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author Bubbles
 * @create 2018/7/13
 * @Describe
 */
public class WeatherService {
    public static ArrayList<WeatherInfo> getWeatherInfo(InputStream is)
            throws Exception {
        ArrayList<WeatherInfo> infos = null;
        WeatherInfo info = null;
        XmlPullParser parser = Xml.newPullParser();
        XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
        //XmlPullParser parser = fac.newPullParser();
        parser.setInput(is, "utf-8");
        int type = parser.getEventType();
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                    if ("infos".equals(parser.getName())) {
                        infos = new ArrayList<>();
                    } else if ("city".equals(parser.getName())) {
                        info = new WeatherInfo();
                        info.setId(Integer.parseInt(parser.getAttributeValue(0)));
                    } else if ("temp".equals(parser.getName())) {
                        info.setTemp(parser.nextText());
                    } else if ("weather".equals(parser.getName())) {
                        info.setWeather(parser.nextText());
                    } else if ("wind".equals(parser.getName())) {
                        info.setWind(parser.nextText());
                    } else if ("name".equals(parser.getName())) {
                        info.setName(parser.nextText());
                    } else if ("pm".equals(parser.getName())) {
                        info.setPm(parser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("city".equals(parser.getName())) {
                        infos.add(info);
                        info = null;
                    }
                    break;
            }
            type = parser.next();
        }
        return infos;
    }
}
