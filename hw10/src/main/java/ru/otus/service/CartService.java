package ru.otus.service;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.otus.model.Product;
import ru.otus.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class CartService {

    private final List<Product> productsCart = new ArrayList<>();

    private final ProductRepository productRepository;

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Long id) {
        this.productsCart.add(productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product with id %d not found".formatted(id))));
    }

    public void deleteProduct(Long id) {
        this.productsCart.remove(productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product with id %d not found".formatted(id))));
    }

    @Override
    public String toString() {
        return "Cart{" +
                "productsCart=" + productsCart +
                '}';
    }
}
