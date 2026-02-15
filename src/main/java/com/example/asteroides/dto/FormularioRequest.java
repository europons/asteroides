package com.example.asteroides.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * La fecha que el usuario introduce en el formulario de búsqueda.
 */
@Data
public class FormularioRequest {

    /**
     * Fecha introducida por el usuario (YYYY-MM-DD).
     */
    @NotNull(message = "Selecciona una fecha.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha;

}
