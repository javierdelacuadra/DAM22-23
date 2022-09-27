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
    exports ui.pantallas.pantallamain;
    exports ui.pantallas.addarticlescreen;
    exports ui.pantallas.deletearticlescreen;
    exports ui.pantallas.listarticlescreen;
    exports ui.pantallas.updatearticlescreen;
    exports ui.pantallas.addnewspaperscreen;
    exports ui.pantallas.deletenewspaperscreen;
    exports ui.pantallas.listnewspaperscreen;
    exports ui.pantallas.updatenewspaperscreen;
    exports ui.pantallas.common;
    exports servicios;
    exports config;
    exports data;
    exports modelo;

    opens modelo to javafx.base, com.google.gson;
    opens config;
    opens servicios;
    opens data;
    opens ui;
    opens ui.common;
    opens ui.pantallas to javafx.fxml;
    opens ui.pantallas.pantallalogin to javafx.fxml;
    opens ui.pantallas.pantallamain to javafx.fxml;
    opens ui.pantallas.addarticlescreen to javafx.fxml;
    opens ui.pantallas.deletearticlescreen to javafx.fxml;
    opens ui.pantallas.listarticlescreen to javafx.fxml;
    opens ui.pantallas.updatearticlescreen to javafx.fxml;
    opens ui.pantallas.addnewspaperscreen to javafx.fxml;
    opens ui.pantallas.deletenewspaperscreen to javafx.fxml;
    opens ui.pantallas.listnewspaperscreen to javafx.fxml;
    opens ui.pantallas.updatenewspaperscreen to javafx.fxml;
    opens ui.pantallas.common;

    /*requires org.junit.jupiter.api;
    requires org.testfx;
    requires org.testfx.junit5;
    requires org.mockito.junit.jupiter;
    requires org.mockito;*/

}