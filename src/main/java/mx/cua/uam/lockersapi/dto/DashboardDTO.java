package mx.cua.uam.lockersapi.dto;

public class DashboardDTO {
    private String nombreAlumno;
    private String matricula;
    private String lockerAsignado;

    private String climaCuajimalpa;
    private String proximoAsueto; // ¡Adiós consejo, hola días festivos!
    private String avatarUrl;
    private String qrCodeUrl;

    // Getters y Setters
    public String getNombreAlumno() { return nombreAlumno; }
    public void setNombreAlumno(String nombreAlumno) { this.nombreAlumno = nombreAlumno; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getLockerAsignado() { return lockerAsignado; }
    public void setLockerAsignado(String lockerAsignado) { this.lockerAsignado = lockerAsignado; }
    public String getClimaCuajimalpa() { return climaCuajimalpa; }
    public void setClimaCuajimalpa(String climaCuajimalpa) { this.climaCuajimalpa = climaCuajimalpa; }
    public String getProximoAsueto() { return proximoAsueto; }
    public void setProximoAsueto(String proximoAsueto) { this.proximoAsueto = proximoAsueto; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public String getQrCodeUrl() { return qrCodeUrl; }
    public void setQrCodeUrl(String qrCodeUrl) { this.qrCodeUrl = qrCodeUrl; }
}