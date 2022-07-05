package com.marcus.libraryapi.domain.requests;

import lombok.Data;

@Data
public class BookPutRequestBody {

    private Long id;
    private String title;
}
