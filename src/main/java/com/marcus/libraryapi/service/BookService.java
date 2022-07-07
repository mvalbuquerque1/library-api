package com.marcus.libraryapi.service;

import com.marcus.libraryapi.domain.entities.Book;
import com.marcus.libraryapi.domain.requests.BookPostRequestBody;
import com.marcus.libraryapi.domain.requests.BookPutRequestBody;
import com.marcus.libraryapi.exceptions.BadRequestException;
import com.marcus.libraryapi.repositories.BookRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public List<Book> findByTitle(String title) {
        return repository.findByTitle(title);
    }

    public Book findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("Book not Found"));
    }


    @Transactional
    public Book save(BookPostRequestBody bookRequest) {
        return repository.save(Book.builder().title(bookRequest.getTitle()).author(bookRequest.getAuthor()).numberPages(bookRequest.getNumberPages()).build());
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    public void update(BookPutRequestBody bookPutRequestBody) {
        Book savedBook = findById(bookPutRequestBody.getId());
        Book book = Book.builder()
                .id(savedBook.getId())
                .title(bookPutRequestBody.getTitle())
                .author(bookPutRequestBody.getAuthor())
                .numberPages(bookPutRequestBody.getNumberPages())
                .build();
        repository.save(book);
    }
}
