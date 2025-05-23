package com.ignacio.backendmereles.Coladas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ignacio.backendmereles.Remito.Remito;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class ColadaRemito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double colada;

    @NotNull
    private Double peso;

    @NotNull
    private Long modeloId;

    @NotNull
    private LocalDate fecha;

    private String imagen;

    @NotNull
    private Integer cantidad;

    @NotNull
    private Double pesoTotal;

    @ManyToOne
    @JoinColumn(name = "remito_id")
    @JsonBackReference
    private Remito remito;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull Double getColada() {
        return colada;
    }

    public void setColada(@NotNull Double colada) {
        this.colada = colada;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Long getModeloId() {
        return modeloId;
    }

    public void setModeloId(Long modeloId) {
        this.modeloId = modeloId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(Double pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public Remito getRemito() {
        return remito;
    }

    public void setRemito(Remito remito) {
        this.remito = remito;
    }

    @Override
    public String toString() {
        return "Colada{id=" + id +
                ", modelo=" + modeloId +
                ", colada n°=" + colada +
                ", cantidad=" + cantidad +
                ", peso=" + peso +
                ", peso total=" + pesoTotal +
                ", fecha=" + fecha +
                ", imagen=" + imagen +"}";
    }
}
