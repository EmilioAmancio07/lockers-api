package mx.cua.uam.lockersapi.dto;

public class AlumnoDTO {
    private Integer id; // Cambiado a Integer
    private String nombre;
    private String correo;
    private String licenciatura;
    private String estatus;
    private String lockerAsignado;
    private String matricula;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getLicenciatura() { return licenciatura; }
    public void setLicenciatura(String licenciatura) { this.licenciatura = licenciatura; }
    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
    public String getLockerAsignado() { return lockerAsignado; }
    public void setLockerAsignado(String lockerAsignado) { this.lockerAsignado = lockerAsignado; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}