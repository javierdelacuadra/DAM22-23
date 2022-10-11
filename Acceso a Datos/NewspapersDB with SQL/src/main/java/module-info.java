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
    requires jakarta.xml.bind;
    requires io.vavr;

    exports ui;
    exports ui.common;
    exports ui.pantallas.pantallalogin;
    exports ui.pantallas.pantallamain;
    exports ui.pantallas.addarticlescreen;
    exports ui.pantallas.listarticlescreen;
    exports ui.pantallas.deletenewspaperscreen;
    exports ui.pantallas.listnewspaperscreen;
    exports ui.pantallas.listreaderscreen;
    exports ui.pantallas.deletereaderscreen;
    exports ui.pantallas.common;
    exports servicios;
    exports config;
    exports data;
    exports di;
    exports modelo;

    opens modelo to javafx.base, com.google.gson, jakarta.xml.bind;
    opens config;
    opens servicios;
    opens data;
    opens common;
    opens ui;
    opens ui.common;
    opens ui.pantallas.pantallalogin to javafx.fxml;
    opens ui.pantallas.pantallamain to javafx.fxml;
    opens ui.pantallas.addarticlescreen to javafx.fxml;
    opens ui.pantallas.listarticlescreen to javafx.fxml;
    opens ui.pantallas.deletenewspaperscreen to javafx.fxml;
    opens ui.pantallas.listnewspaperscreen to javafx.fxml;
    opens ui.pantallas.listreaderscreen to javafx.fxml;
    opens ui.pantallas.deletereaderscreen to javafx.fxml;
    opens ui.pantallas.common;
}