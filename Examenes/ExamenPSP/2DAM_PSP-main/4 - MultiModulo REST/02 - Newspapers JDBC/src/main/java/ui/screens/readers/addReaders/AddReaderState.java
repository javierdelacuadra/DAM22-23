package ui.screens.readers.addReaders;

import lombok.Data;
import model.Reader;

import java.util.List;

@Data
public class AddReaderState {

    private String error;
    private List<Reader> allReaders;

    private String welcomeMessage;


    public AddReaderState(String error, List<Reader> allReaders, String welcomeMessage) {
        this.error = error;
        this.allReaders = allReaders;
        this.welcomeMessage = welcomeMessage;
    }
}
