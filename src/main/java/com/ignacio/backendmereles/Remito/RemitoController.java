package com.ignacio.backendmereles.Remito;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/remitos")
public class RemitoController {

    private final RemitoService remitoService;

    public RemitoController(RemitoService remitoService) {
        this.remitoService = remitoService;
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Remito>> obtenerRemitosActivos() {
        return ResponseEntity.ok(remitoService.obtenerRemitosActivos());
    }

    @GetMapping("/locales")
    public ResponseEntity<List<Remito>> obtenerRemitosLocales() {
        return ResponseEntity.ok(remitoService.obtenerRemitosActivosNoEnviados());
    }

    @PostMapping("/generar")
    public ResponseEntity<Remito> generarRemito(@RequestBody Remito remito) {
        Remito nuevoRemito = remitoService.crearRemito(remito);
        return ResponseEntity.ok(nuevoRemito);
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Remito> desactivarRemito(@PathVariable Long id) {
        Optional<Remito> remitoDesactivado = remitoService.desactivarRemito(id);
        return remitoDesactivado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/enviar")
    public ResponseEntity<Remito> enviarRemitoId(@PathVariable Long id) {
        Optional<Remito> remitoEnviado = remitoService.enviarRemito(id);
        return remitoEnviado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/pesar")
    public ResponseEntity<Remito> pesarRemitoId(@PathVariable Long id) {
        Optional<Remito> remitoEnviado = remitoService.pesarRemito(id);
        return remitoEnviado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/egresar")
    public ResponseEntity<Remito> egresarRemitoId(@PathVariable Long id) {
        Optional<Remito> remitoEnviado = remitoService.egresarRemito(id);
        return remitoEnviado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/actualizar")
    public ResponseEntity<Remito> actualizarRemito(@PathVariable Long id, @RequestBody RemitoUpdateRequest remitoUpdateRequest) {
        Optional<Remito> remitoActualizado = remitoService.actualizarRemito(id, remitoUpdateRequest);
        return remitoActualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
