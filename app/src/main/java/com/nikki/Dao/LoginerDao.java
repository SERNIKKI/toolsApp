package com.nikki.Dao;

import com.nikki.DBUtils.RhesisUtil;
import com.nikki.bean.Loginer;
import com.nikki.bean.Rhesis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginerDao implements LoginerDaoImpl{
    RhesisUtil rhesisUtil = new RhesisUtil();
    private Connection conn = rhesisUtil.getConnection();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    //添加用户信息方法
    public boolean insert(Loginer loginer) throws SQLException {
        String sql = "insert ignore into app_user(only_name,user_account,user_password,user_avatar,input_time) values (?,?,?,?,?)";
        Object[] params = new Object[]{loginer.getOnly_name(),loginer.getUser_account(),loginer.getUser_password(),loginer.getAvatar(),loginer.getInput_time()};
        stmt = conn.prepareStatement(sql);
        stmt.setString(1,loginer.getOnly_name());
        stmt.setString(2,loginer.getUser_account());
        stmt.setString(3,loginer.getUser_password());
        stmt.setString(4,loginer.getAvatar());
        stmt.setString(5,loginer.getInput_time());
        return stmt.executeUpdate()>0;
    }

    //修改用户名方法
    public boolean update_OnlyName(String only_name,String user_avatar) throws SQLException{
        String sql = "update app_user set only_name = ? where user_avatar = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1,only_name);
        stmt.setString(2,user_avatar);
        return stmt.executeUpdate()>0;
    }

    //查询用户id方法
    public int select_id(String user_account){
        String sql = "select id from app_user where user_account = ?";
        int id = -1;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,user_account);
            rs = stmt.executeQuery();
            while (rs.next()){
                id = rs.getInt("id");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }
    //修改账户名方法
    public boolean update_connect(String user_account,int id) throws SQLException{
        String sql = "update app_user set only_name = ?,user_account = ? where id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1,user_account);
        stmt.setString(2,user_account);
        stmt.setInt(3,id);
        return stmt.executeUpdate()>0;
    }

    //查询是否有only_name
    public String select_OnlyName(int id){
        String sql = "select only_name from app_user where id = ?";
        String str = "";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,id);
            rs = stmt.executeQuery();
            while (rs.next()){
                str = rs.getString("only_name");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return str;
    }

    //查询账号密码
    public String select_UserPassword(String user_account){
        String sql = "select user_password from app_user where user_account = ?";
        String str = "";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,user_account);
            rs = stmt.executeQuery();
            while (rs.next()){
                str = rs.getString("user_password");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return str;
    }
}
