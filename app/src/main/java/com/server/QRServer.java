package com.server;

import com.alibaba.fastjson.JSONObject;
import com.nikki.bean.IP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//文字转二维码
public class QRServer {
    private static String KEY = "959f61ecb9bcd5a81d76c9d87fb9d269";
    //通过POST方式获取HTTP服务器数据
    public String executeHttpGet(String text){
        HttpURLConnection conn = null;
        InputStream in = null;
        try {
            //http://apis.juhe.cn/qrcode/api?key=&type=2&fgcolor=00b7ee&w=90&m=5&text=hello%20world!
            String path = "http://apis.juhe.cn/qrcode/api?key=" + KEY + "&type=1&fgcolor=00b7ee&w=90&m=5&text=" + text;
            System.out.println(path);
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
                return String.valueOf(jsonObject1.get("base64_image"));
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
