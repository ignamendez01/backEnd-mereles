package com.ignacio.backendmereles.Remito;
import com.ignacio.backendmereles.Coladas.ColadaRemito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RemitoService {

    private final RemitoRepository remitoRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public RemitoService(RemitoRepository remitoRepository) {
        this.remitoRepository = remitoRepository;
    }

    private void notificarCambioRemitos() {
        messagingTemplate.convertAndSend("/topic/remitos", "actualizar");
    }

    public List<Remito> obtenerTodos() {
        return remitoRepository.findAll();
    }

    public Remito crearRemito(Remito remito) {
        if (remito.getColadas() != null) {
            for (ColadaRemito coladaRemito : remito.getColadas()) {
                coladaRemito.setRemito(remito);
            }
        }

        remito.setActivo(true);
        remito.setEnviado(false);
        remito.setEstado("De alta");

        Remito guardado = remitoRepository.save(remito);
        notificarCambioRemitos();
        return guardado;
    }

    public List<Remito> obtenerRemitosActivos() {
        return remitoRepository.findByActivoTrueOrderByIdAsc();
    }

    public List<Remito> obtenerRemitosActivosNoEnviados() {
        return remitoRepository.findByActivoTrueAndEnviadoFalseOrderByIdAsc();
    }

    public Optional<Remito> desactivarRemito(Long id) {
        return remitoRepository.findById(id).map(remito -> {
            remito.setActivo(false);
            Remito actualizado = remitoRepository.save(remito);
            notificarCambioRemitos();
            return actualizado;
        });
    }

    public Optional<Remito> enviarRemito(Long id) {
        return remitoRepository.findById(id).map(remito -> {
            remito.setEnviado(true);
            remito.setEstado("Enviado, sin pesar");
            Remito actualizado = remitoRepository.save(remito);
            notificarCambioRemitos();
            return actualizado;
        });
    }

    public Optional<Remito> pesarRemito(Long id) {
        return remitoRepository.findById(id).map(remito -> {
            remito.setEstado("Enviado, pesado");
            Remito actualizado = remitoRepository.save(remito);
            notificarCambioRemitos();
            return actualizado;
        });
    }

    public Optional<Remito> egresarRemito(Long id) {
        return remitoRepository.findById(id).map(remito -> {
            remito.setEstado("Recibido");
            Remito actualizado = remitoRepository.save(remito);
            notificarCambioRemitos();
            return actualizado;
        });
    }

    public Optional<Remito> actualizarRemito(Long id, RemitoUpdateRequest remitoUpdateDTO) {
        return remitoRepository.findById(id).map(remito -> {
            remito.setTachoId(remitoUpdateDTO.getTachoId());
            remito.setPesoTotal(remitoUpdateDTO.getPesoTotal());
            remito.setTachoPeso(remitoUpdateDTO.getPesoTacho());

            List<ColadaRemito> coladasActuales = remito.getColadas();
            List<ColadaRemito> coladasNuevas = remitoUpdateDTO.getColadas();

            coladasActuales.removeIf(coladaExistente ->
                    coladasNuevas.stream().noneMatch(coladaNueva -> coladaNueva.getId().equals(coladaExistente.getId()))
            );

            for (ColadaRemito nuevaColadaRemito : coladasNuevas) {
                boolean existe = false;

                for (ColadaRemito coladaRemitoExistente : coladasActuales) {
                    if (coladaRemitoExistente.getId().equals(nuevaColadaRemito.getId())) {
                        coladaRemitoExistente.setColada(nuevaColadaRemito.getColada());
                        coladaRemitoExistente.setFecha(nuevaColadaRemito.getFecha());
                        coladaRemitoExistente.setCantidad(nuevaColadaRemito.getCantidad());
                        coladaRemitoExistente.setImagen(nuevaColadaRemito.getImagen());
                        coladaRemitoExistente.setPeso(nuevaColadaRemito.getPeso());
                        coladaRemitoExistente.setPesoTotal(nuevaColadaRemito.getPesoTotal());
                        coladaRemitoExistente.setModeloId(nuevaColadaRemito.getModeloId());
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    nuevaColadaRemito.setRemito(remito);
                    coladasActuales.add(nuevaColadaRemito);
                }
            }

            Remito actualizado = remitoRepository.save(remito);
            notificarCambioRemitos();
            return actualizado;
        });
    }
}
