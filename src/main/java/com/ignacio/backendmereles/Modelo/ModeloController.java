package com.ignacio.backendmereles.Modelo;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/modelos")
@CrossOrigin(origins = "*")
public class ModeloController {

    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Modelo> crearModelo(
            @RequestParam("descripcion") String descripcion,
            @RequestParam("peso") Double peso,
            @RequestParam("imagen") MultipartFile imagen) {
        try {
            Modelo nuevoModelo = modeloService.crearModelo(descripcion, peso, imagen);
            return ResponseEntity.ok(nuevoModelo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Modelo>> obtenerTodosLosModelos() {
        return ResponseEntity.ok(modeloService.obtenerTodosLosModelos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Modelo>> obtenerModelosActivos() {
        return ResponseEntity.ok(modeloService.obtenerModelosActivos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Modelo> actualizarModelo(@PathVariable Long id, @RequestParam("descripcion") String descripcion,
                                                   @RequestParam("peso") Double peso, @RequestParam(value = "imagen", required = false) MultipartFile imagen) throws IOException {
        Optional<Modelo> modeloActualizado = modeloService.actualizarModelo(id, descripcion, peso, imagen);
        return modeloActualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Modelo> desactivarModelo(@PathVariable Long id) {
        Optional<Modelo> modeloDesactivado = modeloService.desactivarModelo(id);
        return modeloDesactivado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
