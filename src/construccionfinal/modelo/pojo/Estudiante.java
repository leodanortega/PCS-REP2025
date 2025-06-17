package construccionfinal.modelo.pojo;

import java.math.BigDecimal;

public class Estudiante extends Usuario {

    private BigDecimal calificacion;
    private int creditos;
    private String estado;
    private int semestre;

    public Estudiante() {
        super();
    }

    public Estudiante(int idUsuario, String nombre, String apePaterno, String apeMaterno, String correo,
                      String telefono, String rol, String identificador, String contrasenia,
                      BigDecimal calificacion, int creditos, String estado, int semestre) {
        super(idUsuario, nombre, apePaterno, apeMaterno, correo, telefono, rol, identificador, contrasenia);
        this.calificacion = calificacion;
        this.creditos = creditos;
        this.estado = estado;
        this.semestre = semestre;
    }

    public BigDecimal getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(BigDecimal calificacion) {
        this.calificacion = calificacion;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    @Override
    public String toString() {
        return super.toString() + " - Semestre: " + semestre;
    }
}
