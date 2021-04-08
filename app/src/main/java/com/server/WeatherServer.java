package com.server;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nikki.bean.Weather;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

//天气预报
public class WeatherServer {
    public static final String AppKey = "d2dfd0c572b28bfa4f5b6e99c7e017f9";
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    HashMap<String,String> week = new HashMap<>();
    //根据城市查询天气
    public Weather getRequest(String city){
        week.put("0","星期日");
        week.put("1","星期一");
        week.put("2","星期二");
        week.put("3","星期三");
        week.put("4","星期四");
        week.put("5","星期五");
        week.put("6","星期六");
        String result = null;
        String url = "http://op.juhe.cn/onebox/weather/query";//请求的接口地址
        Map params = new HashMap();
        params.put("cityname",city);//要查询的城市
        params.put("key",AppKey);
        params.put("dtype","json");//返回数据格式，xml或者json
        Weather weather = new Weather();
        try {
            result = net(url,params,"GET");
            JSONObject jsonObject = JSONObject.parseObject(result);
            if(jsonObject.getInteger("error_code")==0){
//                System.out.println(jsonObject.get("result"));
                JSONObject jsonObject1 = JSONObject.parseObject(String.valueOf(jsonObject.get("result")));
                JSONObject jsonObject2 = JSONObject.parseObject(String.valueOf(jsonObject1.get("data")));
                System.out.println(jsonObject2.get("realtime"));
                //获取当前天气状况
                JSONObject jsonObject5 = JSONObject.parseObject(String.valueOf(jsonObject2.get("realtime")));
//                System.out.println("日期:" + jsonObject5.get("date"));
//                System.out.println("城市:" + jsonObject5.get("city_name"));
//                System.out.println("农历:" + jsonObject5.get("moon"));
//                System.out.println("第" + jsonObject5.get("week") + "周");
                weather.setDate(String.valueOf(jsonObject5.get("date")));
                weather.setCity(String.valueOf(jsonObject5.get("city_name")));
                weather.setMoon(String.valueOf(jsonObject5.get("moon")));
                weather.setWeek(week.get(String.valueOf(jsonObject5.get("week"))));
                JSONObject jsonObject6 = JSONObject.parseObject(String.valueOf(jsonObject5.get("weather")));
//                System.out.println("温度:" + jsonObject6.get("temperature") + "℃");
//                System.out.println("湿度:" + jsonObject6.get("humidity"));
//                System.out.println("天气:" + jsonObject6.get("info"));
//                System.out.println("更新时间:" + jsonObject5.get("time"));
                weather.setTemperature(jsonObject6.get("temperature") + "℃");
                weather.setHumidity(String.valueOf(jsonObject6.get("humidity")));
                weather.setInfo(String.valueOf(jsonObject6.get("info")));
                weather.setTime(String.valueOf(jsonObject5.get("time")));
                JSONObject jsonObject7 = JSONObject.parseObject(String.valueOf(jsonObject5.get("wind")));
//                System.out.println("风向:" + jsonObject7.get("direct") + " 风力:" + jsonObject7.get("power"));
                weather.setDirect(String.valueOf(jsonObject7.get("direct")));
                weather.setPower(String.valueOf(jsonObject7.get("power")));

                //获取各项天气指数
                JSONObject jsonObject3 = JSONObject.parseObject(String.valueOf(jsonObject2.get("life")));
                JSONObject jsonObject4 = JSONObject.parseObject(String.valueOf(jsonObject3.get("info")));
                JSONArray jsonArray = (JSONArray)JSONObject.parse(String.valueOf(jsonObject4.get("yundong")));
//                System.out.println("运动:" +  jsonArray.get(0) + " " + jsonArray.get(1));
                weather.setYundong(jsonArray.get(0) + " " + jsonArray.get(1));
                JSONArray jsonArray1 = (JSONArray)JSONObject.parse(String.valueOf(jsonObject4.get("chuanyi")));
//                System.out.println("穿衣:" +  jsonArray1.get(0) + " " + jsonArray1.get(1));
                weather.setChuanyi(jsonArray1.get(0) + " " + jsonArray1.get(1));
                JSONArray jsonArray2 = (JSONArray)JSONObject.parse(String.valueOf(jsonObject4.get("guomin")));
//                System.out.println("过敏:" +  jsonArray2.get(0) + " " + jsonArray2.get(1));
                weather.setGuomin(jsonArray2.get(0) + " " + jsonArray2.get(1));
                JSONArray jsonArray3 = (JSONArray)JSONObject.parse(String.valueOf(jsonObject4.get("daisan")));
//                System.out.println("带伞:" +  jsonArray3.get(0) + " " + jsonArray3.get(1));
                weather.setDaisan(jsonArray3.get(0) + " " + jsonArray3.get(1));
                JSONArray jsonArray4 = (JSONArray)JSONObject.parse(String.valueOf(jsonObject4.get("kongtiao")));
//                System.out.println("空调:" +  jsonArray4.get(0) + " " + jsonArray4.get(1));
                weather.setKongtiao(jsonArray4.get(0) + " " + jsonArray4.get(1));
                JSONArray jsonArray5 = (JSONArray)JSONObject.parse(String.valueOf(jsonObject4.get("ganmao")));
//                System.out.println("感冒:" +  jsonArray5.get(0) + " " + jsonArray5.get(1));
                weather.setGanmao(jsonArray5.get(0) + " " + jsonArray5.get(1));
                JSONArray jsonArray6 = (JSONArray)JSONObject.parse(String.valueOf(jsonObject4.get("ziwaixian")));
//                System.out.println("紫外线:" +  jsonArray6.get(0) + " " + jsonArray6.get(1));
                weather.setZiwaixian(jsonArray6.get(0) + " " + jsonArray6.get(1));
                JSONArray jsonArray7 = (JSONArray)JSONObject.parse(String.valueOf(jsonObject4.get("shushidu")));
//                System.out.println("舒适度:" +  jsonArray7.get(0) + " " + jsonArray7.get(1));
                weather.setShushidu(jsonArray7.get(0) + " " + jsonArray7.get(1));
            }else
                System.out.println(jsonObject.get("error_code") + ":" + jsonObject.get("reason"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return weather;
    }

    public String net(String strUrl,Map params,String method) throws Exception{
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null||method.equals("GET")){
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection)url.openConnection();
            if(method==null||method.equals("GET")){
                conn.setRequestMethod("GET");
            }else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent",userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if(params!=null&&method.equals("POST")){
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is,DEF_CHATSET));
            String strRead = null;
            while ((strRead=reader.readLine())!=null){
                sb.append(strRead);
            }
            rs = sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null)
                reader.close();
            if(conn!=null)
                conn.disconnect();
        }
        return rs;
    }

    //将map型转换为请求参数型
    public String urlencode(Map<String,String> data){
        StringBuffer sb = new StringBuffer();
        for (Map.Entry i:data.entrySet()){
            try{
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
