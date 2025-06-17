package construccionfinal.modelo.pojo;

public class CriterioEvaluacion {
    private int idCriterio;
    private String nombreCriterio;
    private String descripcion;

    // Constructor correcto con inicialización de atributos
    public CriterioEvaluacion(int idCriterio, String nombreCriterio, String descripcion) {
        this.idCriterio = idCriterio;
        this.nombreCriterio = nombreCriterio;
        this.descripcion = descripcion;
    }

    // Constructor vacío para casos donde no se inicialicen valores de inmediato
    public CriterioEvaluacion() {}

    // Métodos getter y setter
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