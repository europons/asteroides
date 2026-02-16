package com.example.asteroides.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConsultaHistorial {
    private String fecha;
    private int asteroidesEncontrados;
}