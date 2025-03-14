package com.ignacio.backendmereles.Remito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RemitoRepository extends JpaRepository<Remito, Long> {
    List<Remito> findByActivoTrueOrderByIdAsc();
    List<Remito> findByActivoTrueAndEnviadoFalseOrderByIdAsc();
}
