package com.marcus.libraryapi.support;

import com.marcus.libraryapi.domain.entities.Book;

public class BookCreator {

    public static Book createBookToBeSaved() {
        return Book.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .numberPages(500)
                .build();
    }

    public static Book createValidBook() {
        return Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .numberPages(500)
                .build();
    }

    public static Book createValidUpdatedBook() {
        return Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .numberPages(500)
                .build();
    }
}
