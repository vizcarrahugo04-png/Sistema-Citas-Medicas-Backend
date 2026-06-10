package com.solu_web.gestor_citas_medicas.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDTO {
    private Integer idPaciente;
    @NotBlank(message = "Los nombres es obligatorios")
    private String nombres;
    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;
    @NotBlank(message = "El dni es obligatorio")
    @Size(min = 8,max = 8,message = "El dni debe tener 8 caracteres")
    private String dni;
    @NotBlank(message="El numero es obligatorio")
    @Size(min = 9,max = 9,message = "El numero debe tener 9 caracteres")
    private String telefono;
    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fechaNacimiento;
    @NotBlank(message = "El sexo es obligatorio")
    private String sexo;
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
    @NotNull(message = "El id del usuario es obligatorio")
    @Positive(message = "El id del usuario debe ser mayor a 0")
    private Integer idUsuario;
}
