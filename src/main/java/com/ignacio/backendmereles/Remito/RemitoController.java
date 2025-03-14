package com.ignacio.backendmereles.Remito;

import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/remitos")
@CrossOrigin(origins = "*")
public class RemitoController {

    private final RemitoService remitoService;
    private final Sinks.Many<List<Remito>> sink;

    public RemitoController(RemitoService remitoService) {
        this.remitoService = remitoService;
        this.sink = Sinks.many().replay().latest();
    }

    @GetMapping("/activos")
    public Flux<ServerSentEvent<List<Remito>>> obtenerRemitosActivos() {
        return sink.asFlux()
                .map(remitosActivos -> ServerSentEvent.builder(remitosActivos).build())
                .startWith(ServerSentEvent.builder(remitoService.obtenerRemitosActivos()).build());
    }

    @GetMapping("/locales")
    public Flux<ServerSentEvent<List<Remito>>> obtenerRemitosLocales() {
        return sink.asFlux()
                .map(remitosActivos -> ServerSentEvent.builder(remitosActivos).build())
                .startWith(ServerSentEvent.builder(remitoService.obtenerRemitosActivosNoEnviados()).build());
    }

    @PostMapping("/generar")
    public ResponseEntity<Remito> generarRemito(@RequestBody Remito remito) {
        Remito nuevoRemito = remitoService.crearRemito(remito);
        sink.tryEmitNext(remitoService.obtenerRemitosActivos());
        sink.tryEmitNext(remitoService.obtenerRemitosActivosNoEnviados());
        return ResponseEntity.ok(nuevoRemito);
    }

    @PostMapping("/enviar")
    public ResponseEntity<Remito> enviarRemito(@RequestBody Remito remito) {
        Remito nuevoRemito = remitoService.enviarRemito(remito);
        sink.tryEmitNext(remitoService.obtenerRemitosActivos());
        sink.tryEmitNext(remitoService.obtenerRemitosActivosNoEnviados());
        return ResponseEntity.ok(nuevoRemito);
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Remito> desactivarRemito(@PathVariable Long id) {
        Optional<Remito> remitoDesactivado = remitoService.desactivarRemito(id);
        sink.tryEmitNext(remitoService.obtenerRemitosActivos());
        sink.tryEmitNext(remitoService.obtenerRemitosActivosNoEnviados());
        return remitoDesactivado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/enviar")
    public ResponseEntity<Remito> enviarRemitoId(@PathVariable Long id) {
        Optional<Remito> remitoEnviado = remitoService.enviarRemito(id);
        sink.tryEmitNext(remitoService.obtenerRemitosActivos());
        sink.tryEmitNext(remitoService.obtenerRemitosActivosNoEnviados());
        return remitoEnviado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/pesar")
    public ResponseEntity<Remito> pesarRemitoId(@PathVariable Long id) {
        Optional<Remito> remitoEnviado = remitoService.pesarRemito(id);
        sink.tryEmitNext(remitoService.obtenerRemitosActivos());
        return remitoEnviado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/egresar")
    public ResponseEntity<Remito> egresarRemitoId(@PathVariable Long id) {
        Optional<Remito> remitoEnviado = remitoService.egresarRemito(id);
        sink.tryEmitNext(remitoService.obtenerRemitosActivos());
        return remitoEnviado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/actualizar")
    public ResponseEntity<Remito> actualizarRemito(@PathVariable Long id, @RequestBody RemitoUpdateRequest remitoUpdateRequest) {
        Optional<Remito> remitoActualizado = remitoService.actualizarRemito(id, remitoUpdateRequest);
        sink.tryEmitNext(remitoService.obtenerRemitosActivos());
        sink.tryEmitNext(remitoService.obtenerRemitosActivosNoEnviados());
        return remitoActualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
