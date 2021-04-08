package com.example.app_2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ToolkitActivity extends AppCompatActivity {
    private ImageView back;
    private ImageView weather;
    private ImageView ip;
    private ImageView qr;
    private ImageView cidian;
    private ImageView guishudi;
    private ImageView poem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolkit);
        back = (ImageView)findViewById(R.id.toolkit_back);
        weather = (ImageView)findViewById(R.id.toolkit_weather);
        ip = (ImageView)findViewById(R.id.toolkit_ip);
        qr = (ImageView)findViewById(R.id.toolkit_qr);
        cidian = (ImageView)findViewById(R.id.toolkit_cidian);
        guishudi = (ImageView)findViewById(R.id.toolkit_guishudi);
        poem = (ImageView)findViewById(R.id.toolkit_poem);
        back.setOnClickListener(new MyClick());
        weather.setOnClickListener(new MyClick());
        ip.setOnClickListener(new MyClick());
        qr.setOnClickListener(new MyClick());
        cidian.setOnClickListener(new MyClick());
        guishudi.setOnClickListener(new MyClick());
        poem.setOnClickListener(new MyClick());
    }
    class MyClick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.toolkit_back:
                    Intent intent = new Intent(ToolkitActivity.this,Login.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.toolkit_weather:
                    weather.setAlpha(0.5f);
                    Intent intent1 = new Intent(ToolkitActivity.this,WeatherActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.toolkit_ip:
                    ip.setAlpha(0.5f);
                    Intent intent2 = new Intent(ToolkitActivity.this,IPActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.toolkit_qr:
                    qr.setAlpha(0.5f);
                    Intent intent3 = new Intent(ToolkitActivity.this,QRActivity.class);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.toolkit_cidian:
                    cidian.setAlpha(0.5f);
                    showList(view);
            }
        }
    }

    private AlertDialog alert2;
    private static int index = 0;
    public void showList(View view){
        final String[] items = {"新华词典","近反义词","成语词典"};
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("词典");
        alert.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ToolkitActivity.this,items[i],Toast.LENGTH_SHORT).show();
                index = i;
            }
        });
        alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (index){
                    case 0:
                        
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                }
                alert2.dismiss();
            }
        });
        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alert2.dismiss();
            }
        });
        alert2 = alert.create();
        alert2.show();
    }
}
