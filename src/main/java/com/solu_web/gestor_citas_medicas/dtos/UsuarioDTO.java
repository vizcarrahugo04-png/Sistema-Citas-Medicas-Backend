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
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer idUsuario;
    @NotBlank(message = "El username es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    private String username;
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ingresar un correo valido")
    @Size(max = 100, message = "El correo no puede exceder los 100 caracteres")
    private String correo;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 100, message = "La contraseña debe ser mayor a 6 y menor a 100 caracteres")
    private String password;
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
    @NotNull(message = "Debe seleccionar un rol")
    @Positive(message = "El ID del rol debe ser mayor a 0")
    private Integer idRol;
    
}
