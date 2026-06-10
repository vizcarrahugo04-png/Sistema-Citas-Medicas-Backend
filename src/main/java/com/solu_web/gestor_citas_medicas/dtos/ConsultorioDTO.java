package com.solu_web.gestor_citas_medicas.dtos;

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
public class ConsultorioDTO {

    private Integer idConsultorio;
    @NotBlank(message = "El numero de consultorio es obligatorio")
    @Size(max = 20, message = "No puede exceder los 20 caracteres")
    private String numero;
    @NotBlank(message = "La ubicacion es obligatoria")
    @Size(max = 70, message = "No puede exceder los 70 caracteres")
    private String ubicacion;
    @NotNull(message = "El piso es obligatorio")
    @Positive(message = "El piso debe que ser mayor a 0")
    private Integer piso;
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}
