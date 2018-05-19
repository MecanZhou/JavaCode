package com.gsafety.test.unit15;

import java.io.*;

public class Test15_2 {
    private final char KEY = '9';

    //将文字写入文件
    public boolean strToFile(String str, File f){
        if (f.exists()){
            f.delete();
            System.out.println("存在的文件"+f.getName()+"已删除！");
        }
        try {
            f.createNewFile();
            System.out.println("文件"+f.getName()+"已创建！");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(str);
            fw.close();
            System.out.println("写入文件"+f.getName()+"成功！");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //读出文件中的文字
    public String fileToStr(File file){
        try {
            FileReader fr = new FileReader(file);
            char[] byt = new char[1024];
            int len = fr.read(byt);
            System.out.println("读出文件"+file.getName()+"成功！");
            return new String(byt,0,len);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //将文字加密
    public String encodeStr(String str){
        StringBuffer sb = new StringBuffer(str);
        StringBuffer res = new StringBuffer();
        for(int i=0;i<str.length();i++)
        {
            char c = (char)(str.charAt(i) ^ KEY);
            res.append(c);
         }
        System.out.println("加密成功！");
         return res.toString();
    }

    //将文字解密
    public String decodeStr(String str){
        StringBuffer sb = new StringBuffer(str);
        StringBuffer res = new StringBuffer();
        for(int i=0;i<str.length();i++)
        {
            char c = (char)(str.charAt(i) ^ KEY);
            res.append(c);
        }
        System.out.println("解密成功！");
        return res.toString();
    }

    public static void main(String[] args) {
        String str = "用心、用智、安天下";
        Test15_2 t = new Test15_2();
        File f = new File("TestWord1");
        if (t.strToFile(str , f)){
            String str2 = t.fileToStr(f);
            File f2 = new File("TestWord2");
            if (t.strToFile(t.encodeStr(str2),f2)){
                String str3 = t.fileToStr(f2);
                System.out.println("源文件文字："+str2);
                System.out.println("加密文字："+str3);
                System.out.println("解密文字："+t.decodeStr(str3));
            }
        }

    }
}
