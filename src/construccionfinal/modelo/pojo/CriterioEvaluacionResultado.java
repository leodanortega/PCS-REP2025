package construccionfinal.modelo.pojo;

public class CriterioEvaluacionResultado {
    private int idEvaluacionOV;
    private int idCriterio;
    private double puntajeObtenido;

    public int getIdEvaluacionOV() {
        return idEvaluacionOV;
    }

    public void setIdEvaluacionOV(int idEvaluacionOV) {
        this.idEvaluacionOV = idEvaluacionOV;
    }

    public int getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(int idCriterio) {
        this.idCriterio = idCriterio;
    }

    public double getPuntajeObtenido() {
        return puntajeObtenido;
    }

    public void setPuntajeObtenido(double puntajeObtenido) {
        this.puntajeObtenido = puntajeObtenido;
    }
    // ✅ Constructor vacío (necesario si se usa en algunas implementaciones)
    public CriterioEvaluacionResultado() {}

    // ✅ Constructor con parámetros, para que la conversión en `clicEvaluar()` funcione
    public CriterioEvaluacionResultado(int idEvaluacion, int idCriterio, int puntajeObtenido) {
        this.idEvaluacionOV = idEvaluacion;
        this.idCriterio = idCriterio;
        this.puntajeObtenido = puntajeObtenido;
    }

}
