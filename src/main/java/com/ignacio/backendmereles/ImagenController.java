package com.ignacio.backendmereles;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImagenController {

    @GetMapping("/imagenes/{imagen}")
    public ResponseEntity<Resource> getImagen(@PathVariable String imagen) {
        Path path = Paths.get("/app/imagenes/" + imagen);
        Resource resource = new FileSystemResource(path);

        if (resource.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)  // Asegúrate de ajustar el tipo de contenido si es necesario
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

