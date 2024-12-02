package ru.otus.container;

import org.springframework.stereotype.Component;
import ru.otus.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ProductContainer {

    private final List<Product> productList = new ArrayList<>();

    private static AtomicLong productId = new AtomicLong(0);

    public Product addProduct(Product p) {
        p.setId(productId.incrementAndGet());
        productList.add(p);
        return p;
    }

    public void deleteById(Long id) {
        productList.remove(productList.stream()
                .filter(product -> Objects.equals(product.getId(), id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product with id %d not found".formatted(id))));
    }

    public List<Product> getProductList() {
        return productList;
    }
}
