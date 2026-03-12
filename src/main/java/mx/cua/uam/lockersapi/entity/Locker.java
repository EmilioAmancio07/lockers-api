package mx.cua.uam.lockersapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Locker")
public class Locker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_locker")
    private Integer id; // Usando Integer

    @Column(name = "numero_locker", nullable = false, length = 500, unique = true)
    private String numeroLocker;

    private String ubicacion;

    @Enumerated(EnumType.STRING)
    private EstadoLocker estado;

    @Enumerated(EnumType.STRING)
    private Dimensiones dimensiones;

    public enum EstadoLocker { Disponible, Ocupado, Mantenimiento }
    public enum Dimensiones { mediano, grande }

    public Locker() {}

    // Getters y Setters exactos para Integer
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNumeroLocker() { return numeroLocker; }
    public void setNumeroLocker(String numeroLocker) { this.numeroLocker = numeroLocker; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public EstadoLocker getEstado() { return estado; }
    public void setEstado(EstadoLocker estado) { this.estado = estado; }

    public Dimensiones getDimensiones() { return dimensiones; }
    public void setDimensiones(Dimensiones dimensiones) { this.dimensiones = dimensiones; }
}