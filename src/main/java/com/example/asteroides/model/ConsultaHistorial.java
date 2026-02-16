package com.example.asteroides.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Guarda un resumen de cada consulta hecha por el usuario.
 */
@Data
@AllArgsConstructor
public class ConsultaHistorial {
    /**
     * Fecha buscada por el usuario.
     */
    private String fecha;

    /**
     * Cantidad de asteroides obtenidos en esa búsqueda.
     */
    private int asteroidesEncontrados;
}