package com.serverql.graphqlserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class GraphQlServerApplication {
	public static final String JAVA_AWT_HEADLESS = "java.awt.headless";
	public static final String FALSE = "false";

	public static final String BASE_APP_URI = "http://localhost:8080/graphiql";

	public static void main(String[] args) {
		System.setProperty(JAVA_AWT_HEADLESS, FALSE);
		SpringApplication.run(GraphQlServerApplication.class, args);
	}

	//Evento para hacer algo al cargar
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() throws URISyntaxException, IOException {
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			Desktop.getDesktop().browse(new URI(BASE_APP_URI));
		}
	}
}
