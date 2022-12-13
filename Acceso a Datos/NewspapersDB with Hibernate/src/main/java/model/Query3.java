package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Query3 {

    private int id;
    private String name_article;
    private int id_reader;
    private int rating;
    private int bad_ratings;
}
