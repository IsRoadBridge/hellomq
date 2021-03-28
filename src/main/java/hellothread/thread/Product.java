package hellothread.thread;


import java.util.List;

interface Middleman{
    float calOutPrice(float inPrice);
}

public class Product {

    private List<Middleman> middlemanList;

    float getUserPrice(float factoryPrice){
        return 0;
    }

    public static void main(String[] args) throws Exception {
    }
}
