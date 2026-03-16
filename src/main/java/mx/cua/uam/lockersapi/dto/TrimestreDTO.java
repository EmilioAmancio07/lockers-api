package mx.cua.uam.lockersapi.dto;

import java.time.LocalDate;

public class TrimestreDTO {
    private String id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}