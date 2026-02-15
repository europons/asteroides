package com.example.asteroides.dto;

import com.example.asteroides.model.Asteroide;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AsteroideResponse {

    /**
     * Número total de asteroides devueltos para ese día.
     */
    private int element_count;


    private Map<String, List<Asteroide>> near_earth_objects;
}
