package com.ignacio.backendmereles.Remito;
import com.ignacio.backendmereles.Colada.Colada;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RemitoService {

    private final RemitoRepository remitoRepository;

    public RemitoService(RemitoRepository remitoRepository) {
        this.remitoRepository = remitoRepository;
    }

    public Remito crearRemito(Remito remito) {
        // Si el remito tiene coladas, aseguramos que cada una esté asociada al remito
        if (remito.getColadas() != null) {
            for (Colada colada : remito.getColadas()) {
                colada.setRemito(remito);  // Asocia cada colada al remito
            }
        }

        remito.setActivo(true);
        remito.setEnviado(false);

        // Aquí ya tenemos las coladas asociadas al remito, por lo que solo lo guardamos
        return remitoRepository.save(remito);  // Guardará también las coladas debido al CascadeType.ALL
    }

    public Remito enviarRemito(Remito remito) {
        // Si el remito tiene coladas, aseguramos que cada una esté asociada al remito
        if (remito.getColadas() != null) {
            for (Colada colada : remito.getColadas()) {
                colada.setRemito(remito);  // Asocia cada colada al remito
            }
        }

        remito.setActivo(true);
        remito.setEnviado(true);

        // Aquí ya tenemos las coladas asociadas al remito, por lo que solo lo guardamos
        return remitoRepository.save(remito);  // Guardará también las coladas debido al CascadeType.ALL
    }

    public List<Remito> obtenerRemitosActivos() {
        return remitoRepository.findByActivoTrueOrderByIdAsc();
    }

    public Optional<Remito> desactivarRemito(Long id) {
        return remitoRepository.findById(id).map(remito -> {
            remito.setActivo(false);
            return remitoRepository.save(remito);
        });
    }

    public Optional<Remito> enviarRemito(Long id) {
        return remitoRepository.findById(id).map(remito -> {
            remito.setEnviado(true);
            return remitoRepository.save(remito);
        });
    }

    public Optional<Remito> actualizarRemito(Long id, RemitoUpdateRequest remitoUpdateDTO) {
        return remitoRepository.findById(id).map(remito -> {
            remito.setTachoId(remitoUpdateDTO.getTachoId());
            remito.setPesoTotal(remitoUpdateDTO.getPesoTotal());

            List<Colada> coladasActuales = remito.getColadas();
            List<Colada> coladasNuevas = remitoUpdateDTO.getColadas();

            coladasActuales.removeIf(coladaExistente ->
                    coladasNuevas.stream().noneMatch(coladaNueva -> coladaNueva.getId().equals(coladaExistente.getId()))
            );

            for (Colada nuevaColada : coladasNuevas) {
                boolean existe = false;

                for (Colada coladaExistente : coladasActuales) {
                    if (coladaExistente.getId().equals(nuevaColada.getId())) {
                        coladaExistente.setColada(nuevaColada.getColada());
                        coladaExistente.setFecha(nuevaColada.getFecha());
                        coladaExistente.setCantidad(nuevaColada.getCantidad());
                        coladaExistente.setImagen(nuevaColada.getImagen());
                        coladaExistente.setPeso(nuevaColada.getPeso());
                        coladaExistente.setModeloId(nuevaColada.getModeloId());
                        coladaExistente.setModeloId(nuevaColada.getModeloId());
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    nuevaColada.setRemito(remito);
                    coladasActuales.add(nuevaColada);
                }
            }

            return remitoRepository.save(remito);
        });
    }
}
