package com.nikki.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app_2.R;
import com.nikki.bean.Rhesis;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class YiYanAdapter extends RecyclerView.Adapter<YiYanAdapter.MyViewHolder>{
    private Context context;
    private List<Rhesis> list;
    private View inflater;
    private Bitmap bitmap;
    private Rhesis rhesis = new Rhesis();
    //解决复用
    private List<Integer> favorList = new ArrayList<>();
    //构造方法传入数据
    public YiYanAdapter(Context context,List<Rhesis> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.item_demo,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //将数据和控件绑定
        new Thread(){
            @Override
            public void run(){
                try {
                    URL url = new URL(list.get(position).getUrl());
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
                    holder.imageView.setImageBitmap(bitmap);
                }catch (Exception e){
                    Message message = new Message();
                    message.what = -1;
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        }.start();
        holder.writerView.setText(list.get(position).getRhesis_writer());
        holder.bookView.setText(list.get(position).getRhesis_book());
        holder.sentenceView.setText(list.get(position).getRhesis_sentence());
        //实现点击效果
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(monItemClickListener!=null){
                    monItemClickListener.OnItemClick(view,list.get(position),position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView writerView;
        TextView bookView;
        TextView sentenceView;
        ImageView imageView;
        public MyViewHolder(View itemView){
            super(itemView);
            writerView = (TextView) itemView.findViewById(R.id.rhesis_writer);
            bookView = (TextView)itemView.findViewById(R.id.rhesis_book);
            sentenceView = (TextView)itemView.findViewById(R.id.rhesis_sentence);
            imageView = (ImageView)itemView.findViewById(R.id.imgs_yiyan);
        }
    }
    //Handler异步方式下载图片
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){
            ImageView imageView;
            switch (msg.what){
                case 1:
                    //下载成功
                    break;
                case -1:
                    //下载失败使用默认图片
                    break;
            }
        };
    };

    //回调接口
    public interface OnItemClickListener{
        void OnItemClick(View v,Rhesis rhesis,int position);
    }
    //私有属性
    private OnItemClickListener monItemClickListener = null;
    //set方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.monItemClickListener = onItemClickListener;
    }
}

