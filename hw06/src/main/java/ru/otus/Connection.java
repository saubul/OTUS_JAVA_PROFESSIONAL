package ru.otus;

import java.util.Set;

public class Connection<T> implements IConnection<T> {

    private final LocalDataBase<T> localDataBase;

    public Connection(LocalDataBase<T> localDataBase) {
        this.localDataBase = localDataBase;
    }


    @Override
    public T create(T object) {
        localDataBase.getSet().add(object);
        return object;
    }

    @Override
    public Set<T> read() {
        return localDataBase.getSet();
    }

    @Override
    public T update(T oldObject, T newObject) {
        localDataBase.getSet().remove(oldObject);
        localDataBase.getSet().add(newObject);
        return newObject;
    }

    @Override
    public T delete(T object) {
        localDataBase.getSet().remove(object);
        return object;
    }

    @Override
    public void clear() {
        localDataBase.getSet().clear();
    }
}
