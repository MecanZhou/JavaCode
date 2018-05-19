package com.gsafety.test.unit20;

import com.gsafety.util.DBHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test20_1 {
    public static void main(String[] args) throws SQLException {
        //实现查找销售部所有成员
        Connection connection = DBHelper.getConnection("//localhost:3306/test_java","admin","root");
        ResultSet rs = DBHelper.query(connection,"SELECT mem_name FROM tb_emp WHERE mem_dep = 2");
        if (rs != null) {
            System.out.println("销售部的成员有：");
            while (rs.next()){
                System.out.print(rs.getString("mem_name")+" ");
            }
        }
    }
}
