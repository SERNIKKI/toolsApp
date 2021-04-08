package com.example.app_2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nikki.adapter.YiYanAdapter;
import com.nikki.bean.Rhesis;
import com.server.WebServer;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class YiYanActivity extends AppCompatActivity {
    private RecyclerView recyclerView;//声明
    private YiYanAdapter yiYanAdapter;//声明适配器
    private Context context;
    private List<Rhesis> list;
    private ImageView yiyan_back;
    private PopupWindow popupWindow;
    private Bitmap bitmap;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.yan_activity);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        yiyan_back = (ImageView)findViewById(R.id.yiyan_back);
        list = new ArrayList<>();
        new Thread(new RegThread()).start();
//        for(int i = 0;i<100;i++){
//            list.add("这是第" + i + "个测试");
//        }
        yiyan_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YiYanActivity.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public class RegThread implements Runnable{
        @Override
        public void run(){
            WebServer webServer = new WebServer();
            String str =  webServer.executeHttpGet("NIKKI","sernikki");
            showReq(str);
        }
    }
    private void showReq(final String str){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONArray jsonArray = (JSONArray) JSONObject.parse(str);;
                    for (int i = 0;i<jsonArray.size();i++) {
                        final Rhesis rhesis = new Rhesis();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String rhesis_writer = (String)jsonObject.get("rhesis_writer");
                        String rhesis_book = (String)jsonObject.get("rhesis_book");
                        String rhesis_sentence = (String)jsonObject.get("rhesis_sentence");
                        int r = (int)(1+Math.random()*(215-1+1));
                        final String url = "https://cdn.jsdelivr.net/gh/sernikki/MyMp3/yiyan/" + r + ".jpg";
                        rhesis.setRhesis_book(rhesis_book);
                        rhesis.setRhesis_writer(rhesis_writer);
                        rhesis.setRhesis_sentence(rhesis_sentence);
                        rhesis.setUrl(url);
                        list.add(rhesis);
                        yiYanAdapter = new YiYanAdapter(context,list);
                        LinearLayoutManager manager = new LinearLayoutManager(context);
                        recyclerView.setLayoutManager(manager);
                        yiYanAdapter.setOnItemClickListener(new YiYanAdapter.OnItemClickListener() {
                            @Override
                            public void OnItemClick(View v, Rhesis rhesis1 ,int position) {
                                try {
                                    Thread.sleep(500);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                showNoneEffect(rhesis1);
//                                Intent intent = new Intent();
//                                Bundle bundle = new Bundle();
//                                intent.setClass(YiYanActivity.this,ItemYiYanActivity.class);
//                                String[] strings = new String[]{rhesis1.getUrl(),rhesis1.getRhesis_writer(),rhesis1.getRhesis_book(),rhesis1.getRhesis_sentence()};
//                                bundle.putStringArray("rhesis",strings);
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                                //Toast.makeText(YiYanActivity.this,rhesis1.getRhesis_writer() + " " + rhesis1.getUrl(),Toast.LENGTH_SHORT).show();
                            }
                        });
                        recyclerView.setAdapter(yiYanAdapter);
//                System.out.println(rhesis_book + " " + rhesis_sentence + " " + rhesis_writer);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    //书写弹窗
    private void showNoneEffect(final Rhesis rhesis){
        TextView text_writer;
        TextView text_book;
        TextView text_juzi;
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //引入弹窗布局
        View vPopupWindow = inflater.inflate(R.layout.activity_yiyan,null,false);
        popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT);
        //设置透明背景
        addBackground();
        //设置进出动画
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        //设置响应外部点击事件
        popupWindow.setOutsideTouchable(true);
        //设置能否响应点击事件
        popupWindow.setTouchable(true);
        //引入依赖布局
        View parentView = LayoutInflater.from(YiYanActivity.this).inflate(R.layout.activity_yiyan,null);
        //相对于父控件的位置
        popupWindow.showAtLocation(parentView, Gravity.CENTER,0,0);
        ImageView back = vPopupWindow.findViewById(R.id.item_yiyan_back);
        text_writer = vPopupWindow.findViewById(R.id.item_yiyan_writer);
        text_book = vPopupWindow.findViewById(R.id.item_yiyan_book);
        text_juzi = vPopupWindow.findViewById(R.id.item_yiyan_juzi);
        imageView = vPopupWindow.findViewById(R.id.img_item_yiyan);
        text_writer.setText(rhesis.getRhesis_writer());
        text_book.setText(rhesis.getRhesis_book());
        text_juzi.setText(rhesis.getRhesis_sentence());
        new Thread(){
            @Override
            public void run(){
                String x = rhesis.getUrl();
                try {
                    URL url = new URL(x);
                    //建立网络连接
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    InputStream inputStream = conn.getInputStream();
                    //获取图片数据
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    Message message = new Message();
                    message.what = 1;
                    //发送消息到队列
                    handler.sendMessage(message);
                }catch (Exception e){
                    Message message = new Message();
                    message.what = -1;
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        }.start();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

    }
    //设置透明背景
    private void addBackground(){
        //设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;//调节透明度
                getWindow().setAttributes(lp);
            }
        });
    }
    //Handler异步方式下载图片
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case 1:
                    //下载成功
                    imageView.setImageBitmap(bitmap);
                    break;
                case -1:
                    //下载失败使用默认图片
                    imageView.setImageResource(R.drawable.img_1);
                    break;
            }
        };
    };
}
