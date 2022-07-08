package com.marcus.libraryapi.service;

import com.marcus.libraryapi.domain.entities.Book;
import com.marcus.libraryapi.exceptions.BadRequestException;
import com.marcus.libraryapi.repositories.BookRepository;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class BookServiceTest {
    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepositoryMock;

    @BeforeEach
    void setUp() {

        BDDMockito.when(bookRepositoryMock.findAll())
                .thenReturn(List.of(BookCreator.createValidBook()));

        BDDMockito.when(bookRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(BookCreator.createValidBook()));

        BDDMockito.when(bookRepositoryMock.findByTitle(ArgumentMatchers.anyString()))
                .thenReturn(List.of(BookCreator.createValidBook()));

        BDDMockito.when(bookRepositoryMock.save(ArgumentMatchers.any(Book.class)))
                .thenReturn(BookCreator.createValidBook());

        BDDMockito.doNothing().when(bookRepositoryMock).delete(ArgumentMatchers.any(Book.class));
    }

    @Test
    @DisplayName("fetchBooks returns list of book when successful")
    void fetchBooks_ReturnsListOfBooks_WhenSuccessful() {
        String expectedTitle = BookCreator.createValidBook().getTitle();

        List<Book> books = bookService.fetchBooks();

        Assertions.assertThat(books)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(books.get(0).getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    @DisplayName("findById returns book when successful")
    void findById_ReturnsBook_WhenSuccessful() {
        Long expectedId = BookCreator.createValidBook().getId();

        Book book = bookService.findById(1L);

        Assertions.assertThat(book).isNotNull();

        Assertions.assertThat(book.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findById throws BadRequestException when book is not found")
    void findById_ThrowsBadRequestException_WhenBookIsNotFound() {
        BDDMockito.when(bookRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> bookService.findById(1L));
    }

    @Test
    @DisplayName("findByName returns a list of book when successful")
    void findByName_ReturnsListOfBook_WhenSuccessful() {
        String expectedTitle = BookCreator.createValidBook().getTitle();

        List<Book> books = bookService.findByTitle("book");

        Assertions.assertThat(books)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(books.get(0).getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    @DisplayName("findByTitle returns an empty list of book when book is not found")
    void findByTitle_ReturnsEmptyListOfBook_WhenBookIsNotFound() {
        BDDMockito.when(bookRepositoryMock.findByTitle(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Book> books = bookService.findByTitle("book");

        Assertions.assertThat(books)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("save returns book when successful")
    void save_ReturnsBook_WhenSuccessful() {

        Book book = bookService.save(BookPostRequestBodyCreator.createBookPostRequestBody());

        Assertions.assertThat(book).isNotNull().isEqualTo(BookCreator.createValidBook());

    }

    @Test
    @DisplayName("update updates book when successful")
    void update_UpdatesBook_WhenSuccessful() {

        Assertions.assertThatCode(() -> bookService.update(BookPutRequestBodyCreator.createBookPutRequestBody()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("delete removes book when successful")
    void delete_RemovesBook_WhenSuccessful() {

        Assertions.assertThatCode(() -> bookService.delete(1L))
                .doesNotThrowAnyException();

    }
}