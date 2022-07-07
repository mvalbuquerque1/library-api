package com.marcus.libraryapi.integration;

import com.marcus.libraryapi.domain.entities.Book;
import com.marcus.libraryapi.repositories.BookRepository;
import com.marcus.libraryapi.support.BookCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

public class BookControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("listAll returns list of book when successful")
    void listAll_ReturnsListOfBooks_WhenSuccessful() {
        //Book savedBook = bookRepository.save(BookCreator.createBookToBeSaved());

//        String expectedName = savedBook.getTitle();

        List<Book> books = testRestTemplate.exchange("/books/3", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Book>>() {
                }).getBody();

        Assertions.assertThat(books)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        //Assertions.assertThat(books.get(2).getTitle()).isEqualTo(expectedName);
    }

}
