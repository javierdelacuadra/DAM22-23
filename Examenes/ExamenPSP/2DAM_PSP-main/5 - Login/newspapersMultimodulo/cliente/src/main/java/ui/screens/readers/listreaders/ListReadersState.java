package ui.screens.readers.listreaders;

import lombok.*;
import org.miutils.domain.modelo.Newspaper;
import org.miutils.domain.modelo.Reader;
import org.miutils.domain.modelo.TypeArt;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ListReadersState {

    private String error;
    private List<Reader> readers;

    private List<TypeArt> typeArts;

    private List<Newspaper> newspaperList;
    private List<Reader> readersFilteredType;

    private List<Reader> readersFilteredNewspaper;

    private Reader readerSelected;
    private boolean isLoading;
    private String warningMessage;
    private String successMessage;

}
