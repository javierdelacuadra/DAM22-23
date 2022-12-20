module ServidorRest {
    requires jakarta.jakartaee.web.api;
    requires com.zaxxer.hikari;
    requires java.sql;
    requires org.yaml.snakeyaml;
    requires lombok;
    requires domain;
    requires io.vavr;
    requires jakarta.mail;
    requires spring.jdbc;
    requires spring.tx;
}