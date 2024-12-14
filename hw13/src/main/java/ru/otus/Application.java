package ru.otus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.Hibernate;
import ru.otus.entity.Address;
import ru.otus.entity.Client;
import ru.otus.entity.Phone;

import java.util.Set;
import java.util.logging.Logger;

public class Application {

    public static final String PERSISTENCE_UNIT_NAME = "SingleUnit";

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

        Address address = new Address();
        address.setStreet("STREET_1");

        Client client = new Client();
        client.setName("CLIENT_1");
        client.setAddress(address);

        Phone phone1 = new Phone();
        phone1.setNumber("PHONE_1");
        phone1.setClient(client);

        Phone phone2 = new Phone();
        phone2.setNumber("PHONE_2");
        phone2.setClient(client);

        client.setPhones(Set.of(phone1, phone2));

        EntityTransaction entityTransaction = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityTransaction = entityManager.getTransaction();

            entityTransaction.begin();

            entityManager.persist(client);

            entityTransaction.commit();

        } catch (Exception e) {
            if (entityTransaction != null && entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }

        LOGGER.info(client.toString());

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityTransaction = entityManager.getTransaction();

            entityTransaction.begin();

            client = entityManager.find(Client.class, 1L);

            // Вредный способ инициализации Lazy сущностей, но для ДЗ, думаю, подойдет
            Hibernate.initialize(client.getPhones());

            entityTransaction.commit();

        } catch (Exception e) {
            if (entityTransaction != null && entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }

        LOGGER.info(client.toString());

    }

}
