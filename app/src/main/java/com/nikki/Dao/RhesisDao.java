package com.nikki.Dao;

import com.example.app_2.R;
import com.nikki.DBUtils.RhesisUtil;
import com.nikki.bean.Rhesis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RhesisDao implements RhesisDaoImpl{
    RhesisUtil rhesisUtil = new RhesisUtil();
    private Connection conn = rhesisUtil.getConnection();
    private PreparedStatement stmt = null;
    private ResultSet rl = null;
    //添加数据方法
    public boolean insert(Rhesis rhesis) throws SQLException {
        String sql = "insert ignore into rhesis(rhesis_sentence,rhesis_writer,rhesis_book,inputtime) values (?,?,?,?)";
        Object[] params = new Object[]{rhesis.getRhesis_sentence(),rhesis.getRhesis_writer(),rhesis.getRhesis_book(),rhesis.getInputtime()};
        stmt = conn.prepareStatement(sql);
        stmt.setString(1,rhesis.getRhesis_sentence());
        stmt.setString(2,rhesis.getRhesis_writer());
        stmt.setString(3,rhesis.getRhesis_book());
        stmt.setString(4,rhesis.getInputtime());
        return stmt.executeUpdate()>0;
    }
    //查询数据方法
    public List<Rhesis> selectAll(){
        String sql = "select rhesis_sentence,rhesis_writer,rhesis_book from rhesis";
        List<Rhesis> query = null;
        try {
            stmt = conn.prepareStatement(sql);
            rl = stmt.executeQuery();
            while (rl.next()){
                Rhesis rhesis = new Rhesis();
                rhesis.setRhesis_sentence(rl.getString("rhesis_sentence"));
                rhesis.setRhesis_writer(rl.getString("rhesis_writer"));
                rhesis.setRhesis_book(rl.getString("rhesis_book"));
                query.add(rhesis);
            }
        }catch (Exception e){
            e.printStackTrace();
    }
        return query;
    }
}
