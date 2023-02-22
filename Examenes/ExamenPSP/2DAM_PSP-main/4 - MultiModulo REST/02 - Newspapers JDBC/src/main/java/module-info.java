module Newspapers {


    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;

    requires lombok;
    requires org.apache.logging.log4j;

    requires jakarta.inject;
    requires jakarta.cdi;
    requires io.vavr;
    requires java.sql;
    requires com.zaxxer.hikari;
    requires spring.jdbc;
    requires spring.core;
    requires spring.tx;

    exports common;
    exports ui.screens.login;
    exports ui.main to javafx.graphics;
    exports ui.screens.newspapers.listNews;
    exports ui.screens.articles.addArticle;
    exports ui.screens.articles.listArticles;
    exports ui.screens.newspapers.delNews;
    exports ui.screens.readers.listReaders;
    exports ui.screens.readers.delReaders;
    exports ui.screens.readarticles.addReadArt;
    exports ui.screens.readers.addReaders;
    exports ui.screens.readers.updReaders;
    exports ui.screens.newspapers.addNews;
    exports ui.screens.newspapers.updNews;
    exports ui.screens.suscriptions.changeSuscription;
    opens ui.screens.login;
    exports model;
    exports dao;
    exports dao.impl;
    exports dao.common;
    exports serivces.common;
    exports serivces;
    exports serivces.impl;
    exports config;
    exports config.common;


    opens ui.screens.common;
    opens ui.screens.principal;
    opens ui.screens.newspapers.listNews;
    opens ui.screens.newspapers.delNews;
    opens ui.screens.articles.addArticle;
    opens ui.screens.articles.listArticles;
    opens ui.screens.readers.listReaders;
    opens ui.screens.readers.delReaders;
    opens ui.screens.readarticles.addReadArt;
    opens ui.screens.readers.addReaders;
    opens ui.screens.readers.updReaders;
    opens ui.screens.newspapers.addNews;
    opens ui.screens.newspapers.updNews;
    opens ui.screens.suscriptions.changeSuscription;
    opens ui.main;
    opens dao.common;
    opens config;
    opens fxml;

//    exports ui;
//    opens domain.modelo to javafx.base;
//    exports ui.pantallas.principal;
//
//    opens ui.pantallas.principal to javafx.fxml;

}
