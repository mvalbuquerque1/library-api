package com.marcus.libraryapi.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookPutRequestBody {

    private Long id;
    private String title;
    private String author;
    private int numberPages;
}
