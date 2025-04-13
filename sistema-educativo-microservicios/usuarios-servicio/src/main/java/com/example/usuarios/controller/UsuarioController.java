package com.sistema.educativo.usuarios.controller;

import com.sistema.educativo.usuarios.model.Docente;
import com.sistema.educativo.usuarios.model.Estudiante;
import com.sistema.educativo.usuarios.model.Usuario;
import com.sistema.educativo.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE') or hasRole('ESTUDIANTE')")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findUsuarioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estudiantes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
    public ResponseEntity<List<Estudiante>> getAllEstudiantes() {
        List<Estudiante> estudiantes = usuarioService.findAllEstudiantes();
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/estudiantes/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE') or hasRole('ESTUDIANTE')")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Long id) {
        return usuarioService.findEstudianteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/docentes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE') or hasRole('ESTUDIANTE')")
    public ResponseEntity<List<Docente>> getAllDocentes() {
        List<Docente> docentes = usuarioService.findAllDocentes();
        return ResponseEntity.ok(docentes);
    }

    @GetMapping("/docentes/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE') or hasRole('ESTUDIANTE')")
    public ResponseEntity<Docente> getDocenteById(@PathVariable Long id) {
        return usuarioService.findDocenteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}