package com.ignacio.backendmereles.Usuario;

import com.ignacio.backendmereles.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    private void notificarCambioUsuarios() {
        messagingTemplate.convertAndSend("/topic/usuarios", "actualizar");
    }

    public ResponseEntity<?> registrarUsuario(RegistroRequest request) {
        if (usuarioRepository.findByUsuario(request.getUsuario()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El nombre de usuario ya está en uso.");
        }

        Usuario nuevo = new Usuario(
                request.getNombre(),
                request.getUsuario(),
                passwordEncoder.encode(request.getPassword()),
                request.getPermiso()
        );

        usuarioRepository.save(nuevo);
        notificarCambioUsuarios();
        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    public ResponseEntity<?> login(LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuario(request.getUsuario());

        if (usuarioOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), usuarioOpt.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(usuarioOpt.get().getUsuario(), usuarioOpt.get().getPermiso().toString());
        return ResponseEntity.ok(token);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> obtenerUsuarioPorNombre(String nombre) {
        return usuarioRepository.findByUsuario(nombre);
    }
}

