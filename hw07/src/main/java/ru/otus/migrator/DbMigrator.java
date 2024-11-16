package ru.otus.migrator;

import ru.otus.datasource.DataSource;

public class DbMigrator {
    private DataSource dataSource;

    public DbMigrator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void migrate() {
        // читаем файл dbinit.sql
        // выполняем все запросы, чтобы проинициализировать БД
    }
}
