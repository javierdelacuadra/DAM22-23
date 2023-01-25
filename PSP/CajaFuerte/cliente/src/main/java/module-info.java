module MercadonaFX {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;

    requires lombok;
    requires com.google.gson;
    requires io.vavr;
    requires java.sql;
    requires org.yaml.snakeyaml;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires okhttp3;
    requires io.reactivex.rxjava3;
    requires retrofit2.adapter.rxjava3;
    requires jakarta.inject;
    requires jakarta.annotation;
    requires jakarta.cdi;
    requires domain;
    requires retrofit2.converter.scalars;
    requires org.apache.logging.log4j;
    requires seguridad;

    exports ui;
    exports ui.common;
    exports ui.pantallas.common.constantes;
    exports ui.pantallas.pantallamain;
    exports ui.pantallas.addreaderscreen;
    exports ui.pantallas.pantallalogin;
    exports ui.pantallas.listcarpetas;
    exports ui.pantallas.addcarpeta;
    exports ui.pantallas.carpetascompartidas;
    exports ui.pantallas.common;
    exports servicios;
    exports servicios.modelo;
    exports config;
    exports data;
    exports data.common;
    exports data.network;
    exports di;
    exports di.common;
    exports data.retrofit;
    exports data.retrofit.common;

    opens config;
    opens servicios;
    opens data;
    opens config.common;
    opens ui;
    opens ui.common;
    opens ui.pantallas.pantallamain to javafx.fxml;
    opens ui.pantallas.addreaderscreen to javafx.fxml;
    opens ui.pantallas.pantallalogin to javafx.fxml;
    opens ui.pantallas.listcarpetas to javafx.fxml;
    opens ui.pantallas.addcarpeta to javafx.fxml;
    opens ui.pantallas.carpetascompartidas to javafx.fxml;
    opens ui.pantallas.common;
}