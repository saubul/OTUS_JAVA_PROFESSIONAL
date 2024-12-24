package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.entity.Book;

@Repository
public interface BookRepository extends ListCrudRepository<Book, Long> {
}
