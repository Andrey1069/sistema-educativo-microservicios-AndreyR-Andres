package com.sistema.educativo.usuarios.repository;

import com.sistema.educativo.usuarios.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocenteRepository extends JpaRepository<Docente, Long> {

    Docente findByCodigo(String codigo);
}