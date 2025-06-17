package construccionfinal.modelo.pojo;

public class CriterioEvaluacion {
    private int idCriterio;
    private String nombreCriterio;
    private String descripcion;

    public CriterioEvaluacion(int idCriterio, String nombreCriterio, String descripcion) {
    }
    public CriterioEvaluacion() {
    }


    public void CriterioEvaluacionOV(int idCriterio, String nombreCriterio, String descripcion) {
        this.idCriterio = idCriterio;
        this.nombreCriterio = nombreCriterio;
        this.descripcion = descripcion;
    }

    public int getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(int idCriterio) {
        this.idCriterio = idCriterio;
    }

    public String getNombreCriterio() {
        return nombreCriterio;
    }

    public void setNombreCriterio(String nombreCriterio) {
        this.nombreCriterio = nombreCriterio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombreCriterio;
    }
}
