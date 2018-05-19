package com.gsafety.dao;

import com.gsafety.bean.Candidate;
import com.gsafety.util.DBHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CdDAO implements Idao{

    private Connection connection;
    private String serverIP = "//localhost:3306/test_java";
    private String username = "admin";
    private String password = "root";

    //查询所有候选人
    public ArrayList findAll() {
        connection = DBHelper.getConnection(serverIP, username, password);
        ArrayList<Candidate> list = new ArrayList<Candidate>();
        ResultSet resultSet = DBHelper.query(connection,"select * from tb_cd");
        try {
            if (resultSet!=null){
                while (resultSet.next()){
                    Candidate c = new Candidate();
                    c.setCdID(resultSet.getInt("Cd_id"));
                    c.setCdName(resultSet.getString("Cd_name"));
                    list.add(c);
                }
            }
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询候选人票数
    public int findVoteNum (Candidate candidate){
        connection = DBHelper.getConnection(serverIP, username, password);
        int n = 0;
        ResultSet rs = DBHelper.query(connection,"select COUNT(*) from tb_vt where Cd_id = ?",candidate.getCdID());
        try {
            if (rs.first()){
                n = rs.getInt(1);
                rs.close();
                connection.close();
                return n;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

        }
        return n;
    }
}
