package com.marcus.libraryapi.domain.dto.response;

import com.marcus.libraryapi.domain.entities.Book;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {

    private String isbn;
    private String title;
    private String author;
    private Long numberPages;

    public static BookResponse of(Book book) {
        return disassemble.apply(book);
    }

    private static Function<Book, BookResponse> disassemble =
            book ->
                    BookResponse.builder()
                            .isbn(book.getIsbn())
                            .title(book.getTitle())
                            .author(book.getAuthor())
                            .numberPages(book.getNumberPages())
                            .build();
}
