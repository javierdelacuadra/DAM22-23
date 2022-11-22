package dao;

import config.ConfigXML;
import dao.common.Constantes;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import model.Objeto;
import model.Objetos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DaoObjetoXML {
    private final ConfigXML configXML;

    @Inject
    public DaoObjetoXML(ConfigXML configXML) {
        this.configXML = configXML;
    }

    //@Inject
    //public DaoObjetoXML() {
    //}

    public Either<String, List<Objeto>> getObjetos() throws JAXBException {
        Path xmlFile = Paths
                .get(ConfigXML.getInstance().getProperty(Constantes.XML_PATH));
        Either<String, List<Objeto>> readerList;
        try {
            JAXBContext context = JAXBContext.newInstance(Objetos.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Objetos readersList = (Objetos) unmarshaller.unmarshal(Files.newInputStream(xmlFile));
            return Either.right(readersList.getObjeto());
        } catch (IOException e) {
            readerList = Either.left(e.getMessage());
        }
        return readerList;
    }

    public Either<String, Boolean> saveObjetos(List<Objeto> objetoList) {
        JAXBContext context;
        Marshaller marshaller;
        try {
            context = JAXBContext.newInstance(Objetos.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            return Either.left(e.getMessage());
        }

        Objetos objetos = new Objetos();
        objetos.setObjeto(objetoList);

        Path xmlFile = Paths
                .get(ConfigXML.getInstance().getProperty(Constantes.XML_PATH));
        try {
            marshaller.marshal(objetos, Files.newOutputStream(xmlFile));
        } catch (JAXBException | IOException e) {
            return Either.left(e.getMessage());
        }
        return Either.right(true);
    }
}