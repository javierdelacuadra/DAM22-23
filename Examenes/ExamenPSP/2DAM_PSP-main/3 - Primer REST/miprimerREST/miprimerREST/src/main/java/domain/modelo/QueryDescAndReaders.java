package domain.modelo;

import lombok.Data;

@Data
public class QueryDescAndReaders {
    private String description;
    private int numberReaders;

    public QueryDescAndReaders(String description, int numberReaders) {
        this.description = description;
        this.numberReaders = numberReaders;
    }
}
