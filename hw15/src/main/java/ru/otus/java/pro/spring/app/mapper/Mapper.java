package ru.otus.java.pro.spring.app.mapper;

public interface Mapper<Entity, Dto> {

    Dto toDto(Entity entity);

    Entity toEntity(Dto dto);

}
