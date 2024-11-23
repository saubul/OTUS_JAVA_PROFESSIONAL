package ru.otus.dao;

import ru.otus.annotation.RepositoryField;
import ru.otus.annotation.RepositoryIdField;
import ru.otus.annotation.RepositoryTable;
import ru.otus.datasource.DataSource;
import ru.otus.exception.ORMException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AbstractRepository<T> {
    private final DataSource dataSource;
    private PreparedStatement psInsert;
    private PreparedStatement psSelectAll;
    private PreparedStatement psSelectById;
    private PreparedStatement psUpdate;
    private PreparedStatement psDeleteById;
    private List<Field> cachedFields;
    private final Class<T> cls;
    private Field idField;

    private Map<Field, Method> getterMethodMap = new HashMap<>();
    private Map<Field, Method> setterMethodMap = new HashMap<>();
    private String tableName;

    public AbstractRepository(DataSource dataSource, Class<T> cls) {
        this.dataSource = dataSource;
        this.cls = cls;
        this.init();
        this.prepareStatements();
    }

    private void prepareStatements() {
        this.prepareInsert();
        this.prepareSelectAll();
        this.prepareSelectById();
        this.prepareDeleteById();
        this.prepareUpdate();
    }

    private void init() {
        if (!cls.isAnnotationPresent(RepositoryTable.class)) {
            throw new ORMException("Класс не предназначен для создания репозитория, не хватает аннотации @RepositoryTable");
        }
        this.tableName = cls.getAnnotation(RepositoryTable.class).title();
        this.cachedFields = new ArrayList<>();
        Arrays.asList(cls.getDeclaredFields()).forEach(field -> {
            if (field.isAnnotationPresent(RepositoryField.class) || field.isAnnotationPresent(RepositoryIdField.class)) {
                if (field.isAnnotationPresent(RepositoryIdField.class)) {
                    this.idField = field;
                } else {
                    this.cachedFields.add(field);
                }
                fillFieldGettersAndSettersMap(field);
            }
        });
        if (idField == null) {
            throw new ORMException("Класс не предназначен для создания репозитория, не хватает обязательной аннотации @RepositoryIdField");
        }
    }

    private void fillFieldGettersAndSettersMap(Field field) {
        String capitalizedFieldName = field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
        try {
            this.getterMethodMap.put(field, cls.getDeclaredMethod("get" + capitalizedFieldName));
            this.setterMethodMap.put(field, cls.getDeclaredMethod("set" + capitalizedFieldName, field.getType()));
        } catch (NoSuchMethodException e) {
            throw new ORMException("При попытке найти методы %s и %s произошла ошибка: %s"
                    .formatted("get" + capitalizedFieldName, "set" + capitalizedFieldName, e.getMessage()));
        }
    }

    public void save(T entity) {
        try {
            for (int i = 0; i < cachedFields.size(); i++) {
                psInsert.setObject(i + 1, getterMethodMap.get(cachedFields.get(i)).invoke(entity));
            }
            psInsert.executeUpdate();
        } catch (Exception e) {
            throw new ORMException("Произошла ошибка при попытки сохранить сущность %s: %s".formatted(entity.toString(), e.getMessage()));
        }
    }

    public T findById(Long id) {
        try {
            this.psSelectById.setObject(1, id);
            ResultSet resultSet = psSelectById.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            T entity = getEntityFromResultSet(resultSet);
            resultSet.close();
            return entity;
        } catch (Exception e) {
            throw new ORMException("Произошла ошибка при попытке получить сущность из таблицы %s по её идентификатору %s: %s"
                    .formatted(tableName, id, e.getMessage()));
        }
    }

    public List<T> findAll() {
        try (ResultSet resultSet = psSelectAll.executeQuery()) {
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(getEntityFromResultSet(resultSet));
            }
            return entities;
        } catch (Exception e) {
            throw new ORMException("Произошла ошибка при поиске всех сущностей из таблицы " + tableName);
        }
    }

    private T getEntityFromResultSet(ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        T entity = cls.getConstructor().newInstance();
        setterMethodMap.get(idField).invoke(entity, resultSet.getObject(1));
        for (int i = 0; i < cachedFields.size(); i++) {
            setterMethodMap.get(cachedFields.get(i)).invoke(entity, resultSet.getObject(i + 2));
        }
        return entity;
    }

    public void update(T entity) {
        try {
            for (int i = 0; i < cachedFields.size(); i++) {
                this.psUpdate.setObject(i + 1, getterMethodMap.get(cachedFields.get(i)).invoke(entity));
            }
            this.psUpdate.setObject(cachedFields.size() + 1, getterMethodMap.get(idField).invoke(entity));
            this.psUpdate.executeUpdate();
        } catch (Exception e) {
            throw new ORMException("При попытке обновить сущность в таблице %s произошла ошибка: %s"
                    .formatted(tableName, e.getMessage()));
        }
    }

    public void deleteById(Long id) {
        try {
            this.psDeleteById.setObject(1, id);
            this.psDeleteById.executeUpdate();
        } catch (Exception e) {
            throw new ORMException("При попытке удалить запись с идентификатором %s в таблице %s произошла ошибка: %s"
                    .formatted(id, tableName, e.getMessage()));
        }
    }

    private void prepareInsert() {
        StringBuilder query = new StringBuilder("insert into ").append(tableName).append(" (");
        for (Field f : cachedFields) {
            query.append(getFieldName(f)).append(", ");
        }
        query.setLength(query.length() - 2);
        query.append(") values (").append("?, ".repeat(cachedFields.size()));
        query.setLength(query.length() - 2);
        query.append(");");
        try {
            psInsert = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализировать репозиторий для класса " + cls.getName() + ". Ошибка: " + e.getMessage());
        }
    }

    private void prepareSelectAll() {
        try {
            psSelectAll = dataSource.getConnection().prepareStatement(getDefaultSelectQuerySB().toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализировать репозиторий для класса " + cls.getName() + ". Ошибка: " + e.getMessage());
        }
    }

    private void prepareSelectById() {
        try {
            StringBuilder selectQueryBuilder = getDefaultSelectQuerySB();
            selectQueryBuilder.append(" where ").append(getFieldName(idField)).append(" = ?");
            this.psSelectById = dataSource.getConnection().prepareStatement(selectQueryBuilder.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализировать репозиторий для класса " + cls.getName() + ". Ошибка: " + e.getMessage());
        }
    }

    private void prepareDeleteById() {
        StringBuilder deleteByIdQuery = new StringBuilder("delete from ").append(tableName).append(" where id = ?");
        try {
            psDeleteById = dataSource.getConnection().prepareStatement(deleteByIdQuery.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализровать репозиторий для класса " + cls.getName() + ". Ошибка: " + e.getMessage());
        }
    }

    private void prepareUpdate() {
        StringBuilder updateQuery = new StringBuilder("update ").append(tableName).append(" set ");
        for (Field f : cachedFields) {
            updateQuery.append(getFieldName(f)).append(" = ?, ");
        }
        updateQuery.setLength(updateQuery.length() - 2);
        updateQuery.append(" where id = ?");
        try {
            this.psUpdate = dataSource.getConnection().prepareStatement(updateQuery.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализровать репозиторий для класса " + cls.getName() + ". Ошибка: " + e.getMessage());
        }
    }

    private StringBuilder getDefaultSelectQuerySB() {
        StringBuilder query = new StringBuilder("select ");
        query.append(getFieldName(idField));
        for (Field f : cachedFields) {
            query.append(", ").append(getFieldName(f));
        }
        query.append(" from ").append(tableName);
        return query;
    }

    private String getFieldName(Field field) {
        RepositoryField annotation = field.getAnnotation(RepositoryField.class);
        if (annotation == null) {
            return field.getName();
        }
        String fieldName = annotation.name();
        return !"".equals(fieldName) ? fieldName : field.getName();
    }

}
