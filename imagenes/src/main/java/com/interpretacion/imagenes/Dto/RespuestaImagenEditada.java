package com.interpretacion.imagenes.Dto;

import java.util.List;

public class RespuestaImagenEditada {
    private String nombreImagenOriginal;
    private String nombreImagenEditada;
    private String nombreImagenEscaneada;
    private List<PuntoDTO> coordenadas;
    private Integer anchoResultado;
    private Integer altoResultado;
    private Double umbral;

    public List<PuntoDTO> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(List<PuntoDTO> coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getNombreImagenOriginal() {
        return nombreImagenOriginal;
    }

    public void setNombreImagenOriginal(String nombreImagenOriginal) {
        this.nombreImagenOriginal = nombreImagenOriginal;
    }

    public String getNombreImagenEditada() {
        return nombreImagenEditada;
    }

    public void setNombreImagenEditada(String nombreImagenEditada) {
        this.nombreImagenEditada = nombreImagenEditada;
    }

    public Integer getAnchoResultado() {
        return anchoResultado;
    }

    public void setAnchoResultado(Integer anchoResultado) {
        this.anchoResultado = anchoResultado;
    }

    public Integer getAltoResultado() {
        return altoResultado;
    }

    public void setAltoResultado(Integer altoResultado) {
        this.altoResultado = altoResultado;
    }

    public Double getUmbral() {
        return umbral;
    }

    public void setUmbral(Double umbral) {
        this.umbral = umbral;
    }

    public String getNombreImagenEscaneada() {
        return nombreImagenEscaneada;
    }

    public void setNombreImagenEscaneada(String nombreImagenEscaneada) {
        this.nombreImagenEscaneada = nombreImagenEscaneada;
    }
}
