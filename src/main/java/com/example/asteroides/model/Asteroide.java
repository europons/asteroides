package com.example.asteroides.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Representa un asteroide con sus propiedades relevantes.
 */
@Data
public class Asteroide {

    /**
     * Identificador del asteroide.
     */
    private String id;

    /**
     * Nombre del asteroide.
     */
    private String name;

    /**
     * Indica si es potencialmente peligroso.
     */
    @JsonProperty("is_potentially_hazardous_asteroid")
    private boolean potentiallyHazardous;

    /**
     * Tamaño mínimo estimado (m).
     */
    private Map<String, Map<String, Double>> estimated_diameter;

    /**
     * Fecha y hora del acercamiento (texto).
     */
    private List<DatosAsteroide> close_approach_data;
}

