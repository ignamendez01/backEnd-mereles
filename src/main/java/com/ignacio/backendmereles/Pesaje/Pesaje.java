package com.ignacio.backendmereles.Pesaje;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ignacio.backendmereles.Coladas.ColadaPesaje;
import com.ignacio.backendmereles.Coladas.ColadaRemito;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Pesaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "pesaje", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ColadaPesaje> coladas = new ArrayList<>();

    @NotNull
    private Double pesoTotal;

    @NotNull
    private Long remitoId;

    @NotNull
    private Long tachoId;

    @NotNull
    private Double tachoPeso;

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

    public List<ColadaPesaje> getColadas() {
        return coladas;
    }

    public void setColadas(List<ColadaPesaje> coladas) {
        this.coladas = coladas;
    }

    public Double getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(Double pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public Long getRemitoId() {
        return remitoId;
    }

    public void setRemitoId(Long remitoId) {
        this.remitoId = remitoId;
    }

    public Long getTachoId() {
        return tachoId;
    }

    public void setTachoId(Long tachoId) {
        this.tachoId = tachoId;
    }

    public Double getTachoPeso() {
        return tachoPeso;
    }

    public void setTachoPeso(Double tachoPeso) {
        this.tachoPeso = tachoPeso;
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

    @Override
    public String toString() {
        return "Pesaje{id=" + id +
                ", coladas=" + coladas +
                ", peso total=" + pesoTotal +
                ", remitoId=" + remitoId +
                ", tachoId=" + tachoId +
                ", tachoPeso=" + tachoPeso +
                ", pesado=" + pesado +
                ", egresado=" + egresado +"}";
    }
}
