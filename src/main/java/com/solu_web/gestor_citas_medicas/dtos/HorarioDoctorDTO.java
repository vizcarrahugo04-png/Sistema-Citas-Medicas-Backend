package com.solu_web.gestor_citas_medicas.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HorarioDoctorDTO {

    private Integer idHorario;

    @NotBlank(message = "El día es obligatorio")
    @Size(max = 20, message = "El día no puede superar los 20 caracteres")
    private String dia;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @NotNull(message = "Los cupos disponibles son obligatorios")
    @Positive(message = "Los cupos deben ser mayores que 0")
    private Integer cuposDisponibles;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

    @NotNull(message = "El doctor es obligatorio")
    @Positive(message = "El id del doctor debe ser mayor que 0")
    private Integer idDoctor;
}
