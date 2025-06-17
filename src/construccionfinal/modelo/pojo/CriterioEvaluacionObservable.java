package construccionfinal.modelo.pojo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CriterioEvaluacionObservable {
    private int idCriterio;
    private StringProperty nombreCriterio;
    private StringProperty descripcion;

    public CriterioEvaluacionObservable() {
        this.nombreCriterio = new SimpleStringProperty("");
        this.descripcion = new SimpleStringProperty("");
    }

    public CriterioEvaluacionObservable(int idCriterio, String nombreCriterio, String descripcion) {
        this.idCriterio = idCriterio;
        this.nombreCriterio = new SimpleStringProperty(nombreCriterio);
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    public int getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(int idCriterio) {
        this.idCriterio = idCriterio;
    }

    public String getNombreCriterio() {
        return nombreCriterio.get();
    }

    public void setNombreCriterio(String nombreCriterio) {
        this.nombreCriterio.set(nombreCriterio);
    }

    public StringProperty nombreCriterioProperty() {
        return nombreCriterio;
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }
}