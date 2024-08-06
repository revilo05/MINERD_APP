package com.example.minerd;

import java.io.Serializable;

public class Incidencia implements Serializable {
    private String titulo;
    private String centroEducativo;
    private String regional;
    private String distrito;
    private String fecha;
    private String descripcion;
    private String foto;

    // Getters y setters para los campos
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getCentroEducativo() { return centroEducativo; }
    public void setCentroEducativo(String centroEducativo) {
        this.centroEducativo = centroEducativo; }
    public String getRegional() { return regional; }
    public void setRegional(String regional) { this.regional = regional; }
    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion =
            descripcion; }
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    @Override
    public String toString() {
        return titulo;
    }
}