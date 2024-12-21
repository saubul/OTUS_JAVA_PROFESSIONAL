package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dto.BookDto;
import ru.otus.entity.Book;
import ru.otus.mapper.BookMapper;
import ru.otus.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Transactional
    public BookDto save(Book book) {
        return bookMapper.toDto(bookRepository.save(book));
    }

    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with %s not found".formatted(id)));
        return bookMapper.toDto(book);
    }

    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

}
