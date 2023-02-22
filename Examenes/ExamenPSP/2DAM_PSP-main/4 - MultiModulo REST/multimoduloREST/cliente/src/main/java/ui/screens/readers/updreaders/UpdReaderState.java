package ui.screens.readers.updreaders;

import lombok.Data;
import org.miutils.domain.modelo.Reader;

import java.util.List;

@Data
public class UpdReaderState {

    private String error;

    private Reader readerSelected;

    private Integer readerPosition;
    private List<Reader> allReaders;

    private boolean isLoading;
    private String confirmation;

    public UpdReaderState(String error, Reader readerSelected, Integer readerPosition, List<Reader> allReaders, boolean isLoading, String confirmation) {
        this.error = error;
        this.readerSelected = readerSelected;
        this.readerPosition = readerPosition;
        this.allReaders = allReaders;
        this.isLoading = isLoading;
        this.confirmation = confirmation;
    }
}
