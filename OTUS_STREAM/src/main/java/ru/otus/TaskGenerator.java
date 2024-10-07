package ru.otus;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TaskGenerator {

    static AtomicLong id = new AtomicLong(0);

    static Random random = new Random();

    public static Task generateTask() {
        return new Task(id.getAndIncrement(), UUID.randomUUID().toString(),  randStatus());
    }

    private static Status randStatus() {
        int randInt = random.nextInt();
        return randInt % 2 == 0 ? Status.CREATED : (randInt % 3 == 0 ? Status.PROCESS : Status.COMPLETED);
    }

}
