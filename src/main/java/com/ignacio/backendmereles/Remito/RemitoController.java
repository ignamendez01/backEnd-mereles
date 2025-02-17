package com.ignacio.backendmereles.Remito;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
public class RemitoController {

    private final RemitoService remitoService;

    public RemitoController(RemitoService remitoService) {
        this.remitoService = remitoService;
    }

    @PostMapping("/generar")
    public ResponseEntity<Remito> generarRemito(@RequestBody Remito remito) {
        Remito nuevoRemito = remitoService.crearRemito(remito);
        return ResponseEntity.ok(nuevoRemito);
    }

    @PostMapping("/enviar")
    public ResponseEntity<Remito> enviarRemito(@RequestBody Remito remito) {
        Remito nuevoRemito = remitoService.enviarRemito(remito);
        return ResponseEntity.ok(nuevoRemito);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Remito>> obtenerRemitosActivos() {
        return ResponseEntity.ok(remitoService.obtenerRemitosActivos());
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

    @PutMapping("/{id}/actualizar")
    public ResponseEntity<Remito> actualizarRemito(@PathVariable Long id, @RequestBody RemitoUpdateRequest remitoUpdateRequest) {
        Optional<Remito> remitoActualizado = remitoService.actualizarRemito(id, remitoUpdateRequest);
        return remitoActualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
