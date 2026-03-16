package mx.cua.uam.lockersapi.dto;

public class LockerDTO {

    private Integer id; // Usando Integer
    private String numeroLocker;
    private String ubicacion;
    private String estado;
    private String dimensiones;
    private String alumnoAsignado;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNumeroLocker() { return numeroLocker; }
    public void setNumeroLocker(String numeroLocker) { this.numeroLocker = numeroLocker; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getDimensiones() { return dimensiones; }
    public void setDimensiones(String dimensiones) { this.dimensiones = dimensiones; }

    public String getAlumnoAsignado() { return alumnoAsignado; }
    public void setAlumnoAsignado(String alumnoAsignado) { this.alumnoAsignado = alumnoAsignado; }
}