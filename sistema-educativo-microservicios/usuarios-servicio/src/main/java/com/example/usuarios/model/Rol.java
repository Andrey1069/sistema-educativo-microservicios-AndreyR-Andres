package com.sistema.educativo.usuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private NombreRol nombre;

    public enum NombreRol {
        ROLE_ESTUDIANTE,
        ROLE_DOCENTE,
        ROLE_ADMIN
    }
}