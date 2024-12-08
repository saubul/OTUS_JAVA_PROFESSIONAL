package ru.otus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.entity.Product;
import ru.otus.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getAll")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @PostMapping("/create")
    public Product createProduct(@RequestBody Product p) {
        return productService.create(p);
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam(name = "id") Long id) {
        return productService.deleteById(id);
    }

}
