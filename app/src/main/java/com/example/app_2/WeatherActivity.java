package com.example.app_2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nikki.bean.Weather;
import com.server.WeatherServer;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {
    private EditText editText;
    private ImageView back;
    private Button button;
    private TextView weather_city;
    private TextView weather_date;
    private TextView weather_week;
    private TextView weather_warm;
    private TextView weather_weather;
    private TextView weather_fengli;
    private TextView yundong;
    private TextView chuanyi;
    private TextView daisan;
    private TextView ganmao;
    private TextView ziwaixian;
    private TextView shushidu;
    private LinearLayout linearLayout;
    public static String city;
    public static Weather weather = new Weather();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        editText = (EditText)findViewById(R.id.weather_city);
        button = (Button)findViewById(R.id.weather_chaxun);
        weather_city = (TextView)findViewById(R.id.weather_tx_city);
        weather_date = (TextView)findViewById(R.id.weather_date);
        weather_week = (TextView)findViewById(R.id.weather_week);
        weather_warm = (TextView)findViewById(R.id.weather_warm);
        weather_fengli = (TextView)findViewById(R.id.weather_fengli);
        weather_weather = (TextView)findViewById(R.id.weather_weather);
        yundong = (TextView)findViewById(R.id.weather_yundong);
        chuanyi = (TextView)findViewById(R.id.weather_chaunyi);
        daisan = (TextView)findViewById(R.id.weather_daisan);
        ganmao = (TextView)findViewById(R.id.weather_ganmao);
        ziwaixian = (TextView)findViewById(R.id.weather_ziwaixian);
        shushidu = (TextView)findViewById(R.id.weather_shushidu);
        back = (ImageView)findViewById(R.id.weather_back);
        linearLayout = (LinearLayout)findViewById(R.id.activity_weather);

        button.setOnClickListener(new MyClick());
        back.setOnClickListener(new MyClick());
    }
    class MyClick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.weather_chaxun:
                    new Thread(new MyTherd()).start();
                    break;
                case R.id.weather_back:
                    Intent intent = new Intent(WeatherActivity.this,ToolkitActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }
    public class MyTherd implements Runnable {
        @Override
        public void run() {
            WeatherServer weatherServer = new WeatherServer();
            city = editText.getText().toString().trim();
            weather = weatherServer.getRequest(city);
            showReq();
        }
    }
    private void showReq(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if("晴".equals(weather.getInfo())){
                    linearLayout.setBackgroundResource(R.drawable.fine);
                    yundong.setTextColor(getResources().getColor(R.color.black));
                    chuanyi.setTextColor(getResources().getColor(R.color.black));
                    daisan.setTextColor(getResources().getColor(R.color.black));
                    ganmao.setTextColor(getResources().getColor(R.color.black));
                    ziwaixian.setTextColor(getResources().getColor(R.color.black));
                    shushidu.setTextColor(getResources().getColor(R.color.black));
                }else if("多云".equals(weather.getInfo())||"阴".equals(weather.getInfo())){
                    linearLayout.setBackgroundResource(R.drawable.cloudy);
                    yundong.setTextColor(getResources().getColor(R.color.white));
                    chuanyi.setTextColor(getResources().getColor(R.color.white));
                    daisan.setTextColor(getResources().getColor(R.color.white));
                    ganmao.setTextColor(getResources().getColor(R.color.white));
                    ziwaixian.setTextColor(getResources().getColor(R.color.white));
                    shushidu.setTextColor(getResources().getColor(R.color.white));
                }else if("阵雨".equals(weather.getInfo())||"雷阵雨".equals(weather.getInfo())||"雷阵雨伴有冰雹".equals(weather.getInfo())){
                    linearLayout.setBackgroundResource(R.drawable.shower);
                    yundong.setTextColor(getResources().getColor(R.color.black));
                    chuanyi.setTextColor(getResources().getColor(R.color.black));
                    daisan.setTextColor(getResources().getColor(R.color.black));
                    ganmao.setTextColor(getResources().getColor(R.color.black));
                    ziwaixian.setTextColor(getResources().getColor(R.color.black));
                    shushidu.setTextColor(getResources().getColor(R.color.black));
                }else if("小雨".equals(weather.getInfo())||"中雨".equals(weather.getInfo())||"大雨".equals(weather.getInfo())||"小到中雨".equals(weather.getInfo())||"中到大雨".equals(weather.getInfo())){
                    linearLayout.setBackgroundResource(R.drawable.rain);
                    yundong.setTextColor(getResources().getColor(R.color.white));
                    chuanyi.setTextColor(getResources().getColor(R.color.white));
                    daisan.setTextColor(getResources().getColor(R.color.white));
                    ganmao.setTextColor(getResources().getColor(R.color.white));
                    ziwaixian.setTextColor(getResources().getColor(R.color.white));
                    shushidu.setTextColor(getResources().getColor(R.color.white));
                }else if("暴雨".equals(weather.getInfo())||"大暴雨".equals(weather.getInfo())||"特大暴雨".equals(weather.getInfo())||"大到暴雨".equals(weather.getInfo())||"暴雨到大暴雨".equals(weather.getInfo())||"大暴雨到特大暴雨".equals(weather.getInfo())){
                    linearLayout.setBackgroundResource(R.drawable.heavy_rain);
                    yundong.setTextColor(getResources().getColor(R.color.white));
                    chuanyi.setTextColor(getResources().getColor(R.color.white));
                    daisan.setTextColor(getResources().getColor(R.color.white));
                    ganmao.setTextColor(getResources().getColor(R.color.white));
                    ziwaixian.setTextColor(getResources().getColor(R.color.white));
                    shushidu.setTextColor(getResources().getColor(R.color.white));
                }else if("雨夹雪".equals(weather.getInfo())||"阵雪".equals(weather.getInfo())||"小雪".equals(weather.getInfo())||"小到中雪".equals(weather.getInfo())){
                    linearLayout.setBackgroundResource(R.drawable.light_snow);
                    yundong.setTextColor(getResources().getColor(R.color.white));
                    chuanyi.setTextColor(getResources().getColor(R.color.white));
                    daisan.setTextColor(getResources().getColor(R.color.white));
                    ganmao.setTextColor(getResources().getColor(R.color.white));
                    ziwaixian.setTextColor(getResources().getColor(R.color.white));
                    shushidu.setTextColor(getResources().getColor(R.color.white));
                }else if("中雪".equals(weather.getInfo())||"大雪".equals(weather.getInfo())||"中到大雪".equals(weather.getInfo())){
                    linearLayout.setBackgroundResource(R.drawable.snow);
                    yundong.setTextColor(getResources().getColor(R.color.black));
                    chuanyi.setTextColor(getResources().getColor(R.color.black));
                    daisan.setTextColor(getResources().getColor(R.color.black));
                    ganmao.setTextColor(getResources().getColor(R.color.black));
                    ziwaixian.setTextColor(getResources().getColor(R.color.black));
                    shushidu.setTextColor(getResources().getColor(R.color.black));
                }else if("暴雪".equals(weather.getInfo())||"大到暴雪".equals(weather.getInfo())){
                    linearLayout.setBackgroundResource(R.drawable.moderate_snow);
                    yundong.setTextColor(getResources().getColor(R.color.white));
                    chuanyi.setTextColor(getResources().getColor(R.color.white));
                    daisan.setTextColor(getResources().getColor(R.color.white));
                    ganmao.setTextColor(getResources().getColor(R.color.white));
                    ziwaixian.setTextColor(getResources().getColor(R.color.white));
                    shushidu.setTextColor(getResources().getColor(R.color.white));
                }
                weather_city.setText(weather.getCity());
                weather_date.setText(weather.getDate());
                weather_fengli.setText(weather.getDirect() + "" + weather.getPower());
                weather_warm.setText(weather.getTemperature());
                weather_weather.setText(weather.getInfo());
                weather_week.setText(weather.getWeek());
                yundong.setText("运动指数: " + weather.getYundong());
                chuanyi.setText("穿衣指数: " + weather.getChuanyi());
                daisan.setText("外出带伞: " + weather.getDaisan());
                ganmao.setText("感冒情况: " + weather.getGanmao());
                ziwaixian.setText("紫外线情况: " + weather.getZiwaixian());
                shushidu.setText("舒适度: " + weather.getShushidu());
            }
        });
    }
}
