package ru.otus.migrator;

import ru.otus.datasource.DataSource;
import ru.otus.exception.OtusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class DbMigrator {
    private final DataSource dataSource;

    public DbMigrator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void migrate() {
        InputStream inputStream;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream("dbinit.sql");
        } catch (Exception e) {
            throw new OtusException("При попытке прочитать файл dbinit.sql произошла ошибка: " + e.getMessage());
        }
        if (inputStream == null) {
            throw new OtusException("При попытке прочитать файл dbinit.sql произошла неизвестная ошибка");
        }

        try {
            readAndExecute(inputStream);
        } catch (Exception e) {
            throw new OtusException("При попытке выполнить запросы в файле dbinit.sql произошла ошибка: " + e.getMessage());
        }
    }

    private void readAndExecute(InputStream inputStream) throws IOException, SQLException {

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                dataSource.getStatement().execute(line);
            }
        }

    }

}
