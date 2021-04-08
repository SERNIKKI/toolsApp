package com.example.app_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.server.QRServer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class QRActivity extends AppCompatActivity {
    private ImageView qr_back;
    private TextView qr_text;
    private TextView qr_file;
    private Button qr_sure;
    private static String file;
    private static String text;
    private static String txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        qr_back = (ImageView)findViewById(R.id.qr_back);
        qr_text = (EditText)findViewById(R.id.qr_text);
        qr_file = (EditText)findViewById(R.id.qr_file);
        qr_sure = (Button)findViewById(R.id.qr_sure);
        qr_back.setOnClickListener(new MyClick());
        qr_sure.setOnClickListener(new MyClick());
    }
    class MyClick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.qr_back:
                    Intent intent = new Intent(QRActivity.this,ToolkitActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.qr_sure:
                    new Thread(new MyTherd()).start();
                    Toast.makeText(QRActivity.this,"成功！文件保存在" + getApplicationContext().getExternalFilesDir("QR").getPath()+"/"+ file + ".txt",Toast.LENGTH_LONG).show();
                    qr_text.setText("");
                    qr_file.setText("");
                    break;
            }
        }
    }
    public class MyTherd implements Runnable {
        @Override
        public void run() {
            QRServer qrServer = new QRServer();
            text = qr_text.getText().toString().trim();
            file = qr_file.getText().toString().trim();
            txt = qrServer.executeHttpGet(text);
            showReq();
        }
    }
    private void showReq(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newDirectory();
                newFile(file);
                writeFile();
            }
        });
    }

    //创建本地文件夹
    public void newDirectory(){
        String paths = getApplicationContext().getExternalFilesDir("").getPath();
        File file = new File(paths + "/" + "QR");
        try {
            if (!file.exists()) {
                file.mkdir();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //创建本地文件
    public void newFile(String files){
        String paths = getApplicationContext().getExternalFilesDir("QR").getPath();
        File file = new File(paths+"/"+ files +".txt");
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //访问文件
    public void writeFile(){
        String paths = getApplicationContext().getExternalFilesDir("QR").getPath();
        String res = txt;
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(paths+"/"+ file + ".txt")));
            writer.write(res);
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
