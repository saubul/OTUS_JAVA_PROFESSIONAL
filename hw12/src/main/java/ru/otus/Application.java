package ru.otus;

import org.hibernate.SessionFactory;
import ru.otus.entity.Customer;
import ru.otus.entity.Product;
import ru.otus.repository.CustomerRepository;
import ru.otus.repository.ProductRepository;
import ru.otus.utils.HibernateUtils;

import java.math.BigDecimal;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.getFactory();

        CustomerRepository customerRepository = new CustomerRepository(sessionFactory);
        ProductRepository productRepository = new ProductRepository(sessionFactory);

        Product product1 = new Product("product_1", BigDecimal.valueOf(1.2));
        Product product2 = new Product("product_2", BigDecimal.valueOf(3.5));
        Customer customer1 = new Customer("customer_1");
        Customer customer2 = new Customer("customer_2");

        customer1.setProducts(List.of(product1, product2));
        customer2.setProducts(List.of(product2));

        productRepository.create(product1);
        productRepository.create(product2);
        customerRepository.create(customer1);
        customerRepository.create(customer2);

        System.out.println(customerRepository.findAll());
        System.out.println(productRepository.findAll());
        System.out.println(customerRepository.findAllByProduct(1L));
        System.out.println(productRepository.findAllByCustomer(2L));
        customerRepository.delete(customer1);
        System.out.println(customerRepository.findAll());
        System.out.println(productRepository.findAll());

    }

}
