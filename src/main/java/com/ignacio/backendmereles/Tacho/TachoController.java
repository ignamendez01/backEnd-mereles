package com.ignacio.backendmereles.Tacho;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tachos")
public class TachoController {

    private final TachoService tachoService;

    public TachoController(TachoService tachoService) {
        this.tachoService = tachoService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Tacho> crearTacho(
            @RequestParam("descripcion") String descripcion,
            @RequestParam("peso") Double peso,
            @RequestParam("imagen") MultipartFile imagen) {
        try {
            Tacho nuevoTacho = tachoService.crearTacho(descripcion, peso, imagen);
            return ResponseEntity.ok(nuevoTacho);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Tacho>> obtenerTodosLosTachos() {
        return ResponseEntity.ok(tachoService.obtenerTodosLosTachos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Tacho>> obtenerTachosActivos() {
        return ResponseEntity.ok(tachoService.obtenerTachosActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tacho> obtenerTacho(@PathVariable Long id) {
        return ResponseEntity.ok(tachoService.obtenerTacho(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tacho> actualizarTacho(@PathVariable Long id, @RequestParam("descripcion") String descripcion,
                                                 @RequestParam("peso") Double peso, @RequestParam(value = "imagen", required = false) MultipartFile imagen) throws IOException {
        Optional<Tacho> tachoActualizado = tachoService.actualizarTacho(id, descripcion, peso, imagen);
        return tachoActualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Tacho> desactivarTacho(@PathVariable Long id) {
        Optional<Tacho> tachoDesactivado = tachoService.desactivarTacho(id);
        return tachoDesactivado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
