package com.adivina.jakarta.common;

public class JakartaConstants {


    private JakartaConstants() {
    }
    //LISTENER
    public static final String TEMPLATE_ENGINE_ATTR = "thymeleaf.TemplateEngineInstance";

    public static final String WEB_INF_TEMPLATES = "/WEB-INF/templates/";

    public static final String HTML = ".html";

    // SERVLET
    public static final String JUEGO_SERVLET = "JuegoServlet";

    public static final String JUEGO = "/juego";

    // TEMPLATES

    public static final String TEMPLATE_JUEGO = "juego";
    public static final String TEMPLATE_PERDISTE = "perdiste";
    public static final String TEMPLATE_GANASTE = "ganaste";
    public static final String ERROR = "error";


    // VARIABLES
    public static final String VIDAS = "vidas";

    public static final String NUM_RANDOM = "numRandom";
    public static final String NUMBER = "number";
    public static final String LISTA = "lista";
    public static final String HELP = "help";

    public static final String NUM_ERROR = "numError";



    // LIMITES
    public static final int BOUND_NUMBER = 100;
    public static final int ORIGIN_NUMBER = 1;
    public static final int ORIGIN_VIDAS = 10;
    public static final int BOUND_VIDAS = 1;

    // MENSAJES
    public static final String MENSAJE_HELP_MAS_BAJO = "Prueba un número más bajo";
    public static final String MENSAJE_HELP_MAS_ALTO = "Prueba un número más alto";
}
