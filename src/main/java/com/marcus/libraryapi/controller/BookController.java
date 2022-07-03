package com.marcus.libraryapi.controller;

import com.marcus.libraryapi.domain.dto.request.BookRequest;
import com.marcus.libraryapi.domain.dto.response.BookResponse;
import com.marcus.libraryapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<BookResponse> save(@RequestBody BookRequest bookRequest) {
        var book = service.save(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(BookResponse.of(book));
    }
}
