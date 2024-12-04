package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.model.Product;
import ru.otus.repository.ProductRepository;
import ru.otus.service.CartService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class Config {

    @Bean
    public ProductRepository productRepository() {

        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            productList.add(new Product((long) i, "Product_" + i, new BigDecimal(i * 2)));
        }
        return new ProductRepository(productList);
    }

}
