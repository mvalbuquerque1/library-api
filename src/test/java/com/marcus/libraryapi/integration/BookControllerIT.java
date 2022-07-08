package com.marcus.libraryapi.integration;


import com.marcus.libraryapi.domain.entities.Book;
import com.marcus.libraryapi.domain.requests.BookPostRequestBody;
import com.marcus.libraryapi.repositories.BookRepository;
import com.marcus.libraryapi.support.BookCreator;
import com.marcus.libraryapi.support.BookPostRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookControllerIT {
    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;
    @Autowired
    private BookRepository bookRepository;

    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("user", "user");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("listAll returns list of book when successful")
    void listAll_ReturnsListOfBooks_WhenSuccessful() {
        Book savedBook = bookRepository.save(BookCreator.createBookToBeSaved());

        String expectedName = savedBook.getTitle();

        List<Book> books = testRestTemplateRoleUser.exchange("/books/", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Book>>() {
                }).getBody();

        Assertions.assertThat(books)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(books.get(0).getTitle()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns book when successful")
    void findById_ReturnsBook_WhenSuccessful() {
        Book savedBook = bookRepository.save(BookCreator.createBookToBeSaved());

        Long expectedId = savedBook.getId();

        Book book = testRestTemplateRoleUser.getForObject("/books/{id}", Book.class, expectedId);

        Assertions.assertThat(book).isNotNull();

        Assertions.assertThat(book.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns a list of book when successful")
    void findByName_ReturnsListOfBook_WhenSuccessful() {
        Book savedBook = bookRepository.save(BookCreator.createBookToBeSaved());

        String expectedName = savedBook.getTitle();

        String url = String.format("/books/find?title=%s", expectedName);

        List<Book> books = testRestTemplateRoleUser.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Book>>() {
                }).getBody();

        Assertions.assertThat(books)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(books.get(0).getTitle()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of book when book is not found")
    void findByName_ReturnsEmptyListOfBook_WhenBookIsNotFound() {

        List<Book> books = testRestTemplateRoleUser.exchange("/books/find?title=Java", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Book>>() {
                }).getBody();

        Assertions.assertThat(books)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("save returns book when successful")
    void save_ReturnsBook_WhenSuccessful() {

        BookPostRequestBody bookPostRequestBody = BookPostRequestBodyCreator.createBookPostRequestBody();

        ResponseEntity<Book> bookResponseEntity = testRestTemplateRoleUser.postForEntity("/books", bookPostRequestBody, Book.class);

        Assertions.assertThat(bookResponseEntity).isNotNull();
        Assertions.assertThat(bookResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(bookResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(bookResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("replace updates book when successful")
    void replace_UpdatesBook_WhenSuccessful() {
        Book savedBook = bookRepository.save(BookCreator.createBookToBeSaved());

        savedBook.setTitle("new name");

        ResponseEntity<Void> bookResponseEntity = testRestTemplateRoleUser.exchange("/books",
                HttpMethod.PUT, new HttpEntity<>(savedBook), Void.class);

        Assertions.assertThat(bookResponseEntity).isNotNull();

        Assertions.assertThat(bookResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes book when successful")
    void delete_RemovesBook_WhenSuccessful() {
        Book savedBook = bookRepository.save(BookCreator.createBookToBeSaved());

        ResponseEntity<Void> bookResponseEntity = testRestTemplateRoleUser.exchange("/books/{id}",
                HttpMethod.DELETE, null, Void.class, savedBook.getId());

        Assertions.assertThat(bookResponseEntity).isNotNull();

        Assertions.assertThat(bookResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
