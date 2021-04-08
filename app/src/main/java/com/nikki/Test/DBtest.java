package com.nikki.Test;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.app_2.R;
import com.nikki.DBUtils.RhesisUtil;
import com.nikki.Dao.LoginerDao;
import com.nikki.bean.Loginer;
import com.nikki.bean.Weather;
import com.server.IPServer;
import com.server.IdiomServer;
import com.server.NOServer;
import com.server.PhoneServer;
import com.server.PoemServer;
import com.server.QRServer;
import com.server.WeatherServer;
import com.server.WebServer;
import com.server.XinHuaServer;

//import org.json.JSONArray;
//import org.json.JSONObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.SQLException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DBtest {
    private static final String TAG = "getURL";
    public static void main(String[] args) {
//        QRServer qrServer = new QRServer();
//        System.out.println(qrServer.executeHttpGet("223.91.104.222"));

//        WeatherServer weatherServer = new WeatherServer();
//        Weather weather = weatherServer.getRequest("广州");
//        System.out.println(weather.toString());

//        NOServer noServer = new NOServer();
//        JSONArray jsonArray = (JSONArray)JSONObject.parse(noServer.executeHttpGet("希望","1"));
//        for (int i = 0; i < jsonArray.size(); i++){
//            System.out.println(jsonArray.get(i));
//        }

//        IdiomServer idiomServer = new IdiomServer();
//        System.out.println(idiomServer.executeHttpGet("卧虎藏龙"));

//        XinHuaServer xinHuaServer = new XinHuaServer();
//        System.out.println(xinHuaServer.executeHttpGet("行"));

//        PhoneServer phoneServer = new PhoneServer();
//        System.out.println(phoneServer.executeHttpGet("13598292803"));

//        PoemServer poemServer = new PoemServer();
//        System.out.println(poemServer.executeHttpGet());

//        for(int i = 0;i< 1000;i++){
//            int r = (int)(1+Math.random()*(215-1+1));
//            String url = "https://cdn.jsdelivr.net/gh/sernikki/MyMp3/yiyan/" + r + ".jpg";
//            System.out.println(url);
//        }

//        WebServer webServer = new WebServer();
//       String str =  webServer.executeHttpGet("NIKKI","sernikki");
//        try {
//            JSONArray jsonArray = (JSONArray) JSONObject.parse(str);;
//            for (int i = 0;i<jsonArray.size();i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String rhesis_book = (String)jsonObject.get("rhesis_book");
//                String rhesis_sentence = (String)jsonObject.get("rhesis_sentence");
//                String rhesis_writer = (String)jsonObject.get("rhesis_writer");
//                System.out.println(rhesis_book + " " + rhesis_sentence + " " + rhesis_writer);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        String[] strings = str.split(", Rhesis");
//        System.out.println(strings.length);
//        for (String s:strings){
//            System.out.println();
//        }

        String url = "https://api.sernikki.cn/api/v1/wyy";
        OkHttpClient okHttpClient =  new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                Log.d(TAG, "onFailure: ");
                System.out.printf("onFailure");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.printf( response.body().string());
//                Log.d(TAG,"onResponse: " + response.body().string());
            }
        });
    }
}
