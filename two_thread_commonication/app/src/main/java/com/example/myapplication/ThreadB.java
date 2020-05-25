package com.example.myapplication;


public class ThreadB extends Thread {
    private Object lock;
    private MyList list;

    public ThreadB(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            System.out.println("先从线程b开始");
            synchronized (lock){
                System.out.println(" 线程b lock");

                if (MyList.size() != 5) {
                    System.out.println("线程b wait begin " + System.currentTimeMillis());
                    lock.wait();
                    System.out.println(" 线程b再次运行，发送通知后再暂停");
                    lock.notify();
                    lock.wait();
                    System.out.println("最后从线程b结束");                }
                Thread.sleep(50);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
