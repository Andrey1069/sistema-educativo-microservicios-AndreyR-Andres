package com.sistema.educativo.usuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "docentes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Docente {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    private Usuario usuario;

    private String especialidad;

    private String departamento;

    private String codigo;
}