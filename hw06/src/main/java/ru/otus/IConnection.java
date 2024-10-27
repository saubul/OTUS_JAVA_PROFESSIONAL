package ru.otus;


import java.util.Set;

public interface IConnection<T> {

    T create(T object);

    Set<T> read();

    T update(T oldObject, T newObject);

    T delete(T object);

    void clear();

}
