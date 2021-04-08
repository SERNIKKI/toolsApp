package com.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//查询近反义词
public class NOServer {
    private static String KEY = "da084459ed56cf9bc10b8726dea59c70";
    //通过POST方式获取HTTP服务器数据,type:1为近义词，2为反义词
    public String executeHttpGet(String word,String type){
        HttpURLConnection conn = null;
        InputStream in = null;
        try {
            //http://apis.juhe.cn/tyfy/query?key=xxxxx&word=%E6%9C%9F%E5%BE%85&type=2
            String path = "http://apis.juhe.cn/tyfy/query?key=" + KEY + "&word=" + word + "&type=" + type;
//            System.out.println(path);
            URL url = new URL(path);
            conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(8000);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.connect();
            int resultCode = conn.getResponseCode();
            if(HttpURLConnection.HTTP_OK == resultCode) {
                in = conn.getInputStream();
                JSONObject jsonObject = JSONObject.parseObject(parseInfo(in));
                JSONObject jsonObject1 = JSONObject.parseObject(String.valueOf(jsonObject.get("result")));
                return String.valueOf(jsonObject1.get("words"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static String parseInfo(InputStream inputStream){
        BufferedReader reader = null;
        String line = "";
        StringBuilder response = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while((line = reader.readLine()) != null){
                response.append(line);
            }
            return response.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
