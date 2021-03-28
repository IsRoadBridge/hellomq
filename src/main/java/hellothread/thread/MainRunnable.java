package hellothread.thread;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainRunnable {

    private static int index = 0;

    public static void main(String[] args) throws InterruptedException {
       /* //创建一个MyRunable对象
        MyRunable myRunable = new MyRunable();
        //将创建的对象传到线程中，参数二是给线程命名，可不要
        Thread thread = new Thread(myRunable,"线程1");
        //开启线程
        thread.start();

        //常用的最简的通过匿名内部类创建并开启一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println(Thread.currentThread().getName() +"....." + i);
                }
            }
        }).start();*/
//        MyRunable myRunable = new MyRunable();
//        Thread thread = new Thread(myRunable, "窗口1");
//        thread.start();
//        Thread thread1 = new Thread(myRunable, "窗口2");
//        thread1.start();
//        Thread thread2 = new Thread(myRunable, "窗口3");
//        thread2.start();
//        Thread thread3 = new Thread(myRunable, "窗口4");
//        thread3.start();
//
//        MyRunable myRunable1 = new MyRunable();
//        Thread thread1 = new Thread(myRunable1,"男朋友");
//        Thread thread2 = new Thread(myRunable1,"女朋友");
//        thread1.setDaemon(true);
//        thread1.setPriority(1);
//        thread1.start();
//        for (int i = 0; i < 2; i++) {
//            System.out.println(i);
//        }
//
//        String[] s = new  String[5];
//
//       Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (s){
//                    s[index] = "hello";
//                    index++;
//                }
//            }
//        });
//
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (s){
//                    s[index] = "world";
//                    index++;
//                }
//            }
//        });
//
//        thread.start();
//        thread1.start();
//        thread.join();
//        thread1.join();
//        System.out.println(Arrays.toString(s));

        BackCard backCard = new BackCard();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    backCard.saveMoney(1000);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    backCard.takeMoney(1000);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    backCard.saveMoney(1000);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    backCard.takeMoney(1000);
                }
            }
        }).start();

     }

}
