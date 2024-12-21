package ru.otus;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.entity.Book;
import ru.otus.service.BookService;

import java.util.logging.Logger;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {

    private final BookService bookService;

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {

        // findAll
        LOGGER.info(bookService.findAll().toString());

        // create
        LOGGER.info(bookService.save(new Book(null, "TEST")).toString());

        // update
        LOGGER.info(bookService.save(new Book(5L, "TEST2")).toString());

        // findById
        LOGGER.info(bookService.findById(5L).toString());

        // deleteById
        bookService.deleteById(5L);

        // check deleteById
        LOGGER.info(bookService.findAll().toString());
    }
}
