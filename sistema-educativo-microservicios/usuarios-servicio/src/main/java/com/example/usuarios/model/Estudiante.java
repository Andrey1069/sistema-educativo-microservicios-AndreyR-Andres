package com.sistema.educativo.usuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    private Usuario usuario;

    private String carrera;

    private String semestre;

    private String codigo;
}