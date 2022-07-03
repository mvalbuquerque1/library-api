package com.marcus.libraryapi.service;

import com.marcus.libraryapi.domain.dto.response.BookResponse;
import com.marcus.libraryapi.domain.entities.Book;
import com.marcus.libraryapi.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<BookResponse> findAll() {
        return repository.findAll().stream().map(BookResponse::of).collect(Collectors.toList());
    }
}
