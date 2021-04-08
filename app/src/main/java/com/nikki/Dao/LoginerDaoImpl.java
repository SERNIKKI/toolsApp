package com.nikki.Dao;

import com.nikki.bean.Loginer;

import java.sql.SQLException;

public interface LoginerDaoImpl {
    /**
     *
     * @param loginer
     * @return boolean
     * @throws SQLException
     */
    public boolean insert(Loginer loginer) throws SQLException;

    /**
     *
     * @param only_name
     * @param user_avatar
     * @return boolean
     * @throws SQLException
     */
    public boolean update_OnlyName(String only_name,String user_avatar) throws SQLException;

    /**
     *
     * @param user_account
     * @return int
     */
    public int select_id(String user_account);

    /**
     *
     * @param user_account
     * @param id
     * @return boolean
     * @throws SQLException
     */
    public boolean update_connect(String user_account,int id) throws SQLException;
}
