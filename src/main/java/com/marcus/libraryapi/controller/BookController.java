package com.marcus.libraryapi.controller;

import com.marcus.libraryapi.domain.entities.Book;
import com.marcus.libraryapi.domain.requests.BookPostRequestBody;
import com.marcus.libraryapi.domain.requests.BookPutRequestBody;
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
    public ResponseEntity<List<Book>> fetchBooks() {
        var books = service.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Book> findById(@PathVariable long id) {
        var book = service.findById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody BookPostRequestBody bookRequest) {
        var savedBook = service.save(bookRequest);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody BookPutRequestBody bookPutRequestBody) {
        service.update(bookPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
