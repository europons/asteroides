package com.example.asteroides.dto;

import com.example.asteroides.model.Asteroide;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Respuesta principal que devuelve la NASA con los asteroides del día.
 */
@Data
public class AsteroideResponse {

    /**
     * Número total de asteroides devueltos para ese día.
     */
    private int element_count;

    /**
     * Asteroides agrupados por fecha (clave: fecha, valor: lista de asteroides).
     */
    private Map<String, List<Asteroide>> near_earth_objects;
}
