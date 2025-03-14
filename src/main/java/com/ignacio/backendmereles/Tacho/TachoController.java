package com.ignacio.backendmereles.Tacho;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tachos")
@CrossOrigin(origins = "*")
public class TachoController {

    private final TachoService tachoService;
    private final Sinks.Many<List<Tacho>> sink;

    public TachoController(TachoService tachoService) {
        this.tachoService = tachoService;
        this.sink = Sinks.many().replay().latest();
    }

    @GetMapping("/stream")
    public Flux<ServerSentEvent<List<Tacho>>> streamTachos() {
        return sink.asFlux()
                .map(tachos -> ServerSentEvent.builder(tachos).build())
                .startWith(ServerSentEvent.builder(tachoService.obtenerTachosActivos()).build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Tacho> crearTacho(
            @RequestParam("descripcion") String descripcion,
            @RequestParam("peso") Double peso,
            @RequestParam("imagen") MultipartFile imagen) {
        try {
            Tacho nuevoTacho = tachoService.crearTacho(descripcion, peso, imagen);
            sink.tryEmitNext(tachoService.obtenerTachosActivos());
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
        sink.tryEmitNext(tachoService.obtenerTachosActivos());
        return tachoActualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Tacho> desactivarTacho(@PathVariable Long id) {
        Optional<Tacho> tachoDesactivado = tachoService.desactivarTacho(id);
        sink.tryEmitNext(tachoService.obtenerTachosActivos());
        return tachoDesactivado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
