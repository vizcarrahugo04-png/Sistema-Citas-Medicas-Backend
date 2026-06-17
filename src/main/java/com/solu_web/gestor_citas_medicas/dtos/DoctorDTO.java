package com.solu_web.gestor_citas_medicas.dtos;

import jakarta.validation.constraints.Email;
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
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private Integer idDoctor;
    @NotBlank(message = "Los nombres es obligatorio")
    @Size(min = 1, max = 70,message = "Los nombres no deben exceder los 70 caracteres")
    private String nombres;
    @NotBlank(message = "Los apellidos es obligatorio")
    @Size(min = 1, max = 70,message = "Los apellidos no deben exceder los 70 caracteres")
    private String apellidos;
    @NotBlank(message = "El cmp es obligatorio")
    @Size(min = 8, max = 9,message = "El cmp no debe exceder los 9 caracteres")
    private String cmp;
    @NotBlank(message = "El telefono es obligatorio")
    @Size(min = 9, max = 9,message = "El telefono no debe exceder los 9 caracteres")
    private String telefono;
    @NotBlank(message = "El correo profesional es obligatorio")
    @Email(message = "El correo profesional debe ser valido")
    private String correoProfesional;
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
    @NotNull(message = "El idUsuario es obligatorio")
    @Positive(message = "El idUsuario debe ser mayor a 0")
    private Integer idUsuario;
    @NotNull(message = "El idEspecialidad es obligatorio")
    @Positive(message = "El idEspecialidad debe ser mayor a 0")
    private Integer idEspecialidad;
}
