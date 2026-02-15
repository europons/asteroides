package com.example.asteroides.model;

import lombok.Data;

import java.util.Map;

@Data
public class DatosAsteroide {
    private String close_approach_date_full;

    // relative_velocity.kilometers_per_hour viene como String en el JSON
    private Map<String, String> relative_velocity;

    // miss_distance.kilometers viene como String en el JSON
    private Map<String, String> miss_distance;
}
