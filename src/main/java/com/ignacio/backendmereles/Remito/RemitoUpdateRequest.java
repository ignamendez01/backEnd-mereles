package com.ignacio.backendmereles.Remito;

import com.ignacio.backendmereles.Coladas.ColadaRemito;

import java.util.List;

public class RemitoUpdateRequest {
    private List<ColadaRemito> coladas;
    private Long tachoId;
    private Double pesoTotal;
    private Double pesoTacho;

    public RemitoUpdateRequest() {}

    public RemitoUpdateRequest(List<ColadaRemito> coladas, Long tachoId, Double pesoTotal, Double pesoTacho) {
        this.coladas = coladas;
        this.tachoId = tachoId;
        this.pesoTotal = pesoTotal;
        this.pesoTacho = pesoTacho;
    }

    public List<ColadaRemito> getColadas() {
        return coladas;
    }

    public void setColadas(List<ColadaRemito> coladaRemitos) {
        this.coladas = coladaRemitos;
    }

    public Long getTachoId() {
        return tachoId;
    }

    public void setTachoId(Long tachoId) {
        this.tachoId = tachoId;
    }

    public Double getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(Double pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public Double getPesoTacho() {
        return pesoTacho;
    }

    public void setPesoTacho(Double pesoTacho) {
        this.pesoTacho = pesoTacho;
    }

    @Override
    public String toString() {
        return "RemitoUpdatedRequest{coladas=" + coladas +
                ", peso total=" + pesoTotal +
                ", tachoId=" + tachoId +
                ", pesoTacho=" + pesoTacho +"}";
    }
}
