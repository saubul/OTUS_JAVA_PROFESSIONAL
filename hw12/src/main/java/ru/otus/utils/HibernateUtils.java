package ru.otus.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.otus.entity.Customer;
import ru.otus.entity.Product;

public final class HibernateUtils {

    private HibernateUtils(){}

    private static SessionFactory SESSION_FACTORY;

    private static void configureFactory() {
        try {
            SESSION_FACTORY = new Configuration()
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Product.class)
                    .configure()
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Ошибка конфигурирования объекта SessionFactory: " + ex.getMessage());
        }
    }

    public static SessionFactory getFactory() {
        if (SESSION_FACTORY == null) {
            configureFactory();
        }

        return SESSION_FACTORY;
    }

}
