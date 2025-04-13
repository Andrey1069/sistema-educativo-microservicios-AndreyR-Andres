package com.sistema.educativo.usuarios.controller;

import com.sistema.educativo.usuarios.dto.JwtResponse;
import com.sistema.educativo.usuarios.dto.LoginRequest;
import com.sistema.educativo.usuarios.dto.MessageResponse;
import com.sistema.educativo.usuarios.dto.RegisterRequest;
import com.sistema.educativo.usuarios.model.Docente;
import com.sistema.educativo.usuarios.model.Estudiante;
import com.sistema.educativo.usuarios.model.Usuario;
import com.sistema.educativo.usuarios.security.UserDetailsImpl;
import com.sistema.educativo.usuarios.security.jwt.JwtUtils;
import com.sistema.educativo.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (usuarioService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: ¡El nombre de usuario ya está en uso!"));
        }

        if (usuarioService.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: ¡El email ya está en uso!"));
        }

        // Crear nueva cuenta de usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(registerRequest.getUsername());
        usuario.setPassword(registerRequest.getPassword());
        usuario.setNombre(registerRequest.getNombre());
        usuario.setApellido(registerRequest.getApellido());
        usuario.setEmail(registerRequest.getEmail());

        Set<String> strRoles = registerRequest.getRoles();
        String tipoUsuario = null;

        if (registerRequest.getTipoUsuario() != null) {
            tipoUsuario = registerRequest.getTipoUsuario().name();
        }

        usuario = usuarioService.registerUsuario(usuario, strRoles, tipoUsuario);

        // Registrar datos adicionales según el tipo de usuario
        if (tipoUsuario != null) {
            if (tipoUsuario.equals(Usuario.TipoUsuario.ESTUDIANTE.name())) {
                Estudiante estudiante = usuarioService.registrarEstudiante(
                        usuario,
                        registerRequest.getCarrera(),
                        registerRequest.getSemestre(),
                        registerRequest.getCodigoEstudiante());
            } else if (tipoUsuario.equals(Usuario.TipoUsuario.DOCENTE.name())) {
                Docente docente = usuarioService.registrarDocente(
                        usuario,
                        registerRequest.getEspecialidad(),
                        registerRequest.getDepartamento(),
                        registerRequest.getCodigoDocente());
            }
        }

        return ResponseEntity.ok(new MessageResponse("¡Usuario registrado exitosamente!"));
    }
}