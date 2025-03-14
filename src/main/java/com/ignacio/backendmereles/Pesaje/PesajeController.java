package com.ignacio.backendmereles.Pesaje;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pesajes")
@CrossOrigin(origins = "*")
public class PesajeController {

    private final PesajeService pesajeService;

    public PesajeController(PesajeService pesajeService) {
        this.pesajeService = pesajeService;
    }

    @GetMapping
    public ResponseEntity<List<Pesaje>> obtenerTodos() {
        return ResponseEntity.ok(pesajeService.obtenerTodos());
    }

    @GetMapping("/no-pesados")
    public ResponseEntity<List<Pesaje>> obtenerNoPesados() {
        return ResponseEntity.ok(pesajeService.obtenerNoPesados());
    }

    @GetMapping("/pesados-no-egresados")
    public ResponseEntity<List<Pesaje>> obtenerPesadosNoEgresados() {
        return ResponseEntity.ok(pesajeService.obtenerPesadosNoEgresados());
    }

    @PostMapping("/crear")
    public ResponseEntity<Pesaje> crearPesaje(@RequestBody Pesaje pesaje) {
        Pesaje nuevoPesaje = pesajeService.crearPesaje(pesaje);
        return ResponseEntity.ok(nuevoPesaje);
    }

    @PostMapping("/crearDesdeRemito/{remitoId}")
    public ResponseEntity<Pesaje> crearPesajeDesdeRemito(@PathVariable Long remitoId) {
        Pesaje nuevoPesaje = pesajeService.crearPesajeDesdeRemito(remitoId);
        return ResponseEntity.ok(nuevoPesaje);
    }

    @PatchMapping("/{id}/pesar")
    public ResponseEntity<Pesaje> pesarRemito(@PathVariable Long id) {
        Optional<Pesaje> pesajePesado = pesajeService.pesarRemito(id);
        return pesajePesado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/egresar")
    public ResponseEntity<Pesaje> egresarRemito(@PathVariable Long id) {
        Optional<Pesaje> pesajeEgresado = pesajeService.egresarRemito(id);
        return pesajeEgresado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
