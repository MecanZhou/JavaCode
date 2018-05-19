package com.gsafety.dao;

import com.gsafety.bean.Voter;
import com.gsafety.util.DBHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VtDAO implements Idao{
    private Connection connection;
    private String serverIP = "//localhost:3306/test_java";
    private String username = "admin";
    private String password = "root";

    //登记投票者票选
    public boolean vote(Voter voter,int n){
        connection = DBHelper.getConnection(serverIP, username, password);
        voter.setVtCdID(n);
        boolean flag = DBHelper.update(connection, "update tb_vt set Cd_id =? where Vt_name = ?",voter.getVtCdID(), voter.getVtName());
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    //登录验证
    public int login(Voter voter){
        connection = DBHelper.getConnection(serverIP, username, password);
        ResultSet resultSet = DBHelper.query(connection, "select * from tb_vt where Vt_name =?",voter.getVtName());
        if (resultSet!=null){
            try {
                while (resultSet.next()){
                    if (resultSet.getString("Vt_pwd").equals(voter.getVtPwd())){
                        if (resultSet.getInt("Cd_id")!=0){
                            System.out.println("你已经投票！");
                            return 2;
                        }else {
                            System.out.println("可以开始投票！");
                            return 1;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //注册用户
    public int reg(Voter voter){
        connection = DBHelper.getConnection(serverIP, username, password);
        ResultSet resultSet = DBHelper.query(connection, "select * from tb_vt where Vt_name =?",voter.getVtName());
        if (resultSet!=null) {
            try {
                while (resultSet.next()){
                    if (resultSet.getString("Vt_pwd")!=null) {
                        resultSet.close();
                        return 2;//用户名被占用
                    }
                }
                boolean flag = DBHelper.update(connection, "insert into tb_vt values(?,?,?)", voter.getVtName(), voter.getVtPwd(), 0);
                if (flag){
                    resultSet.close();
                    return 1;//注册成功
                }else {
                    resultSet.close();
                    return 0;//注册失败
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return 0;
    }

    @Override
    public ArrayList findAll() {
        return null;
    }
}
