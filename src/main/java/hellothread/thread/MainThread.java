package hellothread.thread;

public class MainThread {

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread("线程1");
        myThread.start();
        myThread.join(5);
        MyThread myThread1 = new MyThread("线程2");
        myThread1.start();
    }
}
