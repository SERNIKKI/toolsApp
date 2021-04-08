package com.example.app_2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.nikki.FileUtil.CreateFiles;
import com.nikki.adapter.YiYanAdapter;
import com.nikki.bean.Loginer;
import com.server.WeatherServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Login extends AppCompatActivity {
    private WebView webView;
    private ImageView mImageView;
    private ImageButton img_home;
    private ImageButton img_write;
    private ImageButton img_toolkit;
    private ImageButton img_steam;
    private ImageButton img_about;
    private ImageButton img_break;
    private ImageButton img_login;
    private Bitmap bitmap;
    private CircleImageView circleImageView;
    private TextView name;
    private String imgs;
    private String user_name;
    //设置透明度
    Float alpha = new Float(0.5);
    //关闭程序的全局变量
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_ui);
        img_home = (ImageButton) findViewById(R.id.img_home);
        img_write = (ImageButton) findViewById(R.id.img_write);
        img_toolkit = (ImageButton)findViewById(R.id.img_toolkit);
        img_steam = (ImageButton)findViewById(R.id.img_steam);
        img_about = (ImageButton)findViewById(R.id.img_about);
        img_break = (ImageButton) findViewById(R.id.img_break);
        img_login = (ImageButton)findViewById(R.id.img_login);
        name = (TextView)findViewById(R.id.name);
//        new Thread(new MyTherd()).start();
        //判断本地文件夹是否有数据
        if("".equals(readFile())&&"".equals(readLocalFile())){
            imgs = "-1";
            name.setText("NIKKI");
        } else if(!"".equals(readFile())&&!"".equals(readLocalFile())){
            String str = readFile();
            imgs = str.split(" ")[1];
            user_name = str.split(" ")[0];
            name.setText(user_name);
        } else if(!"".equals(readFile())){
            String str = readFile();
            imgs = str.split(" ")[1];
            user_name = str.split(" ")[0];
        }else if(!"".equals(readLocalFile())){
            String str = readLocalFile();
            imgs = "-1";
            user_name = str.split(" ")[0];
            name.setText(user_name);
        }
        //加载网页
        webView = findViewById(R.id.webView2);
        webView.setWebViewClient(new WebViewClient());
        //可执行js
        webView.getSettings().setJavaScriptEnabled(true);
        //设置缓存
        webView.getSettings().setAppCacheEnabled(true);
        //设置缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //支持缩放
        webView.getSettings().setSupportZoom(true);
        //调整图片到合适大小
        webView.getSettings().setUseWideViewPort(true);
        //支持内容重新布局
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置可以被显示的屏幕控制
        webView.getSettings().setDisplayZoomControls(true);
        //设置默认字体大小
        webView.getSettings().setDefaultFontSize(12);
        //可执行js脚本
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);//DOM Storage 重点是设置这个;
        webView.loadUrl("https://blog.sernikki.cn");
        final DrawerLayout drawerLayout=findViewById(R.id.drawerLayout);
        //给按钮添加一个监听器
        findViewById(R.id.top_view_left_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开侧滑菜单
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        new Thread(){
            @Override
            public void run(){
                try {
                    URL url = new URL(imgs);
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
//        final CircleImageView circleImageView = (CircleImageView)findViewById(R.id.roundview);
        //侧边栏事件
        img_home.setOnClickListener(new MyStream());
        img_write.setOnClickListener(new MyStream());
        img_break.setOnClickListener(new MyStream());
        img_login.setOnClickListener(new MyStream());
        img_steam.setOnClickListener(new MyStream());
        img_about.setOnClickListener(new MyStream());
        img_toolkit.setOnClickListener(new MyStream());
        //主页面事件
    }
    //监听侧边栏事件
    public class MyStream implements View.OnClickListener{
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.img_home:
                    LinearLayout linearLayout = (LinearLayout)findViewById(R.id.home);
                    linearLayout.setAlpha(alpha);
                    if(!"".equals(readFile())||!"".equals(readLocalFile())){
                        Toast.makeText(Login.this,"欢迎" + name.getText() + "~",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, YiYanActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(Login.this,"请登陆后使用此功能哦~",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
//                    Intent intent = new Intent(Login.this,MainActivity.class);
//                    startActivity(intent);
                    break;
                case R.id.img_write:
                    LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.write);
                    linearLayout1.setAlpha(alpha);
                    Intent intent1 = new Intent(Login.this,NotepadActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.img_toolkit:
                    LinearLayout linearLayout2 = (LinearLayout)findViewById(R.id.toolkit);
                    linearLayout2.setAlpha(alpha);
                    if(!"".equals(readFile())||!"".equals(readLocalFile())){
                        Toast.makeText(Login.this,"欢迎" + name.getText() + "~",Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(Login.this,ToolkitActivity.class);
                        startActivity(intent2);
                        finish();
                    }else {
                        Toast.makeText(Login.this,"请登陆后使用此功能哦~",Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(Login.this,MainActivity.class);
                        startActivity(intent2);
                        finish();
                    }
                    break;
                case R.id.img_steam:
                    LinearLayout linearLayout4 = (LinearLayout)findViewById(R.id.steam);
                    linearLayout4.setAlpha(alpha);
                    Intent intent4 = new Intent(Login.this,SteamActivity.class);
                    startActivity(intent4);
                    finish();
                    break;
                case R.id.img_about:
                    LinearLayout linearLayout5 = (LinearLayout)findViewById(R.id.about);
                    linearLayout5.setAlpha(alpha);
                    Intent intent5 = new Intent(Login.this,AboutActivity.class);
                    startActivity(intent5);
                    finish();
                    break;
                case R.id.img_break:
                    LinearLayout linearLayout6 = (LinearLayout)findViewById(R.id.breaks);
                    linearLayout6.setAlpha(alpha);
                    finish();
                    break;
                case R.id.img_login:
                    LinearLayout linearLayout7 = (LinearLayout)findViewById(R.id.login);
                    linearLayout7.setAlpha(alpha);
                    if(!"".equals(readFile())||!"".equals(readLocalFile())){
                        Toast.makeText(Login.this,"您已经登陆过啦~",Toast.LENGTH_SHORT).show();
                        linearLayout7.setAlpha(1);
                        break;
                    }else{
                        Intent intent7 = new Intent(Login.this,MainActivity.class);
                        startActivity(intent7);
                        finish();
                    }
                    break;
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) ) {
            if (webView.canGoBack())
            {
                webView.goBack(); //goBack()表示返回WebView的上一页面
                return true;
            }else
            {
                finish();
                return true;
            }
        }
        return false;
    }
    //Handler异步方式下载图片
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){
                ImageView imageView;
                switch (msg.what){
                    case 1:
                        //下载成功
                        circleImageView = (CircleImageView)findViewById(R.id.roundview);
                        circleImageView.setImageBitmap(bitmap);
                        break;
                    case -1:
                        //下载失败使用默认图片
                        circleImageView = (CircleImageView)findViewById(R.id.roundview);
                        circleImageView.setImageResource(R.drawable.img_1);
                        break;
                }
        };
    };
    //访问Login文件
    public String readFile(){
        String paths = getApplicationContext().getExternalFilesDir("NIKKI").getPath();
        String str = "";
        Loginer loginer = new Loginer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(paths+"/"+"Login.txt")));
            str = reader.readLine();
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return str;
    }
    //读取local文件
    public String readLocalFile(){
        String paths = getApplicationContext().getExternalFilesDir("Local").getPath();
        String str = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(paths+"/"+"local_user.txt")));
            str = reader.readLine();
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return str;
    }
}
