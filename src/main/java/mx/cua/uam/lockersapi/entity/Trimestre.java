package mx.cua.uam.lockersapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Trimestre")
public class Trimestre {

    @Id
    @Column(name = "id_trimestre", length = 5)
    private String id;

    @Column(name = "fecha_inicio", nullable = false)
    private java.time.LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private java.time.LocalDate fechaFin;

    public Trimestre() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public java.time.LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(java.time.LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public java.time.LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(java.time.LocalDate fechaFin) { this.fechaFin = fechaFin; }
}