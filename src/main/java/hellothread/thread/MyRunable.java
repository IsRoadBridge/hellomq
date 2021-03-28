package hellothread.thread;

public class MyRunable implements  Runnable {


    @Override
    public  void  run() {
         for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() +"....." + i);
         }
      /*  while (number >= 1){
            System.out.println(Thread.currentThread().getName() + "卖了第" + (101-number) + "张票");
            --number;
        }*/

    }
}
