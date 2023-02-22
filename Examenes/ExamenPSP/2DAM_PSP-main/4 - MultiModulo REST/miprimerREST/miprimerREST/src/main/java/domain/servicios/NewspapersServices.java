package domain.servicios;

import domain.modelo.Newspaper;

import java.util.List;

public interface NewspapersServices {
    List<Newspaper> getAll();

     List<Newspaper> getAllSuscribed(String id);

    List<Newspaper> getAllUnsuscribed(String idReader);


    Newspaper get(String idNews);

    Newspaper add (Newspaper newspaper);

    Newspaper update(Newspaper news);
    boolean delete(String idNews);

}
