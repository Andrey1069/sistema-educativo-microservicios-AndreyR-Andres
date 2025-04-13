package com.sistema.educativo.usuarios.repository;

import com.sistema.educativo.usuarios.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Estudiante findByCodigo(String codigo);
}