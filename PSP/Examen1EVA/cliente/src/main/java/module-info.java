module cliente {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires MaterialFX;

    requires jakarta.inject;
    requires com.google.gson;
    requires jakarta.cdi;
    requires org.yaml.snakeyaml;
    requires io.reactivex.rxjava3;
    requires io.vavr;
    requires okhttp3;
    requires retrofit2;
    requires retrofit2.converter.scalars;
    requires retrofit2.converter.gson;
    requires retrofit2.adapter.rxjava3;
    requires domain;

    exports ui to javafx.graphics;
    exports ui.pantallas.pantallamain;
    exports ui.pantallas.pantallaequipos;
    exports ui.common;
    exports ui.consola;
    exports config;
    exports dao;
    exports dao.retrofit;
    exports di;
    exports servicios;

    opens ui;
    opens config;
    opens servicios;
    opens dao;
    opens ui.pantallas.pantallamain to javafx.fxml;
    opens ui.pantallas.pantallaequipos to javafx.fxml;
}