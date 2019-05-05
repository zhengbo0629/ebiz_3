package com.ebiz.test;

public class test1 {

    public static void main(String args[]) {
        String str = "小学,初中,高中,大专,本科,研究生,博士";
        String[] buff = str.split(",");
        for(int i=0;i<buff.length;i++){
            System.out.println(buff[i]);
        }
    }
}
