package com.hit.bubbl.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_info = findViewById(R.id.tv_info);

        try {
            ArrayList<WeatherInfo> infos = WeatherService
                    .getWeatherInfo(MainActivity.class.getClassLoader()
                            .getResourceAsStream("assets/weather.xml"));
            StringBuffer sb = new StringBuffer();
            for (WeatherInfo info : infos) {
                sb.append(info);
                sb.append("\n");
            }
            tv_info.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "xml解析失败", Toast.LENGTH_SHORT).show();
        }
    }
}
