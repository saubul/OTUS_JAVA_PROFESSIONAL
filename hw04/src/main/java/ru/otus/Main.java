package ru.otus;

import ru.otus.threadpool.OtusThreadPool;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        OtusThreadPool otusThreadPool = new OtusThreadPool(5);

        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i < 100; i++) {
            otusThreadPool.execute(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Меня прервали! Обработка ошибки: " + e.getMessage() + ". Заканчиваю работу!");
                }
                System.out.println(Thread.currentThread().getName() + ": " + atomicInteger.incrementAndGet());
            });
        }

        System.out.println("IM DONE!");

        Thread.sleep(1000);
        otusThreadPool.shutdown();


        for (int i = 0; i < 10; i++) {
            otusThreadPool.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ": " + atomicInteger.incrementAndGet());
            });
        }

    }

}
