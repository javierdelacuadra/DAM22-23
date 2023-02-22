module Retrofit {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires lombok;
    requires org.apache.logging.log4j;
    requires jakarta.inject;
    requires jakarta.cdi;
    requires retrofit2;
    requires okhttp3;
    requires retrofit2.converter.moshi;
    requires com.squareup.moshi;
    requires io.vavr;

    exports ui.main to javafx.graphics;
    exports ui.pantallas.listadociudades;
    exports ui.pantallas.paisdetalle;
    exports dominio.modelo;
    exports dao;
    exports dominio.serivcios;
    exports dominio.serivcios.impl;
    exports dao.retrofit.llamadas;
    exports dao.retrofit.produces;
    exports dao.retrofit.modelo;
    exports dao.impl;
    exports dao.common;
    exports config;
    exports config.common;

    opens ui.pantallas.common;
    opens ui.pantallas.principal;
    opens ui.pantallas.listadociudades;
    opens ui.pantallas.paisdetalle to javafx.fxml;
    opens ui.main;
    opens config;
    opens fxml;
    opens dominio.modelo to com.squareup.moshi;
    opens dao.common;
    opens dao.retrofit.modelo to com.squareup.moshi;
    opens config.common;
}
