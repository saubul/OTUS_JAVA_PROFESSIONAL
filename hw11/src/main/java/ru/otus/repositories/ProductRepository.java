package ru.otus.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.container.ProductContainer;
import ru.otus.entity.Product;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final ProductContainer productContainer;

    public List<Product> findAll() {
        return productContainer.getProductList();
    }

    public Product create(Product p) {
        return productContainer.addProduct(p);
    }

    public void deleteById(Long id) {
        productContainer.deleteById(id);
    }

}
