package ru.otus.serializers;

import org.yaml.snakeyaml.Yaml;

public class YamlSerializer implements Serializer {

    private static final Yaml YAML = new Yaml();

    @Override
    public <T> String serialize(T entity) {
        return YAML.dump(entity);
    }
}
