package ru.otus.repository;

import java.util.List;

public interface IRepository<T> {

    T create(T entity);

    T findById(Long id);

    List<T> findAll();

    void delete(T entity);

}
