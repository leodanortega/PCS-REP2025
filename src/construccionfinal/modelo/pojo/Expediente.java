package construccionfinal.modelo.pojo;

public class Expediente {
    private int idExpediente;
    private int idEstudiante;
    private int idGrupoEE;
    private int idPeriodo;
    private String calificaciones;
    private String horas;
    private String informe;
    private int idDocumentoInicial;

    public Expediente() {}

    public Expediente(int idExpediente, int idEstudiante, int idGrupoEE, int idPeriodo, String calificaciones, String horas, String informe, int idDocumentoInicial) {
        this.idExpediente = idExpediente;
        this.idEstudiante = idEstudiante;
        this.idGrupoEE = idGrupoEE;
        this.idPeriodo = idPeriodo;
        this.calificaciones = calificaciones;
        this.horas = horas;
        this.informe = informe;
        this.idDocumentoInicial = idDocumentoInicial;
    }

    public int getIdExpediente() { return idExpediente; }
    public void setIdExpediente(int idExpediente) { this.idExpediente = idExpediente; }

    public int getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(int idEstudiante) { this.idEstudiante = idEstudiante; }

    public int getIdGrupoEE() { return idGrupoEE; }
    public void setIdGrupoEE(int idGrupoEE) { this.idGrupoEE = idGrupoEE; }

    public int getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(int idPeriodo) { this.idPeriodo = idPeriodo; }

    public String getCalificaciones() { return calificaciones; }
    public void setCalificaciones(String calificaciones) { this.calificaciones = calificaciones; }

    public String getHoras() { return horas; }
    public void setHoras(String horas) { this.horas = horas; }

    public String getInforme() { return informe; }
    public void setInforme(String informe) { this.informe = informe; }

    public int getIdDocumentoInicial() {
        return idDocumentoInicial;
    }

    public void setIdDocumentoInicial(int idDocumentoInicial) {
        this.idDocumentoInicial = idDocumentoInicial;
    }
}