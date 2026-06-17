package com.solu_web.gestor_citas_medicas.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CitaMedicaDTO {

    private Integer idCita;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 200, message = "El motivo no puede superar los 200 caracteres")
    private String motivo;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 30, message = "El estado no puede superar los 30 caracteres")
    private String estado;

    @NotNull(message = "El paciente es obligatorio")
    @Positive(message = "El id del paciente debe ser mayor que 0")
    private Integer idPaciente;

    @NotNull(message = "El doctor es obligatorio")
    @Positive(message = "El id del doctor debe ser mayor que 0")
    private Integer idDoctor;

    @NotNull(message = "El horario es obligatorio")
    @Positive(message = "El id del horario debe ser mayor que 0")
    private Integer idHorario;

    @NotNull(message = "El consultorio es obligatorio")
    @Positive(message = "El id del consultorio debe ser mayor que 0")
    private Integer idConsultorio;
}
