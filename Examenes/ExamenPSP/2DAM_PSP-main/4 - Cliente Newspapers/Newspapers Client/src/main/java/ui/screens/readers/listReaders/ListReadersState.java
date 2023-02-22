package ui.screens.readers.listReaders;

import model.Newspaper;
import model.Reader;
import model.TypeArt;
import lombok.Data;

import java.util.List;

@Data
public class ListReadersState {

    private String error;
    private List<Reader> readers;

    private List<TypeArt> typeArts;

    private List<Newspaper> newspaperList;
    private List<Reader> readersFilteredType;

    private List<Reader> readersFilteredNewspaper;
    public ListReadersState(String error, List<Reader> readers, List<TypeArt> typeArts, List<Reader> articlesFiltered, List<Newspaper> newspaperList, List<Reader> readersFilteredNewspaper) {
        this.error = error;
        this.readers = readers;
        this.typeArts = typeArts;
        this.readersFilteredType = articlesFiltered;
        this.newspaperList = newspaperList;
        this.readersFilteredNewspaper = readersFilteredNewspaper;
    }

}
