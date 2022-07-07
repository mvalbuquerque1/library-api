package com.marcus.libraryapi.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookPostRequestBody {

    //@NotEmpty(message = "Title can't be empty")
    private String title;
    //@NotEmpty(message = "Title can't be empty")
    private String author;
    private int numberPages;
}
