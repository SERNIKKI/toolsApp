package com.example.app_2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nikki.Dao.LoginerDao;
import com.nikki.bean.Loginer;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.List;

public class QQLogin extends AppCompatActivity {
    private static final String TAG = "QQLogin";
    private static final String PACKAGE_QQ = "com.tencent.mobileqq";
    public static String avatar;
    public static String nickname;
    //展示个人信息
    private TextView infoName;
    private ImageView infoIcon;
    //初始化腾讯业务
    private Tencent tencent;
    private Loginer loginer;
    private LoginerDao loginerDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq_login);
        tencent = Tencent.createInstance("101865160",this);
        infoIcon = this.findViewById(R.id.info_icon);
        infoName = this.findViewById(R.id.text_icon);
        //登陆点击事件
        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                手机未安装应用，则会跳转到网页进行授权
                if(!hasapp(QQLogin.this,PACKAGE_QQ)){
                    Toast.makeText(QQLogin.this,"未安装QQ应用",Toast.LENGTH_SHORT).show();
                    return;
                }
                //如果session无效，就开始登陆操作
                if(!tencent.isSessionValid()){
                    loginQQ();
                }
            }
        });
        //返回点击事件
        findViewById(R.id.qq_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QQLogin.this,Login.class);
                startActivity(intent);
                Toast.makeText(QQLogin.this,"登陆失败",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    //qq登陆
    private IUiListener listener;

    private void loginQQ() {
        listener = new IUiListener() {
            @Override
            public void onComplete(Object object) {
                Log.e(TAG, "登陆成功：" + object.toString());
                JSONObject jsonObject = (JSONObject) object;
                try {
                    //得到token，exprice，openId等参数
                    String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                    String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                    String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
                    tencent.setAccessToken(token, expires);
                    tencent.setOpenId(openId);
                    Log.e(TAG, "token: " + token);
                    Log.e(TAG, "expires: " + expires);
                    Log.e(TAG, "openId: " + openId);
                    //获取个人信息
                    getQQInfo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
                //登录失败
                Log.e(TAG, "登录失败" + uiError.errorDetail);
                Log.e(TAG, "登录失败" + uiError.errorMessage);
                Log.e(TAG, "登录失败" + uiError.errorCode + "");
            }

            @Override
            public void onCancel() {
                //登录取消
                Log.e(TAG, "登录取消");
            }
        };
        //context上下文、第二个参数SCOPO 是一个String类型的字符串，表示一些权限
        //应用需要获得权限，由“,”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
        //第三个参数事件监听器
        tencent.login(this,"all",listener);
        //注销登录
        //tencent.logout(this);
    }
    //获取个人信息
    private void getQQInfo(){
        //获取基本信息
        QQToken qqToken = tencent.getQQToken();
        final UserInfo info = new UserInfo(this,qqToken);
        info.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object object) {
                try {
                    Log.e(TAG,"个人信息：" + object.toString());
//                    infoText.setText(object.toString());
                    //头像
                    avatar = ((JSONObject)object).getString("figureurl_2");
                    nickname = ((JSONObject)object).getString("nickname");
                    //文件操作
                    newDirectory();
                    newFile();
                    readFile(nickname,avatar);
                    infoName.setText(nickname);
                    checkLogin1();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
            }

            @Override
            public void onCancel() {
            }
        });
    }
    //回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,requestCode,data);
        //腾讯qq回调
        Tencent.onActivityResultData(requestCode,resultCode,data,listener);
        if(requestCode == Constants.REQUEST_API){
            if(resultCode == Constants.REQUEST_LOGIN){
                Tencent.handleResultData(data,listener);
            }
        }
    }
    //安装了相应的app
    private boolean hasapp(Context context, String packname){
        boolean is = false;
        List<PackageInfo> packages = context.getPackageManager()
                .getInstalledPackages(0);
        for(int i = 0;i < packages.size(); i++){
            PackageInfo packageInfo = packages.get(i);
            String packageName = packageInfo.packageName;
            if(packageName.equals(packname)){
                is = true;
            }
        }
        return is;
    }
    //创建本地文件夹
    public void newDirectory(){
        String paths = getApplicationContext().getExternalFilesDir("").getPath();
        File file = new File(paths + "/" + "NIKKI");
        try {
            if (!file.exists()) {
                file.mkdir();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //创建本地文件
    public void newFile(){
        String paths = getApplicationContext().getExternalFilesDir("NIKKI").getPath();
        File file = new File(paths+"/"+"Login.txt");
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //访问文件
    public void readFile(String name,String avatar){
        String paths = getApplicationContext().getExternalFilesDir("NIKKI").getPath();
        File file = new File(paths+"/"+"Login.txt");
        String res = name + " " + avatar;
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(paths+"/"+"Login.txt")));
            writer.write(name + " " + avatar);
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void checkLogin1(){
        DBThread1 dt = new DBThread1();
        dt.setContext(this);
        Thread thread = new Thread(dt);
        thread.start();
    }
    class DBThread1 implements Runnable {
        private Context context;
        public void setContext(Context context) {
            this.context = context;
        }
        @Override
        public void run() {
            LoginerDao loginerDao = new LoginerDao();
            try {
                loginer = new Loginer();
                loginer.setInput_time();
                loginer.setOnly_name(nickname);
                loginer.setUser_account(nickname);
                loginer.setAvatar(avatar);
                loginerDao.insert(loginer);
                Intent intent = new Intent();
                intent.setClass(QQLogin.this,Login.class);
                QQLogin.this.startActivity(intent);
                finish();
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
    }
}
