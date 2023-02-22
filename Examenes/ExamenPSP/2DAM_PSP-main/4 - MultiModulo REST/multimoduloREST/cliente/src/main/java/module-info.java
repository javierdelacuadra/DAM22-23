module Newspapers {


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


    exports dao.retrofit.produces;
    exports dao.retrofit.llamadas;
    exports ui.main to javafx.graphics;
    exports ui.screens.newspapers.listnews;
    exports ui.screens.readers.listreaders;
    exports ui.screens.readers.updreaders;
    exports ui.screens.newspapers.updnews;
    exports dao;
    exports dao.impl;
    exports dao.common;
    exports domain.serivces;
    exports domain.serivces.impl;
    exports config;
    exports config.common;

    opens config.common;
    opens ui.screens.common;
    opens ui.screens.principal;
    opens ui.screens.newspapers.listnews;
    opens ui.screens.readers.listreaders;
    opens ui.screens.readers.updreaders;
    opens ui.screens.newspapers.updnews;
    opens ui.main;
    opens dao.common;
    opens config;
    opens fxml;


}
