package com.basicrest.springrestbasic.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Article{
    private int id;
    private String title;
    private String type;
    private int idNewspaper;

}
