package com.example.app_2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.nikki.bean.Loginer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SteamActivity extends AppCompatActivity {
    private TextView account;
    private TextView language;
    private TextView logout;
    private TextView clean;
    private ImageView setting_back;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        account = (TextView)findViewById(R.id.setting_account);
        language = (TextView)findViewById(R.id.setting_language);
        logout = (TextView)findViewById(R.id.setting_logout);
        clean = (TextView)findViewById(R.id.setting_clean);
        setting_back = (ImageView) findViewById(R.id.setting_back);
        account.setOnClickListener(new Click());
        language.setOnClickListener(new Click());
        logout.setOnClickListener(new Click());
        clean.setOnClickListener(new Click());
        setting_back.setOnClickListener(new Click());
    }
    //为控件添加对应的点击事件
    class Click implements View.OnClickListener{
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.setting_account:
                    break;
                case R.id.setting_language:
                    break;
                case R.id.setting_logout:
                    showNoneEffect();
                    break;
                case R.id.setting_clean:
                    break;
                case R.id.setting_back:
                    Intent intent = new Intent(SteamActivity.this,Login.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }
    //书写弹窗
    private void showNoneEffect(){
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //引入弹窗布局
        View vPopupWindow = inflater.inflate(R.layout.logout,null,false);
        popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT);
        //设置透明背景
        addBackground();
        //设置进出动画
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        //设置响应外部点击事件
        popupWindow.setOutsideTouchable(true);
        //设置能否响应点击事件
        popupWindow.setTouchable(true);
        //引入依赖布局
        View parentView = LayoutInflater.from(SteamActivity.this).inflate(R.layout.logout,null);
        //相对于父控件的位置
        popupWindow.showAtLocation(parentView, Gravity.CENTER,0,0);
        TextView back = vPopupWindow.findViewById(R.id.steam_logout);
        TextView no_back = vPopupWindow.findViewById(R.id.steam_no_logout);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFile("NIKKI","Login.txt");
                deleteFile("Local","local_user.txt");
                Toast.makeText(SteamActivity.this,"注销成功~~~",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SteamActivity.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
        no_back.setOnClickListener(new View.OnClickListener() {
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
    //创建本地文件
    public boolean deleteFile(String Files ,String files){
        String paths = getApplicationContext().getExternalFilesDir(Files).getPath();
        File file = new File(paths+"/"+files);
        try {
            if(file.exists()) {
                file.delete();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
