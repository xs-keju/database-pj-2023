package com.example.backend0.util;

/**
 * @ClassName VariableDefine
 * @Description
 **/

public class VariableDefine {
    public static String tokenValid(){return "validToken";}
    public static String signature(){return "XXWANDCPX";}
    public static long illegalTime(){return 10*60;}// 十分钟
    public static Integer getTypeUser(){
        return 1;
    }
    public static Integer getTypeShopper(){
        return 2;
    }
    public static Integer getTypeAdmin(){
        return 0;
    }
}
