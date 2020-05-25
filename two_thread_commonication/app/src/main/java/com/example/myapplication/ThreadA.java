package com.example.myapplication;

public class ThreadA extends Thread {

    private MyList list;
    private Object lock;
    public ThreadA(Object lock) {
        super();
        this.lock=lock;
    }

    @Override
    public void run() {
        try {
            synchronized (lock){
                for (int i = 0; i < 10; i++) {
                    MyList.add();
                    if(i==5){
                        lock.notify();
                        lock.wait();
                        System.out.println("线程A已经发出了通知");
                    }
                    System.out.println("从线程A添加了" + (i + 1) + "个元素");
                    Thread.sleep(1000);
                }
                lock.notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}