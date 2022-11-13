package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Query2 {
    private int id;
    private String name_article;
    private int idType;
    private String name_newspaper;
}
