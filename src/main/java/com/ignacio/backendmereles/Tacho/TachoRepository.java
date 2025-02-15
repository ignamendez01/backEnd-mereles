package com.ignacio.backendmereles.Tacho;

import com.ignacio.backendmereles.Modelo.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TachoRepository extends JpaRepository<Tacho, Long> {
    List<Tacho> findByActivoTrueOrderByIdAsc();
}
