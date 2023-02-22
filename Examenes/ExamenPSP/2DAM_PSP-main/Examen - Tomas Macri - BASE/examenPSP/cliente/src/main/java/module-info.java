module cliente {
//    requires jakarta.inject;
//    requires lombok;
//    requires org.apache.logging.log4j;
//    requires io.vavr;
//    requires io.reactivex.rxjava3;
//    requires retrofit2;
//    requires utils;
//    requires okhttp3;
//    requires com.squareup.moshi;
//    requires jakarta.cdi;
//    requires retrofit2.adapter.rxjava3;
//    requires retrofit2.converter.moshi;
//    requires javafx.graphics;
//    requires javafx.fxml;
//    requires javafx.controls;
//    requires MaterialFX;
//
//
//    exports org.cliente.org.cliente.config;
//    exports org.cliente.org.cliente.config.common;
//    exports org.cliente.org.cliente.ui.getAllJavaFX.main;
//    exports org.cliente.org.cliente.ui.getAllJavaFX.paisdetalle;
//    exports org.cliente.org.cliente.domain.servicios.impl;
//    exports org.cliente.org.cliente.dao;
//    exports org.cliente.org.cliente.dao.retrofit.produces;
//    exports org.cliente.org.cliente.ui.getUnaPersona;
//    exports org.cliente.org.cliente.dao.retrofit.llamadas;
//    exports org.cliente.org.cliente.domain.modelo;
//
//    opens org.cliente.org.cliente.config;
//    opens org.cliente.org.cliente.dao;
//    opens org.cliente.org.cliente.dao.common;
//    opens org.cliente.org.cliente.dao.retrofit.produces;
//    opens org.cliente.org.cliente.ui.getAllJavaFX.main;
//    opens org.cliente.org.cliente.ui.getAllJavaFX.common;
//    opens org.cliente.org.cliente.ui.getAllJavaFX.paisdetalle;
//    opens org.cliente.fxml;


    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;

    requires lombok;

    requires jakarta.inject;
    requires jakarta.cdi;

    requires io.vavr;

    requires org.apache.logging.log4j;
    requires retrofit2;
    requires okhttp3;
    requires retrofit2.converter.moshi;
    requires com.squareup.moshi;
    requires retrofit2.adapter.rxjava3;

    requires utils;
    requires io.reactivex.rxjava3;


    exports org.cliente.config;
    exports org.cliente.config.common;

    exports org.cliente.dao.retrofit.produces;
    exports org.cliente.dao.retrofit.llamadas;
    exports org.cliente.dao;
    exports org.cliente.dao.impl;
    exports org.cliente.dao.common;

    exports org.cliente.domain.servicios.impl;
    exports org.cliente.domain.modelo;

    exports org.cliente.ui.getAllJavaFX.main to javafx.graphics;
    exports org.cliente.ui.getAllJavaFX.principal;
    exports org.cliente.ui.updPersona;
    exports org.cliente.ui.getUnaPersona;
    exports org.cliente.ui.deletePersona;

    opens org.cliente.config.common;
    opens org.cliente.config;

    opens org.cliente.dao.common;

    opens org.cliente.ui.getAllJavaFX.common;
    opens org.cliente.ui.getAllJavaFX.principal;
    opens org.cliente.ui.getUnaPersona;
    opens org.cliente.ui.getAllJavaFX.main;
    opens fxml;

}