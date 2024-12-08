package ru.otus.repository;

import jakarta.persistence.EntityManagerFactory;
import ru.otus.entity.Product;

import java.util.List;

public class ProductRepository extends AbstractRepository<Product> {

    public ProductRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, Product.class);
    }

    public List<Product> findAllByCustomer(Long customerId) {
        return executeOperationList(e -> e.createQuery("select p from Product p join p.customers c where c.id = :customerId", Product.class)
                .setParameter("customerId", customerId)
                .getResultList());
    }

}
