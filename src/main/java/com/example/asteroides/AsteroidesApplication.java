package com.example.asteroides;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicación.
 *
 * <p>Desde aquí se inicia todo el proyecto Spring Boot.</p>
 */
@SpringBootApplication
public class AsteroidesApplication {

	/**
	 * Arranca la aplicación web.
	 *
	 * @param args argumentos opcionales al iniciar
	 */
	public static void main(String[] args) {
		SpringApplication.run(AsteroidesApplication.class, args);
	}

}
