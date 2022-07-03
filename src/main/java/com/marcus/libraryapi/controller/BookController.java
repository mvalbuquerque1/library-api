package com.marcus.libraryapi.controller;

import com.marcus.libraryapi.domain.dto.response.BookResponse;
import com.marcus.libraryapi.domain.entities.Book;
import com.marcus.libraryapi.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<BookResponse>> fetchBooks() {
        var books = service.findAll();
        return ResponseEntity.ok(books);
    }
}
