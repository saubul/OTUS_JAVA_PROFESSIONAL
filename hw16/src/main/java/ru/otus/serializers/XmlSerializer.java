package ru.otus.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.otus.exceptions.JsonSerializerException;

public class XmlSerializer implements Serializer {

    private static final XmlMapper XML_MAPPER = new XmlMapper();

    @Override
    public <T> String serialize(T entity) {
        try {
            return XML_MAPPER.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new JsonSerializerException("При попытке сериализовать объект в JSON произошла ошибка: %s".formatted(e.getMessage()));
        }
    }
}
