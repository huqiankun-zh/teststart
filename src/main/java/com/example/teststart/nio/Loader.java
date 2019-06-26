package com.example.teststart.nio;

public class Loader {

    public static void main(String[] args) {
        Deamon deamon = new Deamon(9999);
        new Thread(deamon).start();
    }

}
