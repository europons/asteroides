package com.example.asteroides.model;

import lombok.Data;

@Data
public class AsteroideVista {
    private String id;

    private String name;

    private double diameterMaxMeters;

    private String missDistanceKm;

    private String velocityKmh;

    private boolean potentiallyHazardous;
}
