package hellothread.thread;

public class BackCard {

    private double money;

    private boolean flag ;

    //存钱
    public synchronized void saveMoney(double money){ //对方法进行上锁
      while (flag){
          try {
              this.wait(); //只有上锁后才能调用wait方法让线程等待
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
      this.money = this.money + money;
      flag = true;
      System.out.println(Thread.currentThread().getName() +"存入" +money + "，余额" +this.money);
      this.notifyAll(); //只有上锁后才能调用notify方法才能唤醒其他线程
    }

    //取钱
    public synchronized void takeMoney(double money){
        while (!flag){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.money = this.money - money;
        flag = false;
        System.out.println(Thread.currentThread().getName() +"取出" +money + "，余额" +this.money);
        this.notifyAll();
    }

}
