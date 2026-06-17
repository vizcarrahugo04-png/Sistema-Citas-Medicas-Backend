package com.solu_web.gestor_citas_medicas.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistorialCitaDTO {

    private Integer idHistorial;

    @NotNull(message = "La fecha del cambio es obligatoria")
    private LocalDateTime fechaCambio;

    @NotBlank(message = "El estado anterior es obligatorio")
    @Size(max = 30, message = "El estado anterior no puede superar los 30 caracteres")
    private String estadoAnterior;

    @NotBlank(message = "El estado nuevo es obligatorio")
    @Size(max = 30, message = "El estado nuevo no puede superar los 30 caracteres")
    private String estadoNuevo;

    @Size(max = 250, message = "La observación no puede superar los 250 caracteres")
    private String observacion;

    @NotNull(message = "La cita es obligatoria")
    @Positive(message = "El id de la cita debe ser mayor que 0")
    private Integer idCita;
}