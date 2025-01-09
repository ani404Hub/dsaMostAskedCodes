package org.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class oddEvenMultiThread {
    public static void main(String[] args) {
        //implement using java 7
        Object lock = new Object();
        Runnable r1= new oddEvenPrint(lock);
        Runnable r2= new oddEvenPrint(lock);
        new Thread(r1, "Odd").start();
        new Thread(r1, "Even").start();

        //implement using java 8 completable future
        try(ExecutorService es = Executors.newFixedThreadPool(2)){                      //Define fixed thread pool else create fork join pool by default
            IntStream.rangeClosed(1,10).forEach(n -> {
                CompletableFuture<Integer> odd = CompletableFuture.completedFuture(n).thenApplyAsync(x -> {
                    if(x%2 != 0)
                        System.out.println(Thread.currentThread().getName());
                    return n;
                }, es);
                odd.join();
                CompletableFuture<Integer> even = CompletableFuture.completedFuture(n).thenApplyAsync(x -> {
                    if(x%2 == 0)
                        System.out.println(Thread.currentThread().getName());
                    return n;
                }, es);
                even.join();
                });
            es.shutdown();                                                                      //not required as using try with resources
        }
    }
}

class oddEvenPrint implements Runnable{
    final Object object;
    static int count = 0;

    public oddEvenPrint(Object object) {                                                       //Add Parametrized constructor
        this.object = object;
    }

    @Override
    public void run() {
        while (count<10){
            if(count%2 != 0 && Thread.currentThread().getName().equals("Even")){
                synchronized (object){
                    System.out.println("Thread Name : " + Thread.currentThread().getName()+ " : "+ count);
                    count++;
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(count%2 == 0 && Thread.currentThread().getName().equals("Odd")){
                synchronized (object){
                    System.out.println("Thread Name : " + Thread.currentThread().getName()+ " : "+ count);
                    count++;
                    object.notify();
                }
            }
        }
    }
}
