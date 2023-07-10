package com.spotify.oauth2.components.miscellaneous;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.LinkedHashMap;

public class DB_Operations {

    public synchronized LinkedHashMap<Object, Object> getSqlResultInMap(String sql) {
        LinkedHashMap<Object, Object> data_map = new LinkedHashMap<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/practice", "root", "root");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();

            while (rs.next()) {
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    data_map.put(md.getColumnName(i), rs.getString(i));
                }
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data_map;
    }

    /**
     * Sample function...
     */
    @Test
    public void getValue() {
        LinkedHashMap<Object, Object> resultInMap = getSqlResultInMap("select * from it_job where name = 'Sabari D V'");
        System.out.println(resultInMap);
    }
}