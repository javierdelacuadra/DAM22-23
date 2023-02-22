package com.basicrest.springrestbasic.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class Newspaper{
    private int id;
    private String name;
    private LocalDate releaseDate;
    private List<Article> articles;
}
