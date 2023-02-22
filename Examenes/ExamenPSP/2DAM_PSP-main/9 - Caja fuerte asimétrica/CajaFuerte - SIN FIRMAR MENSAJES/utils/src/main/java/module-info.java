module utils {
    exports org.utils.domain.modelo;
    exports org.utils.common;
    requires lombok;
    opens org.utils.domain.modelo to com.squareup.moshi;
    opens org.utils.domain.modelo.dto to com.squareup.moshi;
    exports org.utils.domain.modelo.dto;

}