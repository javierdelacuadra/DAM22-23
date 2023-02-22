package ui.screens.readers.delReaders;

import model.Reader;
import lombok.Data;

import java.util.List;

@Data
public class DelReaderState {

    private String error;
    private List<Reader> readerList;

    private Reader readerSelected;

    private String warningMessage;

    private String successMessage;

    public DelReaderState(String error, List<Reader> readerList, Reader readerSelected, String warningMessage, String successMessage) {
        this.error = error;
        this.readerList = readerList;
        this.readerSelected = readerSelected;
        this.warningMessage = warningMessage;
        this.successMessage = successMessage;
    }
}
