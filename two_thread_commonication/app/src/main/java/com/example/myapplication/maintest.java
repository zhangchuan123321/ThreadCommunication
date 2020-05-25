package com.example.myapplication;

public class maintest {
    public static void main(String[] args) {

        Object lock = new Object();
        ThreadB b=new ThreadB(lock);
        b.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ThreadA a=new ThreadA(lock);
        a.start();
    }
}
