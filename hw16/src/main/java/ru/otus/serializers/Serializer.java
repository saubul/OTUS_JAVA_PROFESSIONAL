package ru.otus.serializers;

public interface Serializer {

    <T> String serialize(T entity);

}
