package com.ignacio.backendmereles.Remito;

import com.ignacio.backendmereles.Colada.Colada;

import java.util.List;

public class RemitoUpdateRequest {
    private List<Colada> coladas;
    private Long tachoId;
    private Double pesoTotal;

    public RemitoUpdateRequest() {}

    public RemitoUpdateRequest(List<Colada> coladas, Long tachoId, Double pesoTotal) {
        this.coladas = coladas;
        this.tachoId = tachoId;
        this.pesoTotal = pesoTotal;
    }

    public List<Colada> getColadas() {
        return coladas;
    }

    public void setColadas(List<Colada> coladas) {
        this.coladas = coladas;
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

    @Override
    public String toString() {
        return "RemitoUpdatedRequest{coladas=" + coladas +
                ", peso total=" + pesoTotal +
                ", tachoId=" + tachoId +"}";
    }
}
