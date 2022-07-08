package com.marcus.libraryapi.controller;

import com.marcus.libraryapi.domain.entities.Book;
import com.marcus.libraryapi.domain.requests.BookPostRequestBody;
import com.marcus.libraryapi.domain.requests.BookPutRequestBody;
import com.marcus.libraryapi.service.BookService;
import com.marcus.libraryapi.support.BookCreator;
import com.marcus.libraryapi.support.BookPostRequestBodyCreator;
import com.marcus.libraryapi.support.BookPutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookServiceMock;

    @BeforeEach
    void setUp() {
        BDDMockito.when(bookServiceMock.fetchBooks())
                .thenReturn(List.of(BookCreator.createValidBook()));

        BDDMockito.when(bookServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(BookCreator.createValidBook());

        BDDMockito.when(bookServiceMock.findByTitle(ArgumentMatchers.anyString()))
                .thenReturn(List.of(BookCreator.createValidBook()));

        BDDMockito.when(bookServiceMock.save(ArgumentMatchers.any(BookPostRequestBody.class)))
                .thenReturn(BookCreator.createValidBook());

        BDDMockito.doNothing().when(bookServiceMock).update(ArgumentMatchers.any(BookPutRequestBody.class));

        BDDMockito.doNothing().when(bookServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("listAll returns list of book when successful")
    void fetchBooks_ReturnsListOfBooks_WhenSuccessful(){
        String expectedTitle = BookCreator.createValidBook().getTitle();

        List<Book> books = bookController.fetchBooks().getBody();

        Assertions.assertThat(books)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(books.get(0).getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    @DisplayName("findById returns book when successful")
    void findById_ReturnsBook_WhenSuccessful(){
        Long expectedId = BookCreator.createValidBook().getId();

        Book book = bookController.findById(1).getBody();

        Assertions.assertThat(book).isNotNull();

        Assertions.assertThat(book.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByTitle returns a list of book when successful")
    void findByTitle_ReturnsListOfBook_WhenSuccessful(){
        String expectedTitle = BookCreator.createValidBook().getTitle();

        List<Book> books = bookController.findByTitle("book").getBody();

        Assertions.assertThat(books)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(books.get(0).getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    @DisplayName("findByTitle returns an empty list of book when book is not found")
    void findByTitle_ReturnsEmptyListOfBook_WhenBookIsNotFound(){
        BDDMockito.when(bookServiceMock.findByTitle(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Book> books = bookController.findByTitle("book").getBody();

        Assertions.assertThat(books)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("save returns book when successful")
    void save_ReturnsBook_WhenSuccessful(){

        Book book = bookController.save(BookPostRequestBodyCreator.createBookPostRequestBody()).getBody();

        Assertions.assertThat(book).isNotNull().isEqualTo(BookCreator.createValidBook());

    }

    @Test
    @DisplayName("update updates book when successful")
    void update_UpdatesBook_WhenSuccessful(){

        Assertions.assertThatCode(() -> bookController.update(BookPutRequestBodyCreator.createBookPutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = bookController.update(BookPutRequestBodyCreator.createBookPutRequestBody());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes book when successful")
    void delete_RemovesBook_WhenSuccessful(){

        Assertions.assertThatCode(() ->bookController.delete(1))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = bookController.delete(1);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
