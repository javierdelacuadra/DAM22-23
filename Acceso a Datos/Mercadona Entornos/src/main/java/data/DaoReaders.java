package data;

import config.ConfigXML;
import jakarta.inject.Inject;
import modelo.Reader;

import java.util.ArrayList;
import java.util.List;

public class DaoReaders {

    private final ConfigXML configXML;

    @Inject
    public DaoReaders(ConfigXML configXML) {
        this.configXML = configXML;
    }

    public List<Reader> getReaders() {
        String path = configXML.getProperty("xmlReaderPath");
        return new ArrayList<>();
    }
}
