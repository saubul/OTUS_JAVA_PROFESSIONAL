package ru.otus.repository;

import org.springframework.stereotype.Component;
import ru.otus.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ProductRepository {

    private final List<Product> productList;

    public ProductRepository(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> findAll() {
        return productList;
    }

    public Optional<Product> findById(Long id) {
        return productList.stream().filter(product -> Objects.equals(id, product.getId())).findFirst();
    }

}
