package com.example.app_2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nikki.DBUtils.DBOpenHelper;
import com.nikki.Dao.LoginerDao;
import com.nikki.FileUtil.CreateFiles;
import com.nikki.bean.Loginer;
import com.nikki.bean.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

public class UserRegister extends AppCompatActivity {
    private EditText reg_username;
    private EditText reg_password;
    private EditText reg_passwords;
    private Button reg_sure;
    private Button red_fanhui;
    private DBOpenHelper dbOpenHelper;
    static int id;
    private String username;
    private String password;
    private Loginer loginer = new Loginer();
    static boolean bl = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_ui);
        Toast.makeText(UserRegister.this,"请注意~如果该页面再次打开，请检查账号密码是否没有非法字符或换一个账号名字哦~",Toast.LENGTH_LONG).show();
        reg_username = (EditText) findViewById(R.id.new_user);
        reg_password = (EditText)findViewById(R.id.new_password);
        reg_passwords = (EditText)findViewById(R.id.again_password);
        reg_sure = (Button)findViewById(R.id.new_register);
        red_fanhui = (Button)findViewById(R.id.register_fanhui);
        red_fanhui.setOnClickListener(new MyButton());
        reg_sure.setOnClickListener(new MyButton());
        dbOpenHelper = new DBOpenHelper(this);
    }
    //创建本地文件夹
    public void newDirectory(String Files){
        String paths = getApplicationContext().getExternalFilesDir("").getPath();
        File file = new File(paths + "/" + Files);
        try {
            if (!file.exists()) {
                file.mkdir();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //创建本地文件
    public void newFile(String Files ,String files){
        String paths = getApplicationContext().getExternalFilesDir(Files).getPath();
        File file = new File(paths+"/"+files);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //访问文件
    public void readFile(String user_account,String user_password,String Files ,String files){
        String paths = getApplicationContext().getExternalFilesDir(Files).getPath();
        String res = user_account + " " + user_password;
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(paths+"/"+files)));
            writer.write(user_account + " " + user_password);
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public class MyButton implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.register_fanhui:
                    Intent intent = new Intent(UserRegister.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.new_register:
                    username = reg_username.getText().toString().trim();
                    password = reg_password.getText().toString().trim();
                    String passwords = reg_passwords.getText().toString().trim();
                    if(!password.equals(passwords)){
                        Toast.makeText(UserRegister.this,"密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
                        reg_password.setText("");
                        reg_passwords.setText("");
                    }else if("".equals(username)||"".equals(passwords)) {
                        Toast.makeText(UserRegister.this,"账号或密码为空！请重新输入",Toast.LENGTH_SHORT).show();
                        reg_username.setText("");
                        reg_password.setText("");
                        reg_passwords.setText("");
                    } else{
                        loginer.setInput_time();
                        loginer.setOnly_name(username);
                        loginer.setUser_account(username);
                        loginer.setUser_password(password);
                        checkLogin1();
//                        if(bl){
//                            Toast.makeText(UserRegister.this,"注册成功(●'◡'●)",Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(UserRegister.this,"注册失败∑( 口 ||，请完善信息",Toast.LENGTH_SHORT).show();
//                        }
//                        if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)){
                            //将用户名加入到数据库中

//                            if(username.equals(user.getUsername())&&password.equals(user.getPassword())){
//                                try {
//                                    int id = loginerDao.select_id(username);
//                                    if(id!=-1){
//                                        Toast.makeText(MainActivity.this,"账户名重复啦，请重新选一个吧~",Toast.LENGTH_SHORT).show();
//                            dbOpenHelper.add(username,password);
//                            Intent intent1 = new Intent(UserRegister.this,MainActivity.class);
//                            startActivity(intent1);
//                            finish();
//                            Toast.makeText(UserRegister.this,"注册成功(●'◡'●)",Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(UserRegister.this,"注册失败∑( 口 ||，请完善信息",Toast.LENGTH_SHORT).show();
//                        }
                        break;
                    }

            }
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
                bl = loginerDao.insert(loginer);
                if(bl){
                    newDirectory("Local");
                    newFile("Local","local_user.txt");
                    readFile(username,password,"Local","local_user.txt");
                    Intent intent1 = new Intent(UserRegister.this,Login.class);
                    context.startActivity(intent1);
                    finish();
                }else {
                    Intent intent = new Intent(UserRegister.this,UserRegister.class);
                    startActivity(intent);
                    finish();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
    }

}
