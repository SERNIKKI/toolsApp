package com.nikki.DBUtils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nikki.bean.User;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    public DBOpenHelper(Context context){
        super(context,"db_test",null,1);
        db = getReadableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    //增加数据
    public void add(String username,String password){
        db.execSQL("INSERT INTO user (username,password) VALUES(?,?)",new Object[]{username,password});
    }
    //删除数据
    public void delete(String username,String password){
        db.execSQL("DELETE FROM user WHERE name = AND password = "+username+password);
    }
    //改
    public void update(String password,String username){
        db.execSQL("UPDATE user SET password = ? WHERE username = ?",new Object[]{username,password});
    }
    //查询
    public ArrayList<User> getALLData() {
        ArrayList<User> list = new ArrayList<>();
        Cursor cursor = db.query("user", null, null, null, null, null, "username DESC");
        while (cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            list.add(new User(username,password));
        }
        return list;
    }
}
