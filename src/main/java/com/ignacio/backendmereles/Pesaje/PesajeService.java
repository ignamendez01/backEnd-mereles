package com.ignacio.backendmereles.Pesaje;

import com.ignacio.backendmereles.Coladas.ColadaPesaje;
import com.ignacio.backendmereles.Coladas.ColadaRemito;
import com.ignacio.backendmereles.Remito.Remito;
import com.ignacio.backendmereles.Remito.RemitoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PesajeService {

    private final PesajeRepository pesajeRepository;
    private final RemitoRepository remitoRepository;

    public PesajeService(PesajeRepository pesajeRepository, RemitoRepository remitoRepository) {
        this.pesajeRepository = pesajeRepository;
        this.remitoRepository = remitoRepository;
    }

    public List<Pesaje> obtenerNoPesados() {
        return pesajeRepository.findByPesadoFalse();
    }

    public List<Pesaje> obtenerPesadosNoEgresados() {
        return pesajeRepository.findByPesadoTrueAndEgresadoFalse();
    }

    public List<Pesaje> obtenerTodos() {
        return pesajeRepository.findAllByOrderByIdAsc();
    }

    public Optional<Pesaje> pesarRemito(Long id) {
        return pesajeRepository.findById(id).map(pesaje -> {
            pesaje.setPesado(true);
            return pesajeRepository.save(pesaje);
        });
    }

    public Optional<Pesaje> egresarRemito(Long id) {
        return pesajeRepository.findById(id).map(pesaje -> {
            pesaje.setEgresado(true);
            return pesajeRepository.save(pesaje);
        });
    }

    public Pesaje crearPesajeDesdeRemito(Long remitoId) {
        Remito remito = remitoRepository.findById(remitoId)
                .orElseThrow(() -> new RuntimeException("Remito no encontrado"));

        Pesaje pesaje = new Pesaje();
        pesaje.setRemitoId(remitoId);
        pesaje.setPesoTotal(remito.getPesoTotal());
        pesaje.setTachoId(remito.getTachoId());
        pesaje.setTachoPeso(remito.getTachoPeso());
        List<ColadaRemito> remitoColadas = remito.getColadas();
        List<ColadaPesaje> pesajeColadas = new ArrayList<>();

        for (ColadaRemito colada : remitoColadas) {
            pesajeColadas.add(new ColadaPesaje(colada.getColada(), colada.getPeso(),
                    colada.getModeloId(), colada.getFecha(), colada.getImagen(), colada.getCantidad(),
                    colada.getPesoTotal()));
        }

        pesaje.setColadas(pesajeColadas);

        if (pesaje.getColadas() != null) {
            for (ColadaPesaje colada : pesaje.getColadas()) {
                colada.setPesaje(pesaje);
            }
        }

        pesaje.setPesado(false);
        pesaje.setEgresado(false);

        return pesajeRepository.save(pesaje);
    }
}

