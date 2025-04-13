package com.sistema.educativo.usuarios.repository;

import com.sistema.educativo.usuarios.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(Rol.NombreRol nombre);
}