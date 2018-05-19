package com.gsafety.bean;

/**
 * 投票系统投票人类
 */

public class Voter {

    private String vtName;
    private String vtPwd;
    private int vtCdID;

    public Voter(String vtName, String vtPwd) {
        this.vtName = vtName;
        this.vtPwd = vtPwd;
    }

    public Voter(String vtName, String vtPwd, int vtCdID) {
        this.vtName = vtName;
        this.vtPwd = vtPwd;
        this.vtCdID = vtCdID;
    }

    @Override
    public String toString() {
        return "Voter{" +
                "vtName='" + vtName + '\'' +
                ", vtPwd='" + vtPwd + '\'' +
                ", vtCdID=" + vtCdID +
                '}';
    }

    public String getVtName() {
        return vtName;
    }

    public void setVtName(String vtName) {
        this.vtName = vtName;
    }

    public String getVtPwd() {
        return vtPwd;
    }

    public void setVtPwd(String vtPwd) {
        this.vtPwd = vtPwd;
    }

    public int getVtCdID() {
        return vtCdID;
    }

    public void setVtCdID(int vtCdID) {
        this.vtCdID = vtCdID;
    }
}
