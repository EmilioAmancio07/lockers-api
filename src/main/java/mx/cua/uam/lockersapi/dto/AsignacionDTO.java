package mx.cua.uam.lockersapi.dto;

import java.time.LocalDateTime;

public class AsignacionDTO {
    private Integer id;         // Cambiado a Integer
    private Integer idAlumno;   // Cambiado a Integer
    private Integer idLocker;   // Cambiado a Integer
    private String idTrimestre;
    private LocalDateTime fechaRegistro;
    private String estatusEntrega;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getIdAlumno() { return idAlumno; }
    public void setIdAlumno(Integer idAlumno) { this.idAlumno = idAlumno; }
    public Integer getIdLocker() { return idLocker; }
    public void setIdLocker(Integer idLocker) { this.idLocker = idLocker; }
    public String getIdTrimestre() { return idTrimestre; }
    public void setIdTrimestre(String idTrimestre) { this.idTrimestre = idTrimestre; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public String getEstatusEntrega() { return estatusEntrega; }
    public void setEstatusEntrega(String estatusEntrega) { this.estatusEntrega = estatusEntrega; }
}