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
@Table(name="consultorios")
public class Consultorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConsultorio;
    @Column(nullable = false,length = 20)
    private String numero;
    @Column(nullable = false, length = 70)
    private String ubicacion;
    @Column(nullable = false)
    private Integer piso;
    @Column(nullable = false)
    private Boolean estado;
}
