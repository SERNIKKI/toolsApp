package com.example.app_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nikki.bean.IP;
import com.server.IPServer;

public class IPActivity extends AppCompatActivity {
    private ImageView back;
    private ImageView select;
    private EditText ip_address;
    private TextView ip_country;
    private TextView ip_ipaddress;
    private TextView ip_province;
    private TextView ip_city;
    private TextView ip_isp;
    private String ips;
    private IP ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);
        select = (ImageView)findViewById(R.id.ip_select);
        back = (ImageView)findViewById(R.id.ip_back);
        ip_country = (TextView) findViewById(R.id.ip_Country);
        ip_ipaddress = (TextView)findViewById(R.id.ip_ipaddress);
        ip_province = (TextView)findViewById(R.id.ip_Province);
        ip_city = (TextView)findViewById(R.id.ip_City);
        ip_isp = (TextView)findViewById(R.id.ip_Isp);
        ip_address = (EditText)findViewById(R.id.ip_address);
        back.setOnClickListener(new MyClick());
        select.setOnClickListener(new MyClick());
    }

    class MyClick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.ip_back:
                    Intent intent = new Intent(IPActivity.this,ToolkitActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.ip_select:
                    new Thread(new MyTherd()).start();
                    break;
            }
        }
    }

    public class MyTherd implements Runnable {
        @Override
        public void run() {
            ip = new IP();
            IPServer ipServer = new IPServer();
            ips = ip_address.getText().toString().trim();
            ip = ipServer.executeHttpGet(ips);
            showReq();
        }
    }
    private void showReq(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ip_ipaddress.setText(ip.getIP());
                ip_country.setText(ip.getCountry());
                ip_province.setText(ip.getProvince());
                ip_city.setText(ip.getCity());
                ip_isp.setText(ip.getIsp());
            }
        });
    }
}
