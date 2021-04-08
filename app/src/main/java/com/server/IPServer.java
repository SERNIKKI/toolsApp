package com.server;

import com.alibaba.fastjson.JSONObject;
import com.nikki.bean.IP;
import com.nikki.bean.Rhesis;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

//IP地址查询
public class IPServer {
    private static String KEY = "fbefc66a39ca30a356680f3c88423b28";
    //通过POST方式获取HTTP服务器数据
    public IP executeHttpGet(String IP){
        HttpURLConnection conn = null;
        InputStream in = null;
        IP ip = new IP();
        try {
            String path = "http://apis.juhe.cn/ip/ipNew?ip=" + IP + "&key=" + KEY;
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
            System.out.println(resultCode);
            if(HttpURLConnection.HTTP_OK == resultCode) {
                in = conn.getInputStream();
                JSONObject jsonObject = JSONObject.parseObject(parseInfo(in));
                JSONObject jsonObject1 = JSONObject.parseObject(String.valueOf(jsonObject.get("result")));
                ip.setCountry(String.valueOf(jsonObject1.get("Country")));
                ip.setProvince(String.valueOf(jsonObject1.get("Province")));
                ip.setCity(String.valueOf(jsonObject1.get("City")));
                ip.setIsp(String.valueOf(jsonObject1.get("Isp")));
                ip.setIP(IP);
                return ip;
            }
            return null;
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
