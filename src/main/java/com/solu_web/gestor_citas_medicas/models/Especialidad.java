package com.solu_web.gestor_citas_medicas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="especialidades")
public class Especialidad {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idEspecialidad;
    @Column(nullable = false, length = 70)
    private String nombre;
    @Column(length = 100)
    private String descripcion;
    @Column(nullable = false)
    private Boolean estado;
}
