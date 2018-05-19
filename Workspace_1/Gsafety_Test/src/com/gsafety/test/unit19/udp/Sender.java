package com.gsafety.test.unit19.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Sender extends Thread{
    /*
     * 多播组发送端
     */

    String message[]= {"This is a multicast message,message comes from Server！",
            "Multicast designed by ZhouMaogen, Student number is 142208100119!"};
    int port=8088;
    InetAddress groupAddress = null;
    MulticastSocket multicastSocket = null;

    public Sender() {

        try {
            groupAddress = InetAddress.getByName("230.198.112.0");
            multicastSocket = new MulticastSocket(port);
            multicastSocket.setTimeToLive(1);
            multicastSocket.joinGroup(groupAddress);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run(){
        int count = 0;
        while(true){
            DatagramPacket packet = null;
            for(String msg: message){
                byte buff[] = msg.getBytes();
                packet = new DatagramPacket(buff, buff.length,groupAddress,port);
                try {
                    multicastSocket.send(packet);
                    count++;
                    System.out.println("多播组消息已发送"+count+"次！");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {

        new Sender().start();
    }
}
