package ru.otus.threadpool;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class OtusThreadPool implements Executor {

    private final int capacity;

    private final LinkedList<Runnable> taskList;

    private final Set<Thread> threadSet;

    private final static OtusThreadFactory otusThreadFactory = new OtusThreadFactory();

    private boolean isActive = true;

    public OtusThreadPool(int capacity) {
        this.capacity = capacity;
        this.taskList = new LinkedList<>();
        this.threadSet = new HashSet<>(this.capacity);
        for (int i = 0; i < capacity; i++) {
            this.threadSet.add(otusThreadFactory.newThread(this.taskList));
        }
    }

    @Override
    public void execute(Runnable command) {
        Objects.requireNonNull(command);
        if (isActive) {
            synchronized (taskList) {
                taskList.addLast(command);
                taskList.notify();
            }
        } else {
            throw new IllegalStateException("Пул потоков не активен");
        }
    }

    public void shutdown() {
        isActive = false;
        for (Thread thread : threadSet) {
            thread.interrupt();
        }
    }

    public int getCapacity() {
        return capacity;
    }

    private static class OtusThreadFactory {

        private static final AtomicInteger threadNumber = new AtomicInteger(0);

        public OtusThread newThread(LinkedList<Runnable> taskList) {
            OtusThread t = new OtusThread(taskList);
            t.setName("OtusThread-" + threadNumber.getAndIncrement());
            t.start();
            return t;
        }
    }

    private static class OtusThread extends Thread {

        private final LinkedList<Runnable> taskList;

        public OtusThread(LinkedList<Runnable> commandList) {
            Objects.requireNonNull(commandList);
            this.taskList = commandList;
        }

        @Override
        public void run() {
            while (true) {
                Runnable task;
                synchronized (taskList) {
                    if (taskList.size() == 0) {
                        try {
                            taskList.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException("Меня прервали! " + e.getMessage());
                        }
                    }
                    task = taskList.getFirst();
                    taskList.removeFirst();
                }
                try {
                    task.run();
                } catch (Exception e) {
                    System.out.println("При перевыполнении задания произошла ошибка: " + e.getMessage());
                }
            }
        }
    }
}

