module cliente {
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

    exports org.cliente.ui.getAllJavaSimpleFX.main to javafx.graphics;
    exports org.cliente.ui.getAllJavaSimpleFX.principal;
    exports org.cliente.ui.updPlayer;
    exports org.cliente.ui.getJugadoresPorEquipo;
    exports org.cliente.ui.getEquipo;
    exports org.cliente.ui.getAllTodos;
    exports org.cliente.ui.createPlayer;
    exports org.cliente.ui.createTeam;
    exports org.cliente.ui.deleteTeam;
    exports org.cliente.ui.deletePlayer;

    opens org.cliente.config.common;
    opens org.cliente.config;

    opens org.cliente.dao.common;

    opens org.cliente.ui.getAllJavaSimpleFX.common;
    opens org.cliente.ui.getAllJavaSimpleFX.principal;
    opens org.cliente.ui.getJugadoresPorEquipo;
    opens org.cliente.ui.getAllJavaSimpleFX.main;
    opens fxml;
    exports org.cliente.domain.servicios;

}