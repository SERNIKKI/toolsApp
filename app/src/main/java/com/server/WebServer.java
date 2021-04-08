package com.server;


import com.nikki.bean.Rhesis;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//一言
public class WebServer {
    private static String IP = "49.234.62.191:21067";

    //通过POST方式获取HTTP服务器数据
    public  String executeHttpGet(String username,String userpassword){
        List<Rhesis> list = new ArrayList<>();
        HttpURLConnection conn = null;
        InputStream in = null;

        try {
            String path = "http://" + IP + "/Login";
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
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            String data = "username=" + URLEncoder.encode(username,"UTF-8") + "&password=" + URLEncoder.encode(userpassword,"UTF-8");
            out.writeBytes(data);
            out.flush();
            out.close();
            int resultCode = conn.getResponseCode();
            if(HttpURLConnection.HTTP_OK == resultCode) {
                in = conn.getInputStream();
                return parseInfo(in);
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
