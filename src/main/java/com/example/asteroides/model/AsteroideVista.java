package com.example.asteroides.model;

import lombok.Data;

/**
 * Modelo simplificado que se enseña en la vista HTML.
 */
@Data
public class AsteroideVista {
    /**
     * Identificador del asteroide.
     */
    private String id;

    /**
     * Nombre del asteroide.
     */
    private String name;

    /**
     * Diámetro máximo estimado en metros.
     */
    private double diameterMaxMeters;

    /**
     * Distancia mínima de paso en kilómetros.
     */
    private String missDistanceKm;

    /**
     * Velocidad estimada en kilómetros por hora.
     */
    private String velocityKmh;

    /**
     * Indica si se considera potencialmente peligroso.
     */
    private boolean potentiallyHazardous;
}
