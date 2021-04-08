package com.example.app_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    private TextView about_author;
    private TextView about_version;
    private TextView about_tell;
    private TextView about_allow;
    private ImageView about_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        about_author = (TextView)findViewById(R.id.about_author);
        about_version = (TextView)findViewById(R.id.about_version);
        about_tell = (TextView)findViewById(R.id.about_tell);
        about_allow = (TextView)findViewById(R.id.about_allow);
        about_back = (ImageView)findViewById(R.id.about_back);
        about_author.setOnClickListener(new MyClick());
        about_version.setOnClickListener(new MyClick());
        about_tell.setOnClickListener(new MyClick());
        about_allow.setOnClickListener(new MyClick());
        about_back.setOnClickListener(new MyClick());

    }

    class MyClick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.about_back:
                    Intent intent = new Intent(AboutActivity.this,Login.class);
                    startActivity(intent);
                    finish();
                case R.id.about_author:
                    break;
                case R.id.about_version:
                    break;
                case R.id.about_tell:
                    break;
                case R.id.about_allow:
                    break;
            }
        }
    }
}
