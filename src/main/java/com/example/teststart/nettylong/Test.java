package com.example.teststart.nettylong;

public class Test {
    public static void main(String[] args) {
        try {
            new NettyServerBootstrap(9999);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
