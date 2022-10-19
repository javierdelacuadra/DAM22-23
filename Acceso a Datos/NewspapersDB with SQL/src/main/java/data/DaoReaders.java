package data;

import common.Constantes;
import config.ConfigXML;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import model.Reader;
import model.Readers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DaoReaders {

    @Inject
    public DaoReaders() {
    }

    public Either<String, List<Reader>> getReaders() throws JAXBException {
        Path xmlFile = Paths
                .get(ConfigXML.getInstance().getProperty(Constantes.XML_READER_PATH));
        Either<String, List<Reader>> readerList;
        try {
            JAXBContext context = JAXBContext.newInstance(Readers.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Readers readersList = (Readers) unmarshaller.unmarshal(Files.newInputStream(xmlFile));
            return Either.right(readersList.getReader());
        } catch (IOException e) {
            readerList = Either.left(e.getMessage());
        }
        return readerList;
    }

    public Either<String, Boolean> saveReaders(List<Reader> readerList) {
        JAXBContext context;
        Marshaller marshaller;
        try {
            context = JAXBContext.newInstance(Readers.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            return Either.left(e.getMessage());
        }

        Readers readers = new Readers();
        readers.setReader(readerList);
        Path xmlFile = Paths
                .get(ConfigXML.getInstance().getProperty(Constantes.XML_READER_PATH));
        try {
            marshaller.marshal(readers, Files.newOutputStream(xmlFile));
        } catch (JAXBException | IOException e) {
            return Either.left(e.getMessage());
        }
        return Either.right(true);
    }
}