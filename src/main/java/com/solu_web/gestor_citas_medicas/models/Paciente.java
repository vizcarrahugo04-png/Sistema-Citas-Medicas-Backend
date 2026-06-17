package com.solu_web.gestor_citas_medicas.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Integer idPaciente;
    @Column(nullable = false, length = 100)
    private String nombres;
    @Column(nullable = false, length = 100)
    private String apellidos;
    @Column(nullable = false, length = 8)
    private String dni;
    @Column(nullable = false,unique = true, length = 9)
    private String telefono;
    @Column(nullable = false, length = 60)
    private String direccion;
    @Column(nullable = false)
    private LocalDate fechaNacimiento;
    @Column(nullable = false, length = 40)
    private String sexo;
    @Column(nullable = false)
    private Boolean estado;
    @OneToOne
    @JoinColumn(name = "id_usuario",nullable = false)
    private Usuario usuario;
}
