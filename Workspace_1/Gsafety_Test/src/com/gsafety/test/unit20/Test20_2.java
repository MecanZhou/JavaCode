package com.gsafety.test.unit20;

import com.gsafety.util.DBHelper;

import java.sql.Connection;
import java.sql.ResultSet;

public class Test20_2 {
    public static void main(String[] args) {
        //实现查找销售部所有成员
        Connection connection = DBHelper.getConnection("//localhost:3306/test_java","admin","root");
        if (DBHelper.update(connection,"INSERT INTO tb_emp VALUES(6,\"李某\",\"F\",\"1999-10-20\",2)")){
            System.out.println("添加成功！");
        }

    }
}
