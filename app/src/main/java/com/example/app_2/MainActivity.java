package com.example.app_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nikki.DBUtils.DBOpenHelper;
import com.nikki.Dao.LoginerDao;
import com.nikki.bean.Loginer;
import com.nikki.bean.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //声明写的DBOpenHelper对象，用来增删改查操作
    private DBOpenHelper dbOpenHelper;
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private Button btn_register;
    private Button btn_other_login;
    private CheckBox checkBox;
    private ImageView login_back;
    private  String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_username = (EditText)findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
        btn_login=(Button)findViewById(R.id.login);
        btn_register = (Button)findViewById(R.id.register);
        btn_other_login = (Button)findViewById(R.id.other_login);
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        login_back = (ImageView)findViewById(R.id.login_back);
        btn_login.setOnClickListener(new MyButton());
        btn_register.setOnClickListener(new MyButton());
        btn_other_login.setOnClickListener(new MyButton());
        login_back.setOnClickListener(new MyButton());
        //实例化对象，用于登陆的数据查询
        dbOpenHelper = new DBOpenHelper(this);
    }
    public class MyButton implements View.OnClickListener{
        @Override
        public void onClick(View view){
            username = et_username.getText().toString().trim();
            password = et_password.getText().toString().trim();
            switch (view.getId()){
                case R.id.login:
                    if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)) {
                        Toast.makeText(MainActivity.this, "账号或者密码不能为空", Toast.LENGTH_SHORT).show();
                    }else {
                        if(checkBox.isChecked()){
                            //保存密码
                        }
                        //开始登陆
                        Toast.makeText(MainActivity.this,"如果点击登录再次打开本页面，请检查账号密码是否有误哦~",Toast.LENGTH_LONG).show();
                        checkLogin();
//                        LoginerDao loginerDao = new LoginerDao();
////                        loginerDao.select_UserPassword(username);
//                        if(password.equals(loginerDao.select_UserPassword(username))){
//                            Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MainActivity.this,Login.class);
//                            startActivity(intent);
//                            finish();
//                        }else {
//                            Toast.makeText(MainActivity.this,"用户名或者密码不正确，请重新输入",Toast.LENGTH_SHORT).show();
//                        }
//                        ArrayList<User> data = dbOpenHelper.getALLData();
//                        System.out.println(data.size());
//                        boolean math = false;
//                        for(int i = 0;i < data.size(); i++){
//                            User user = data.get(i);
//                            Loginer loginer = new Loginer();
//                            loginer.setInput_time();
//                            loginer.setOnly_name(username);
//                            loginer.setUser_account(username);
//                            loginer.setUser_password(password);
//                            LoginerDao loginerDao = new LoginerDao();
//                            if(username.equals(user.getUsername())&&password.equals(user.getPassword())){
//                                try {
//                                    int id = loginerDao.select_id(username);
//                                    if(id!=-1){
//                                        Toast.makeText(MainActivity.this,"账户名重复啦，请重新选一个吧~",Toast.LENGTH_SHORT).show();
//                                    }else {
//                                        if(loginerDao.insert(loginer)) {
//                                            math = true;
//                                            break;
//                                        }
//                                    }
//                                }catch (SQLException e){
//                                    math = false;
//                                }
//                            }else{
//                                math = false;
//                            }
//                        }
//                        if(math){
//                            Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MainActivity.this,Login.class);
//                            startActivity(intent);
//                            finish();
//                        }else {
//                            Toast.makeText(MainActivity.this,"用户名或者密码不正确，请重新输入",Toast.LENGTH_SHORT).show();
//                        }
                    }
                    break;
                case R.id.register:
                    Intent intent = new Intent(MainActivity.this, UserRegister.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.other_login:
                    Intent intent1 = new Intent(MainActivity.this, QQLogin.class);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.login_back:
                    Intent intent2 = new Intent(MainActivity.this,Login.class);
                    startActivity(intent2);
                    finish();
                    break;
            }
        }
    }
    //创建本地文件夹
    public void newDirectory(){
        String paths = getApplicationContext().getExternalFilesDir("").getPath();
        File file = new File(paths + "/" + "Local");
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
        String paths = getApplicationContext().getExternalFilesDir("Local").getPath();
        File file = new File(paths+"/"+"local_user.txt");
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //访问文件
    public void WriteFile(String name,String avatar){
        String paths = getApplicationContext().getExternalFilesDir("Local").getPath();
        String res = name + " " + avatar;
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(paths+"/"+"local_user.txt")));
            writer.write(name + " " + avatar);
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //读取文件
    public String readFile(){
        String paths = getApplicationContext().getExternalFilesDir("Local").getPath();
        String str = "";
        Loginer loginer = new Loginer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(paths+"/"+"local_user.txt")));
            str = reader.readLine();
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return str;
    }
    private void checkLogin(){
        DBThread dt = new DBThread();
        dt.setContext(this);
        Thread thread = new Thread(dt);
        thread.start();
    }
    class DBThread implements Runnable {
        private User user;
        private Context context;

        public void setUser(User user) {
            this.user = user;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            LoginerDao loginerDao = new LoginerDao();
//           loginerDao.select_UserPassword(username);
            if (password.equals(loginerDao.select_UserPassword(username))) {
                if("".equals(readFile())){
                    newDirectory();
                    newFile();
                    WriteFile(username,password);
                }
                Intent intent = new Intent(MainActivity.this, Login.class);
                context.startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                context.startActivity(intent);
                finish();
            }
        }
    }
}
