package ru.otus;

public enum Status {

    CREATED(0),
    PROCESS(1),
    COMPLETED(2);

    private final int value;
    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
