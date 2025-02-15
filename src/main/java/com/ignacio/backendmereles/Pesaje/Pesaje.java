package com.ignacio.backendmereles.Pesaje;

import com.ignacio.backendmereles.Colada.Colada;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Pesaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "pesaje_id")
    private List<Colada> coladas;

    @NotNull
    private Double pesoTotal;

    @NotNull
    private Double tachoId;

    @NotNull
    private Boolean pesado;

    @NotNull
    private Boolean egresado;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Colada> getColadas() {
        return coladas;
    }

    public void setColadas(List<Colada> coladas) {
        this.coladas = coladas;
    }

    public Double getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(Double pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public Double getTachoId() {
        return tachoId;
    }

    public void setTachoId(Double tachoId) {
        this.tachoId = tachoId;
    }

    public Boolean getPesado() {
        return pesado;
    }

    public void setPesado(Boolean pesado) {
        this.pesado = pesado;
    }

    public Boolean getEgresado() {
        return egresado;
    }

    public void setEgresado(Boolean egresado) {
        this.egresado = egresado;
    }
}
