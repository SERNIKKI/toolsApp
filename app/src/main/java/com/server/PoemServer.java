package com.server;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//唐诗宋词
public class PoemServer {
    private static String KEY = "be8d7c42da7b4c1da29891593f3882fe";
    //通过POST方式获取HTTP服务器数据,type:1为近义词，2为反义词
    public String executeHttpGet(){
        HttpURLConnection conn = null;
        InputStream in = null;
        try {
            //http://api.avatardata.cn/TangShiSongCi/Search?key=7c15bbbda7a040ca8e88dd21f4b55c5c&keyWord=秋兴
            String path = "http://api.avatardata.cn/TangShiSongCi/Random?key=" + KEY;
            System.out.println(path);
            URL url = new URL(path);
            conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setReadTimeout(8000);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.connect();
            int resultCode = conn.getResponseCode();
            System.out.println(resultCode);
            System.out.println(HttpURLConnection.HTTP_OK);
            if(HttpURLConnection.HTTP_OK == resultCode) {
                in = conn.getInputStream();
                System.out.println(in);
                JSONObject jsonObject = JSONObject.parseObject(parseInfo(in));
//                JSONObject jsonObject1 = JSONObject.parseObject(String.valueOf(jsonObject.get("result")));
                return String.valueOf(jsonObject.get("result"));
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("233");
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
            System.out.println(response.toString());
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
