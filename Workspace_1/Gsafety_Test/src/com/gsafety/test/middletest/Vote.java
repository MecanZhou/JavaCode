package com.gsafety.test.middletest;

import com.gsafety.bean.Candidate;
import com.gsafety.bean.Voter;
import com.gsafety.dao.CdDAO;
import com.gsafety.dao.VtDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/**
 * 中期测试：班级投票系统
 */

public class Vote {

    private static final String CODES="0123456789QWERTYUIOPASDFGHJKLZXCVBNM";
    private CdDAO cdDAO = new CdDAO();
    private VtDAO vtDAO = new VtDAO();

    public static void main(String[] args) {

        Vote v = new Vote();
        System.out.println("欢迎进入投票系统！");
        System.out.println();
        System.out.println("################");
        System.out.println();
        v.showResult();
        v.showDialog();
    }

    public void showDialog(){
        System.out.println("请选择你要进行的操作：");
        System.out.println("1.登录(查看票选结果)    2.注册    0.退出");
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        try {
            String str = br.readLine();
            while (!str.matches("[0-2]+")){
                System.out.println("输入格式不正确！重新输入：");
                str = br.readLine();
            }
            int flag = Integer.parseInt(str);
            while(flag!=0) {
               if (flag == 1) {
                    login();
                } else if (flag == 2) {
                    reg();
                }
                System.out.println("请选择你要进行的操作：");
                System.out.println("1.登录(查看票选结果)    2.注册    0.退出");
                flag = Integer.parseInt(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //登录验证
    public void login(){
        System.out.println("***开始登录操作***");
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("请输入用户名：");
            String username = br.readLine();
            System.out.println("请输入密码：");
            String pwd = br.readLine();
            Voter voter = new Voter(username,pwd);
            Random random = new Random();
            char[] codes = new char[4];
            for (int i = 0; i < 4; i++) {
                codes[i] = CODES.charAt(random.nextInt(CODES.length()));
            }
            String str = new String(codes);
            System.out.println("请输入验证码："+str);
            String str1 = br.readLine();
            while (!str1.equalsIgnoreCase(str)){
                System.out.println("验证码错误，请输入验证码："+str);
                str1 = br.readLine();
            }
            int flag = vtDAO.login(voter);
            if (flag==0){
                System.out.println("用户名不存在或密码错误！");
            }else if(flag==1){
                //可以开始投票
                voting(voter);
            }else {
                //显示票选结果
                System.out.println("你已投票，投票结果为：");
                showResult();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //注册
    public boolean reg(){
        System.out.println("***开始注册操作***");
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("请输入用户名：");
            String username = br.readLine();
            System.out.println("请输入密码：");
            String pwd = br.readLine();
            System.out.println("请再次输入密码：");
            String pwd2 = br.readLine();
            if (!pwd.equals(pwd2)){
                System.out.println("两次输入密码不一致！重新注册！");
                reg();
            }
            Voter voter = new Voter(username,pwd,0);
            int flag = vtDAO.reg(voter);
            if (flag==0){
                System.out.println("注册失败！");
            }else if(flag==1){
                System.out.println("注册成功！");
            }else {
                System.out.println("该用户名已被注册！请重新输入！");
                reg();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    //显示票选结果
    public void showResult(){
        try {
            ArrayList<Candidate> list = cdDAO.findAll();
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).toString()+"  票数："+cdDAO.findVoteNum(list.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //投票操作
    public boolean voting(Voter voter){
        System.out.println("候选人信息：");
        ArrayList list = null;
        try {
            list = cdDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入您要投票的候选人ID：");
        try {
            int n = Integer.parseInt(br.readLine());
            if (vtDAO.vote(voter,n)){
                System.out.println("投票成功！");
                showResult();
                System.out.println();
                System.out.println("退出登录！");
                return true;
            }else {
                System.out.println("投票失败！");
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
