package com.solu_web.gestor_citas_medicas.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "citas_medicas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CitaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCita;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false, length = 200)
    private String motivo;

    @Column(nullable = false, length = 30)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_doctor", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "id_horario", nullable = false)
    private HorarioDoctor horarioDoctor;

    @ManyToOne
    @JoinColumn(name = "id_consultorio", nullable = false)
    private Consultorio consultorio;
}