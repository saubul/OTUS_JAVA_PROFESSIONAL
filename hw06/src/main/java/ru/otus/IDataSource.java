package ru.otus;

public interface IDataSource<T> {

    Connection<T> getConnection();

}
