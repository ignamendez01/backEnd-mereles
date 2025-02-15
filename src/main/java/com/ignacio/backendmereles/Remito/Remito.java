package com.ignacio.backendmereles.Remito;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ignacio.backendmereles.Colada.Colada;
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
    @JsonManagedReference // Gestiona la relación en el "lado propietario" (Remito)
    private List<Colada> coladas = new ArrayList<>();

    @NotNull
    private Double pesoTotal;

    @NotNull
    private Long tachoId;

    @NotNull
    private Boolean activo;

    @NotNull
    private Boolean enviado;

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

    public Long getTachoId() {
        return tachoId;
    }

    public void setTachoId(Long tachoId) {
        this.tachoId = tachoId;
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

    @Override
    public String toString() {
        return "Remito{id=" + id +
                ", coladas=" + coladas +
                ", peso total=" + pesoTotal +
                ", tachoId=" + tachoId +
                ", activo=" + activo +
                ", enviado=" + enviado +"}";
    }
}
