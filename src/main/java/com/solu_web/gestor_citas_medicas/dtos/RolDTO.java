package com.solu_web.gestor_citas_medicas.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolDTO {
    private Integer idRol;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede contener mas de 50 caracteres")
    private String nombre;
    @Size(max = 150,message = "La descripcion no puede contener mas de 150 caracteres")
    private String descripcion;
}
