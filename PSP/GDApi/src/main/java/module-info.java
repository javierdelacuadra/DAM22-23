module GDApi {
    requires lombok;
    requires jakarta.jakartaee.web.api;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;
    requires org.apache.logging.log4j;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires MaterialFX;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires retrofit2.converter.moshi;
    requires okhttp3;
    requires com.squareup.moshi;
    requires io.vavr;

    exports ui to javafx.graphics;
    exports modelo;
    exports data;
    exports ui.pantallas.pantallabusqueda;
    exports servicios;
    exports di;
    exports config;
    exports retrofit;
    exports ui.pantallas.pantallaniveles;
    exports ui.pantallas.pantallausers;
    exports ui.pantallas.pantallamain;
    exports ui.pantallas.common;
    exports ui.pantallas.pantallainicio;

    opens ui;
    opens config;
    opens modelo;
    opens common;
    opens ui.pantallas.common;
    opens ui.pantallas.pantallamain;
    opens ui.pantallas.pantallainicio;
    opens ui.pantallas.pantallabusqueda;
    opens ui.pantallas.pantallaniveles;
    opens ui.pantallas.pantallausers;
}