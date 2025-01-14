package ru.otus.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.exceptions.JsonSerializerException;

public class JsonSerializer implements Serializer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public <T> String serialize(T entity) {
        try {
            return OBJECT_MAPPER.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new JsonSerializerException("При попытке сериализовать объект в JSON произошла ошибка: %s".formatted(e.getMessage()));
        }
    }
}
