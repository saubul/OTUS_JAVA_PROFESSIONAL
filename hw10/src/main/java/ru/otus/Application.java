package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.config.Config;
import ru.otus.service.CartService;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class, CartService.class);

        CartService cartService = context.getBean(CartService.class);
        cartService.addProduct(1L);
        cartService.addProduct(3L);
        cartService.deleteProduct(3L);

        System.out.println(cartService);

        // Проверка скоупа "Prototype" у CartService

        CartService cartService2 = context.getBean(CartService.class);
        cartService2.addProduct(2L);
        cartService2.addProduct(4L);
        cartService2.addProduct(5L);

        System.out.println(cartService2);
    }

}
