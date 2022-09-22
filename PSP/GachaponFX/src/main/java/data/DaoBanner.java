package data;

import jakarta.inject.Inject;
import modelo.Banner;
import modelo.Personaje;

import java.util.ArrayList;
import java.util.List;

public class DaoBanner {

    private final Database db;

    @Inject
    public DaoBanner(Database db) {
        this.db = db;
    }

   public void cosa() {

   }
}