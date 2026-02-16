package com.example.asteroides.model;

import lombok.Data;

import java.util.Map;

/**
 * Datos de un acercamiento concreto del asteroide a la Tierra.
 */
@Data
public class DatosAsteroide {
    /**
     * Fecha y hora completas del acercamiento.
     */
    private String close_approach_date_full;

    /**
     * Bloque de velocidad relativa (en el JSON llega como texto).
     */
    private Map<String, String> relative_velocity;

    /**
     * Bloque de distancia mínima (en el JSON llega como texto).
     */
    private Map<String, String> miss_distance;
}
