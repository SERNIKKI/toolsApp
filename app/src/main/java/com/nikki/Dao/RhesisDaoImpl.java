package com.nikki.Dao;

import com.nikki.bean.Rhesis;

import java.sql.SQLException;
import java.util.List;

public interface RhesisDaoImpl {
    /**
     *
     * @param rhesis
     * @return boolean
     * @throws SQLException
     */
    public boolean insert(Rhesis rhesis) throws SQLException;

    /**
     *
     * @return List
     */
    public List<Rhesis> selectAll();
}
