package ru.otus;

import java.util.Set;

public interface IService<T> {

    void insert(int count);

    void updateAll();

    void clear();

    Set<T> readAll();

}
