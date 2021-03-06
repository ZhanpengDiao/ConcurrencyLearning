package com.diao.ConcurrentCollections;

import java.util.concurrent.Exchanger;

/**
 * Created by ZhanpengDiao on 20/7/17.
 */
class FirstThread implements Runnable {
    private int counter;
    private Exchanger<Integer> exchanger;

    public FirstThread(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        while(true) {
            counter = counter + 1;
            System.out.println("FirstThread incremented the counter: " + counter);

            try {
                counter = exchanger.exchange(counter); // send the counter and get another counter
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class SecondThread implements Runnable {
    private int counter;
    private Exchanger<Integer> exchanger;

    public SecondThread(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        while(true) {
            counter = counter - 1;
            System.out.println("SecondThread decremented the counter: " + counter);

            try {
                counter = exchanger.exchange(counter); // send the counter and get another counter
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ExchangerTest {

    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<>();

        new Thread(new FirstThread(exchanger)).start();
        new Thread(new SecondThread(exchanger)).start();
    }

}
