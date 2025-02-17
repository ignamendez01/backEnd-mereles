package com.ignacio.backendmereles.Pesaje;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PesajeRepository extends JpaRepository<Pesaje, Long> {
    List<Pesaje> findByPesadoFalse();
    List<Pesaje> findByPesadoTrueAndEgresadoFalse();
    List<Pesaje> findAllByOrderByIdAsc();
}
