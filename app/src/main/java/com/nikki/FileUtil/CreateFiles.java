package com.nikki.FileUtil;


import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nikki.bean.Loginer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

public class CreateFiles {
    private AppCompatActivity context;
    CreateFiles(AppCompatActivity app){
        this.context = app;
    }
    //创建本地文件夹
    public void newDirectory(String Files){
        String paths = context.getApplicationContext().getExternalFilesDir("").getPath();
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
        String paths = context.getApplicationContext().getExternalFilesDir(Files).getPath();
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
        String paths = context.getApplicationContext().getExternalFilesDir(Files).getPath();
        String res = user_account + " " + user_password;
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(paths+"/"+files)));
            writer.write(user_account + " " + user_password);
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //读取文件
    public String readFile(String Files ,String files){
        String paths = context.getApplicationContext().getExternalFilesDir(Files).getPath();
        String str = "";
//        Loginer loginer = new Loginer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(paths+"/"+files)));
            str = reader.readLine();
//            loginer.setAvatar(str.split(" ")[1]);
//            loginer.setOnly_name(str.split(" ")[0]) ;
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return str;
    }
}
