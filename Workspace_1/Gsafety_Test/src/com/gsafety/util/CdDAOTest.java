package com.gsafety.util;

import com.gsafety.bean.Candidate;
import com.gsafety.dao.CdDAO;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CdDAOTest {

    @Test
    public void testFindAll(){
        CdDAO cdDAO = new CdDAO();
        try {
            ArrayList list = cdDAO.findAll();
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
