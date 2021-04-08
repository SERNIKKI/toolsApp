package com.example.app_2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nikki.bean.Rhesis;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemYiYanActivity extends AppCompatActivity {
    private ImageView imageView;
    private ImageView imageView1;
    private TextView text_writer;
    private TextView text_book;
    private TextView text_juzi;
    private String url;
    private Rhesis rhesis;
    private Bitmap bitmap;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yiyan);
        imageView = (ImageView)findViewById(R.id.img_item_yiyan);
        imageView1 = (ImageView)findViewById(R.id.item_yiyan_back);
        text_writer = (TextView)findViewById(R.id.item_yiyan_writer);
        text_book = (TextView)findViewById(R.id.item_yiyan_book);
        text_juzi = (TextView)findViewById(R.id.item_yiyan_juzi);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String[] strings = bundle.getStringArray("rhesis");
        if(strings!=null){
            url = strings[0];
            text_writer.setText(strings[1]);
            text_book.setText(strings[2]);
            text_juzi.setText(strings[3]);
            new Thread(){
                @Override
                public void run(){
                    String x = url;
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
        }else {
            Toast.makeText(ItemYiYanActivity.this,"哎呀~页面消失了~",Toast.LENGTH_SHORT).show();
            text_writer.setText("404");
            text_book.setText("404");
            text_juzi.setText("404");
        }
        imageView1.setOnClickListener(new MyClick());
    }
    class MyClick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent intent = new Intent(ItemYiYanActivity.this,YiYanActivity.class);
            startActivity(intent);
            finish();
        }
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
