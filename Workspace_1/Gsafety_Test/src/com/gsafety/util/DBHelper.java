package com.gsafety.util;

import java.sql.*;

public class DBHelper {

    /**
     * 连接数据库
     * @param serverIP 数据库连接地址
     * @param username 数据库名
     * @param password 数据库密码
     * @return 数据库连接对象
     */
    public static Connection getConnection(String serverIP, String username, String password){

        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql:"+serverIP,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * 对数据库执行增、删、改操作
     * @param connection 数据库连接对象
     * @param sql 数据库操作语句
     * @param params 执行对象
     * @return 查询结果
     */
    public static boolean update(Connection connection, String sql,Object... params){

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            for(int i = 0;i < params.length;i++){
                ps.setObject(i + 1, params[i]);
            }
            int rows = ps.executeUpdate();
            return rows > 0;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 对数据库执行查询操作
     * @param connection 数据库连接对象
     * @param sql 数据库操作语句
     * @param params 执行对象
     * @return 数据结果集
     */
    public static ResultSet query(Connection connection,String sql,Object... params){

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            for(int i = 0;i < params.length;i++){
                ps.setObject(i + 1, params[i]);
            }
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        DBHelper.getConnection("//localhost:3306/test_java","admin","root");
//    }
}
