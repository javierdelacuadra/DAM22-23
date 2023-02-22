package domain.servicios;

import org.miutils.domain.modelo.Newspaper;

import java.util.List;

public interface NewspapersServices {
    List<Newspaper> getAll();

    Newspaper get(String idNews);

    Newspaper add (Newspaper newspaper);

    Newspaper update(Newspaper news);
    boolean delete(String idNews);

}
