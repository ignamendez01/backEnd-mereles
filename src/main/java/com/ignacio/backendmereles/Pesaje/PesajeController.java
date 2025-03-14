package com.ignacio.backendmereles.Pesaje;

import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pesajes")
@CrossOrigin(origins = "*")
public class PesajeController {

    private final PesajeService pesajeService;
    private final Sinks.Many<List<Pesaje>> sink;

    public PesajeController(PesajeService pesajeService) {
        this.pesajeService = pesajeService;
        this.sink = Sinks.many().replay().latest();
    }

    @GetMapping("/stream")
    public Flux<ServerSentEvent<List<Pesaje>>> obtenerRemitosActivos() {
        return sink.asFlux()
                .map(remitos -> ServerSentEvent.builder(remitos).build())
                .startWith(ServerSentEvent.builder(pesajeService.obtenerTodos()).build());
    }

    @GetMapping("/no-pesados")
    public Flux<ServerSentEvent<List<Pesaje>>> obtenerNoPesados() {
        return sink.asFlux()
                .map(remitosNoPesados -> ServerSentEvent.builder(remitosNoPesados).build())
                .startWith(ServerSentEvent.builder(pesajeService.obtenerNoPesados()).build());
    }

    @GetMapping("/pesados-no-egresados")
    public Flux<ServerSentEvent<List<Pesaje>>> obtenerPesadosNoEgresados() {
        return sink.asFlux()
                .map(remitosNoEgresados -> ServerSentEvent.builder(remitosNoEgresados).build())
                .startWith(ServerSentEvent.builder(pesajeService.obtenerPesadosNoEgresados()).build());
    }

    @GetMapping
    public ResponseEntity<List<Pesaje>> obtenerTodos() {
        return ResponseEntity.ok(pesajeService.obtenerTodos());
    }

    @PostMapping("/crear")
    public ResponseEntity<Pesaje> crearPesaje(@RequestBody Pesaje pesaje) {
        Pesaje nuevoPesaje = pesajeService.crearPesaje(pesaje);
        sink.tryEmitNext(pesajeService.obtenerTodos());
        sink.tryEmitNext(pesajeService.obtenerNoPesados());
        return ResponseEntity.ok(nuevoPesaje);
    }

    @PostMapping("/crearDesdeRemito/{remitoId}")
    public ResponseEntity<Pesaje> crearPesajeDesdeRemito(@PathVariable Long remitoId) {
        Pesaje nuevoPesaje = pesajeService.crearPesajeDesdeRemito(remitoId);
        sink.tryEmitNext(pesajeService.obtenerTodos());
        sink.tryEmitNext(pesajeService.obtenerNoPesados());
        return ResponseEntity.ok(nuevoPesaje);
    }

    @PatchMapping("/{id}/pesar")
    public ResponseEntity<Pesaje> pesarRemito(@PathVariable Long id) {
        Optional<Pesaje> pesajePesado = pesajeService.pesarRemito(id);
        sink.tryEmitNext(pesajeService.obtenerTodos());
        sink.tryEmitNext(pesajeService.obtenerNoPesados());
        sink.tryEmitNext(pesajeService.obtenerPesadosNoEgresados());
        return pesajePesado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/egresar")
    public ResponseEntity<Pesaje> egresarRemito(@PathVariable Long id) {
        Optional<Pesaje> pesajeEgresado = pesajeService.egresarRemito(id);
        sink.tryEmitNext(pesajeService.obtenerTodos());
        sink.tryEmitNext(pesajeService.obtenerPesadosNoEgresados());
        return pesajeEgresado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
