package com.marcus.libraryapi.support;

import com.marcus.libraryapi.domain.requests.BookPostRequestBody;

public class BookPostRequestBodyCreator {
    public static BookPostRequestBody createBookPostRequestBody(){
        return BookPostRequestBody.builder()
                .title(BookCreator.createBookToBeSaved().getTitle())
                .author(BookCreator.createBookToBeSaved().getAuthor())
                .numberPages(BookCreator.createBookToBeSaved().getNumberPages())
                .build();
    }
}
