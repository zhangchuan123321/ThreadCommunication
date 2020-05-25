两个工作线程之间的通信：
使用wait、notify方法实现线程间的通信:

1.wait和notify必须配合synchronized关键字使用
2.wait方法释放锁，notify方法不释放锁
3.当前的线程必须拥有当前对象的monitor，也即lock
4.要确保调用wait()方法的时候拥有锁，即，wait()方法的调用必须放在synchronized方法或synchronized块中

总结：ynchronized 只能有一个线程进行执行，这个线程不释放锁的话，其他线程就一直等待。

好处：解决了多线程的安全问题

弊端：效率非常低，多个线程需要判断锁，比较消耗资源，抢锁的资源。
线程A：
public class ThreadA extends Thread {

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

线程B：
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

在MainActivity打开A B线程：
         
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
                
                
运行结果：
先从线程b开始
 线程b lock
线程b wait begin 1590372980843
从线程A添加了1个元素
从线程A添加了2个元素
从线程A添加了3个元素
从线程A添加了4个元素
从线程A添加了5个元素
 线程b再次运行，发送通知后再暂停
线程A已经发出了通知
从线程A添加了6个元素
从线程A添加了7个元素
从线程A添加了8个元素
从线程A添加了9个元素
从线程A添加了10个元素
最后从线程b结束

Process finished with exit code 0
