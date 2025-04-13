package com.sistema.educativo.usuarios.service;

import com.sistema.educativo.usuarios.model.*;
import com.sistema.educativo.usuarios.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private PasswordEncoder encoder;

    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Transactional
    public Usuario registerUsuario(Usuario usuario, Set<String> strRoles, String tipoUsuario) {
        // Encriptar contraseña
        usuario.setPassword(encoder.encode(usuario.getPassword()));

        Set<Rol> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Rol userRole = rolRepository.findByNombre(Rol.NombreRol.ROLE_ESTUDIANTE)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Rol adminRole = rolRepository.findByNombre(Rol.NombreRol.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(adminRole);
                        break;
                    case "docente":
                        Rol modRole = rolRepository.findByNombre(Rol.NombreRol.ROLE_DOCENTE)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(modRole);
                        break;
                    default:
                        Rol userRole = rolRepository.findByNombre(Rol.NombreRol.ROLE_ESTUDIANTE)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(userRole);
                }
            });
        }

        usuario.setRoles(roles);

        if (tipoUsuario != null) {
            usuario.setTipoUsuario(Usuario.TipoUsuario.valueOf(tipoUsuario));
        } else {
            usuario.setTipoUsuario(Usuario.TipoUsuario.ESTUDIANTE);
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Estudiante registrarEstudiante(Usuario usuario, String carrera, String semestre, String codigo) {
        Estudiante estudiante = new Estudiante();
        estudiante.setUsuario(usuario);
        estudiante.setCarrera(carrera);
        estudiante.setSemestre(semestre);
        estudiante.setCodigo(codigo);

        return estudianteRepository.save(estudiante);
    }

    @Transactional
    public Docente registrarDocente(Usuario usuario, String especialidad, String departamento, String codigo) {
        Docente docente = new Docente();
        docente.setUsuario(usuario);
        docente.setEspecialidad(especialidad);
        docente.setDepartamento(departamento);
        docente.setCodigo(codigo);

        return docenteRepository.save(docente);
    }

    public List<Estudiante> findAllEstudiantes() {
        return estudianteRepository.findAll();
    }

    public Optional<Estudiante> findEstudianteById(Long id) {
        return estudianteRepository.findById(id);
    }

    public List<Docente> findAllDocentes() {
        return docenteRepository.findAll();
    }

    public Optional<Docente> findDocenteById(Long id) {
        return docenteRepository.findById(id);
    }
}