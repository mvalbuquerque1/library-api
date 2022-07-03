package com.marcus.libraryapi.domain.dto.request;

import com.marcus.libraryapi.domain.entities.Book;
import lombok.*;

import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {

    private String title;
    private String author;
    private int numberPages;

    public static Book toDomain(BookRequest bookRequestDTO) {
        return assemble.apply(bookRequestDTO);
    }

    private static Function<BookRequest, Book> assemble =
            dto ->
                    Book.builder()
                            .isbn(UUID.randomUUID().toString())
                            .title(dto.getTitle())
                            .author(dto.getAuthor())
                            .numberPages(dto.numberPages)
                            .build();
}
