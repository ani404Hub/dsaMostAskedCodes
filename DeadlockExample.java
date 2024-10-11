package org.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockExample {
    public static void main(String[] args) {
        final Object obj1 = new Object();
        final Object obj2 = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (obj1) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (obj2) {}
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (obj2) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
                synchronized (obj1) {}
            }
        });

        t1.start();
        t2.start();
    }

    class DeadlockAvoidance {
        public static void main(String[] args) {
            final Lock lock1 = new ReentrantLock();
            final Lock lock2 = new ReentrantLock();

            Thread t1 = new Thread(() -> {
                try {
                    if (lock1.tryLock(1000, TimeUnit.MILLISECONDS)) {
                        System.out.println("Thread 1: Locked resource 1");
                        try { Thread.sleep(100); } catch (InterruptedException e) { }
                        if (lock2.tryLock(1000, TimeUnit.MILLISECONDS)) {
                            try {
                                System.out.println("Thread 1: Locked resource 2");
                            } finally {
                                lock2.unlock();         // Unlock resource 2
                            }
                        } else {
                            System.out.println("Thread 1: Could not lock resource 2, avoiding deadlock");
                        }
                    } else {
                        System.out.println("Thread 1: Could not lock resource 1, avoiding deadlock");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock1.unlock();                     // Unlock resource 1
                }
            });

            Thread t2 = new Thread(() -> {
                try {
                    if (lock2.tryLock(1000, TimeUnit.MILLISECONDS)) {
                        System.out.println("Thread 2: Locked resource 2");
                        try { Thread.sleep(100); } catch (InterruptedException e) {}
                        if (lock1.tryLock(1000, TimeUnit.MILLISECONDS)) {
                            try {
                                System.out.println("Thread 2: Locked resource 1");
                            } finally {
                                lock1.unlock();                 // Unlock resource 1
                            }
                        } else {
                            System.out.println("Thread 2: Could not lock resource 1, avoiding deadlock");
                        }
                    } else {
                        System.out.println("Thread 2: Could not lock resource 2, avoiding deadlock");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock2.unlock();                             // Unlock resource 2
                }
            });

            t1.start();
            t2.start();
        }
    }

}
