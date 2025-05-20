package com.ignacio.backendmereles;

import com.ignacio.backendmereles.Usuario.Permiso;
import com.ignacio.backendmereles.Usuario.RegistroRequest;
import com.ignacio.backendmereles.Usuario.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioService usuarioService;  // O el repository directamente

    public DataLoader(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Comprobamos si el usuario ya existe (por si no queremos crear duplicados)
        if (usuarioService.obtenerUsuarioPorNombre("dios").isEmpty()) {
            RegistroRequest registroRequest = new RegistroRequest();
            registroRequest.setNombre("Administrador General");
            registroRequest.setUsuario("dios");
            registroRequest.setPassword("dios123");
            registroRequest.setPermiso(Permiso.ADMIN);
            // Cargamos el usuario
            usuarioService.registrarUsuario(registroRequest);
            System.out.println("Usuario administrador creado correctamente.");
        } else {
            System.out.println("El usuario administrador ya existe.");
        }
    }
}

