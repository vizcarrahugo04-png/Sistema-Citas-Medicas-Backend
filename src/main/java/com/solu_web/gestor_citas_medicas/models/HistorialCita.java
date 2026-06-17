package com.solu_web.gestor_citas_medicas.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_citas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistorialCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHistorial;

    @Column(nullable = false)
    private LocalDateTime fechaCambio;

    @Column(nullable = false, length = 30)
    private String estadoAnterior;

    @Column(nullable = false, length = 30)
    private String estadoNuevo;

    @Column(length = 250)
    private String observacion;

    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private CitaMedica citaMedica;
}