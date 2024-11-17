package ru.otus;

import ru.otus.dao.AbstractRepository;
import ru.otus.datasource.DataSource;
import ru.otus.migrator.DbMigrator;
import ru.otus.model.User;

public class Application {

    public static void main(String[] args) {
        DataSource dataSource = null;
        try {
            System.out.println("Сервер запущен");
            dataSource = new DataSource("jdbc:h2:~/dbwe;MODE=PostgreSQL");
            dataSource.connect();
            DbMigrator dbMigrator = new DbMigrator(dataSource);
            dbMigrator.migrate();

            AbstractRepository<User> userAbstractRepository = new AbstractRepository<>(dataSource, User.class);
            System.out.println(userAbstractRepository.findAll());
            userAbstractRepository.save(new User(null, "1", "2", "3"));
            System.out.println(userAbstractRepository.findAll());
            System.out.println(userAbstractRepository.findById(1L));
            System.out.println(userAbstractRepository.findById(2L));

            userAbstractRepository.update(new User(1L, "10", "11", "12"));
            System.out.println(userAbstractRepository.findById(1L));

            userAbstractRepository.deleteById(1L);
            System.out.println(userAbstractRepository.findById(1L));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (dataSource != null) {
                dataSource.close();
            }
            System.out.println("Сервер завершил свою работу");
        }
    }

}
