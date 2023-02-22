module servidor {
    requires utils;

    requires lombok;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires com.zaxxer.hikari;
    requires spring.jdbc;
    requires spring.core;
    requires spring.tx;
    requires jakarta.jakartaee.web.api;
    requires jakarta.mail;
    requires thymeleaf;
    requires jjwt.api;
    requires io.vavr;

}