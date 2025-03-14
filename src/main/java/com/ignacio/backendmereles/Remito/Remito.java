package com.ignacio.backendmereles.Remito;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Remito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "remito", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ColadaRemito> coladas = new ArrayList<>();

    @NotNull
    private Double pesoTotal;

    @NotNull
    private Long tachoId;

    @NotNull
    private Double tachoPeso;

    @NotNull
    private Boolean activo;

    @NotNull
    private Boolean enviado;

    @NotNull
    private String estado;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ColadaRemito> getColadas() {
        return coladas;
    }

    public void setColadas(List<ColadaRemito> coladaRemitos) {
        this.coladas = coladaRemitos;
    }

    public Double getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(Double pesoTotal) {
        this.pesoTotal = pesoTotal;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getEnviado() {
        return enviado;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Remito{id=" + id +
                ", coladas=" + coladas +
                ", peso total=" + pesoTotal +
                ", tachoId=" + tachoId +
                ", tachoPeso=" + tachoPeso +
                ", activo=" + activo +
                ", enviado=" + enviado +
                ", estado=" + estado +"}";
    }
}
