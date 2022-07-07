package com.marcus.libraryapi.support;

import com.marcus.libraryapi.domain.requests.BookPutRequestBody;

public class BookPutRequestBodyCreator {
    public static BookPutRequestBody createBookPutRequestBody(){
        return BookPutRequestBody.builder()
                .id(BookCreator.createValidUpdatedBook().getId())
                .title(BookCreator.createValidUpdatedBook().getTitle())
                .author(BookCreator.createValidUpdatedBook().getAuthor())
                .numberPages(BookCreator.createValidUpdatedBook().getNumberPages())
                .build();
    }
}
