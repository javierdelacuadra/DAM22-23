package data.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import config.Configuracion;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Article;
import modelo.Cliente;
import modelo.Producto;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Log4j2

public class Database {

    private final Gson gson;
    private final Configuracion configuracion;

    @Inject
    public Database(Gson gson, Configuracion configuracion) {
        this.gson = gson;
        this.configuracion = configuracion;
    }

    public List<Producto> loadProductos() {
        Type userListType = new TypeToken<ArrayList<Producto>>() {
        }.getType();

        List<Producto> productos = null;
        try {
            productos = gson.fromJson(
                    new FileReader(configuracion.getPathProductos()),
                    userListType);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return productos;
    }

    public List<Article> loadArticles() {
        List<Article> articles = new ArrayList<>();
        int lines = 0;
        try {
            lines = Files.readAllLines(Paths.get(configuracion.getPathArticles())).size();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        try (FileInputStream fis = new FileInputStream(configuracion.getPathArticles());
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            for (int i = 0; i < lines; i++) {
                articles.add((Article) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public boolean saveArticles(List<Article> articles) {
        try (FileOutputStream fos = new FileOutputStream(configuracion.getPathArticles());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Article article : articles) {
                oos.writeObject(article);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean saveProductos(List<Producto> productos) {
        try (FileWriter fw = new FileWriter("data/productos.json")) {
            gson.toJson(productos, fw);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public Map<String, Cliente> loadClientes() {
        Type userListType = new TypeToken<Map<String, Cliente>>() {
        }.getType();

        Map<String, Cliente> clientes = new LinkedHashMap<>();
        try {
            clientes = gson.fromJson(
                    new FileReader(configuracion.getPathClientes()),
                    userListType);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return clientes;
    }

    public boolean saveClientes(Map<String, Cliente> clientes) {
        try (FileWriter fw = new FileWriter("data/clientes.json")) {
            gson.toJson(clientes, fw);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }
}
