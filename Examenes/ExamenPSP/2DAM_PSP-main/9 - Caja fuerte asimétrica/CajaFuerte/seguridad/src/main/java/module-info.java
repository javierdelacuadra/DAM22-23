module seguridad {
    exports org.seguridad.impl;
    exports org.seguridad;

    requires com.google.common;
    requires lombok;
    requires org.apache.logging.log4j;
    requires org.bouncycastle.provider;

    requires org.bouncycastle.pkix;
    requires io.vavr;
    requires utils;
}