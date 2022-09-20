module MercadonaFX {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;

    requires lombok;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires com.google.gson;
    requires org.apache.logging.log4j;
    requires jakarta.cdi;
    requires jakarta.inject;

    exports ui;
    exports ui.common;
    exports ui.pantallas;
    exports ui.pantallas.pantallalogin;
    exports ui.pantallas.pantalladminclientes;
    exports ui.pantallas.pantallacliente;
    exports ui.pantallas.pantallaadminproductos;
    exports ui.pantallas.pantallaconfig;
    exports ui.pantallas.pantallamain;
    exports ui.pantallas.common;
    exports servicios.impl;
    exports servicios;
    exports data.impl;
    exports config;
    exports di;
    exports data;
    exports modelo;

    opens modelo to javafx.base, com.google.gson;
    opens config;
    opens servicios.impl;
    opens data.impl;
    opens servicios;
    opens data;
    opens ui;
    opens ui.common;
    opens ui.pantallas to javafx.fxml;
    opens ui.pantallas.pantallalogin to javafx.fxml;
    opens ui.pantallas.pantallamain to javafx.fxml;
    opens ui.pantallas.pantallaconfig to javafx.fxml;
    opens ui.pantallas.pantallaadminproductos to javafx.fxml;
    opens ui.pantallas.pantalladminclientes to javafx.fxml;
    opens ui.pantallas.pantallacliente;
    opens ui.pantallas.common;

    /*requires org.junit.jupiter.api;
    requires org.testfx;
    requires org.testfx.junit5;
    requires org.mockito.junit.jupiter;
    requires org.mockito;*/

}