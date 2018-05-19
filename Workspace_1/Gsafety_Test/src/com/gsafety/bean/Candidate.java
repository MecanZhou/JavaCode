package com.gsafety.bean;

/**
 * 投票系统候选人类
 */

public class Candidate {

    private int cdID;
    private String cdName;
    private int cont;

    public Candidate() {
    }

    public Candidate(int cdID, String cdName) {
        this.cdID = cdID;
        this.cdName = cdName;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "候选人ID：" + cdID +
                ", 候选人姓名：'" + cdName + '\'' +
                '}';
    }

    public int getCdID() {
        return cdID;
    }

    public void setCdID(int cdID) {
        this.cdID = cdID;
    }

    public String getCdName() {
        return cdName;
    }

    public void setCdName(String cdName) {
        this.cdName = cdName;
    }
}
