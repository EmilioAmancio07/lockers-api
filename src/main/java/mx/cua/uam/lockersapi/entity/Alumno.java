package mx.cua.uam.lockersapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Alumno")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alumno")
    private Integer id; // Cambiado a Integer

    private String nombre;
    private String correo;
    private String licenciatura;

    @Enumerated(EnumType.STRING)
    private EstatusAlumno estatus;

    public enum EstatusAlumno { Inscrito, Baja }

    public Alumno() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getLicenciatura() { return licenciatura; }
    public void setLicenciatura(String licenciatura) { this.licenciatura = licenciatura; }
    public EstatusAlumno getEstatus() { return estatus; }
    public void setEstatus(EstatusAlumno estatus) { this.estatus = estatus; }
}