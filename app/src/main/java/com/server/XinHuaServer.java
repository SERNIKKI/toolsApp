package com.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//新华字典
public class XinHuaServer {
    private static String KEY = "9b3e03339997c5b03056390bd2141794";
    //通过POST方式获取HTTP服务器数据
    public String executeHttpGet(String word){
        HttpURLConnection conn = null;
        InputStream in = null;
        try {
            //http://apis.juhe.cn/tyfy/query?key=xxxxx&word=%E6%9C%9F%E5%BE%85&type=2
            String path = "http://v.juhe.cn/xhzd/query?key=" + KEY + "&word=" + word;
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
                    //字
                    System.out.println(String.valueOf(jsonObject1.get("zi")));
                    //五笔
                    System.out.println(String.valueOf(jsonObject1.get("wubi")));
                    //拼音
                    System.out.println(String.valueOf(jsonObject1.get("pinyin")));
                    //部首
                    System.out.println(String.valueOf(jsonObject1.get("bushou")));
                    //笔划
                    System.out.println(String.valueOf(jsonObject1.get("bihua")));
                    //简单解释
//                    System.out.println(String.valueOf(jsonObject1.get("jijie")));
                    //详解
//                    System.out.println(String.valueOf(jsonObject1.get("xiangjie")));
                    JSONArray jsonArray = jsonObject1.getJSONArray("xiangjie");
                    for(int i = 0;i<jsonArray.size();i++){
                        System.out.println(jsonArray.get(i));
                    }
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
