package com.ignacio.backendmereles.Coladas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ignacio.backendmereles.Pesaje.Pesaje;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class ColadaPesaje {
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
    @JoinColumn(name = "pesaje_id")
    @JsonBackReference
    private Pesaje pesaje;



    public ColadaPesaje(Double colada, Double peso, Long modeloId, LocalDate fecha, String imagen, Integer cantidad, Double pesoTotal) {
        this.colada = colada;
        this.peso = peso;
        this.modeloId = modeloId;
        this.fecha = fecha;
        this.imagen = imagen;
        this.cantidad = cantidad;
        this.pesoTotal = pesoTotal;
    }

    public ColadaPesaje() {

    }

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

    public Pesaje getPesaje() {
        return pesaje;
    }

    public void setPesaje(Pesaje pesaje) {
        this.pesaje = pesaje;
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
