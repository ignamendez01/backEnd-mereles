package com.ignacio.backendmereles.Remito;

import com.ignacio.backendmereles.Modelo.Modelo;
import com.ignacio.backendmereles.Tacho.Tacho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RemitoRepository extends JpaRepository<Remito, Long> {
    List<Remito> findByActivoTrueOrderByIdAsc();
}
