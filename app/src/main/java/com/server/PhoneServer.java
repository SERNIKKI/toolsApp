package com.server;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//归属地查询
public class PhoneServer {
    private static String KEY = "4c444d0bd4ac6b8dbdb27ca32fe40dd1";
    //通过POST方式获取HTTP服务器数据
    public String executeHttpGet(String phone){
        HttpURLConnection conn = null;
        InputStream in = null;
        try {
            //http://apis.juhe.cn/mobile/get?phone=13429667914&key=您申请的KEY
            String path = "http://apis.juhe.cn/mobile/get?phone=" + phone + "&key=" + KEY;
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
                if(jsonObject.getInteger("error_code")==0){
                    JSONObject jsonObject1 = JSONObject.parseObject(String.valueOf(jsonObject.get("result")));
                    //province 省份
                    //city 城市
                    //areacode 区号(可能为空)
                    //zip 邮编
                    //company 运营商
                    return String.valueOf(jsonObject.get("result"));
                }else {
                    return "error!";
                }
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
