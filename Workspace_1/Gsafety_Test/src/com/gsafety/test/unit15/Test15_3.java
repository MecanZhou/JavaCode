package com.gsafety.test.unit15;

import java.io.*;

public class Test15_3 {

    public static void main(String[] args) {
        File file = new File("User_Info.txt");
        if(file.exists()){
            file.delete();
            System.out.println("存在的文件"+file.getName()+"已删除！");
        }
        try {
            file.createNewFile();
            System.out.println("新的文件"+file.getName()+"已创建！");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("User\tPassWord");
            bw.newLine();
            //bw.flush();
            BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入要插入的用户名，输入\"done\"退出：");
            String str = br.readLine();
            while(!str.equalsIgnoreCase("done")){
                bw.write(str+"\t");
                System.out.println("请输入该用户的密码：");
                String str2 = br.readLine();
                bw.write(str2);
                bw.newLine();
                System.out.println(str+"插入成功！\n请继续输入要插入的用户名，输入\"done\"退出：");
                str = br.readLine();
                bw.flush();
            }
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
