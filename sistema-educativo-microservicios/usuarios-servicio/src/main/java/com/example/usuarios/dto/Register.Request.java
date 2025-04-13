package com.sistema.educativo.usuarios.dto;

import com.sistema.educativo.usuarios.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    @Email
    private String email;

    private Set<String> roles;

    private Usuario.TipoUsuario tipoUsuario;

    // Campos adicionales para estudiante
    private String carrera;
    private String semestre;
    private String codigoEstudiante;

    // Campos adicionales para docente
    private String especialidad;
    private String departamento;
    private String codigoDocente;
}