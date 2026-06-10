package com.solu_web.gestor_citas_medicas.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EspecialidadDTO {
    private Integer idEspecialidad;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max= 70, message = "El nombre no puede ser mayor a 70 caracteres")
    private String nombre;
    @Size(max = 100, message = "La descripcion no puede ser mayor a 100 caracteres")
    private String descripcion;
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}
