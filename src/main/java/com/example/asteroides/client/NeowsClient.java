package com.example.asteroides.client;

import com.example.asteroides.dto.AsteroideResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

/**
 * Cliente sencillo para consultar la API de asteroides de la NASA.
 */
@Component
public class NeowsClient {

    /**
     * Dirección base de la API de la NASA.
     */
    private static final String URL_BASE = "https://api.nasa.gov/neo/rest/v1/feed";

    @Value("${nasa.api.key}")
    private String apiKey;

    /**
     * Cliente HTTP usado para hacer peticiones.
     */
    private final RestClient restClient = RestClient.create();

    /**
     * Pide los asteroides de una fecha concreta.
     *
     * @param fecha día a consultar
     * @return respuesta completa de la NASA para ese día
     */
    public AsteroideResponse obtenerAsteroides(LocalDate fecha) {
        return restClient.get()
                .uri(URL_BASE + "?start_date={start}&end_date={end}&api_key={key}",
                        fecha, fecha, apiKey)
                .retrieve()
                .body(AsteroideResponse.class);
    }
}

