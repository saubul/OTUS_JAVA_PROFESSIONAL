package ru.otus.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.entity.Product;
import ru.otus.repositories.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product create(Product p) {
        return productRepository.create(p);
    }

    public String deleteById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            return e.getMessage();
        }

        return "Product with id %d is removed".formatted(id);
    }

}
