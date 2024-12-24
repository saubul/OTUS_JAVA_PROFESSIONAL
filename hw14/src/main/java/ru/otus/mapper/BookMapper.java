package ru.otus.mapper;

import org.springframework.stereotype.Component;
import ru.otus.dto.BookDto;
import ru.otus.entity.Book;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle());
    }

}
