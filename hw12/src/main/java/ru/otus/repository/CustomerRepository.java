package ru.otus.repository;

import jakarta.persistence.EntityManagerFactory;
import ru.otus.entity.Customer;

import java.util.List;

public class CustomerRepository extends AbstractRepository<Customer> {

    public CustomerRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, Customer.class);
    }

    public List<Customer> findAllByProduct(Long productId) {
        return executeOperationList(e -> e.createQuery("select c from Customer c join c.products p where p.id = :productId", Customer.class)
                .setParameter("productId", productId)
                .getResultList());
    }

}
