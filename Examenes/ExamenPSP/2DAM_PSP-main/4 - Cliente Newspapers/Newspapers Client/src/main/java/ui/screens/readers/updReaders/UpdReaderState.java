package ui.screens.readers.updReaders;

import model.Reader;
import lombok.Data;

import java.util.List;

@Data
public class UpdReaderState {

    private String error;

    private Reader readerSelected;

    private Integer readerPosition;
    private List<Reader> allReaders;

    private String confirmation;

    public UpdReaderState(String error, Reader readerSelected, Integer readerPosition, List<Reader> allReaders, String confirmation) {
        this.error = error;
        this.readerSelected = readerSelected;
        this.readerPosition = readerPosition;
        this.allReaders = allReaders;
        this.confirmation = confirmation;
    }
}
