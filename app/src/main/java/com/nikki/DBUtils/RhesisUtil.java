package com.nikki.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;

public class RhesisUtil {
    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //选择驱动类，连接地址、账号密码，连接mysql
            String url = "jdbc:mysql://tx.mxlbs.cn:3306/nikki?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
            String sqlusername="nikki";
            String sqlpassword="2106739694";
            conn = DriverManager.getConnection(url, sqlusername,
                    sqlpassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}
