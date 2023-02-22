module utils {
    requires lombok;

    exports org.miutils.domain.modelo;

    opens org.miutils.domain.modelo;
    exports org.miutils.common;
}