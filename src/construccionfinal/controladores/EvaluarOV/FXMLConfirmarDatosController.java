package construccionfinal.controladores.EvaluarOV;

import construccionfinal.dao.CriterioEvaluacionResultadoDAO;
import construccionfinal.dao.EvaluacionOVDAO;
import construccionfinal.dao.ExpedienteDAO;
import construccionfinal.modelo.pojo.*;
import construccionfinal.utilidades.Utilidad;
import construccionfinal.utilidades.UtilidadImagen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FXMLConfirmarDatosController implements Initializable {

    @FXML
    private Label lblNombreAlumno;

    @FXML
    private Label lblMatricula;

    @FXML
    private Label lblNombreProyecto;

    @FXML
    private Label lblOrganizacion;

    @FXML
    private Label lblResponsableProyecto;

    @FXML
    private VBox vbCriteriosEvaluados;

    private Estudiante estudiante;
    private Proyecto proyecto;
    private OrganizacionVinculada organizacion;
    private ResponsableProyecto responsable;
    private Map<CriterioEvaluacion, Integer> respuestas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void setDatos(Estudiante estudiante, Proyecto proyecto, OrganizacionVinculada organizacion, ResponsableProyecto responsable, Map<CriterioEvaluacion, Integer> respuestas) {
        this.estudiante = estudiante;
        this.proyecto = proyecto;
        this.organizacion = organizacion;
        this.responsable = responsable;
        this.respuestas = respuestas;

        lblNombreAlumno.setText(estudiante.getNombre());
        lblMatricula.setText(estudiante.getIdentificador());
        lblNombreProyecto.setText(proyecto.getNombre());
        lblOrganizacion.setText(organizacion.getNombre());
        lblResponsableProyecto.setText(responsable.getNombre());

        cargarCriterios();
    }

    private void cargarCriterios() {
        vbCriteriosEvaluados.getChildren().clear();
        for (Map.Entry<CriterioEvaluacion, Integer> entry : respuestas.entrySet()) {
            Label lblCriterio = new Label(entry.getKey().getDescripcion() + " - Puntuación: " + entry.getValue());
            vbCriteriosEvaluados.getChildren().add(lblCriterio);
        }
    }

    @FXML
    private void clicCancelar() {
        Stage stage = (Stage) vbCriteriosEvaluados.getScene().getWindow();
        Utilidad.mostrarAlertaConfirmacion("Salir", "¿Estás seguro de que deseas cancelar la evaluación?");
        stage.close();
    }

    @FXML
    private void clicGuardar() {
        int idExpediente = obtenerIdExpediente();
        if (idExpediente == -1) {
            mostrarAlerta("Error: No cuentas con un expediente para poder realizar esta operación.");
            return;
        }

        int idEvaluacionOV = EvaluacionOVDAO.guardarEvaluacion(respuestas, idExpediente);
        if (idEvaluacionOV == -1) {
            mostrarAlerta("Error al guardar la evaluación.");
            return;
        }

        List<CriterioEvaluacionResultado> resultados = respuestas.entrySet().stream()
                .map(entry -> {
                    CriterioEvaluacionResultado resultado = new CriterioEvaluacionResultado();
                    resultado.setIdCriterio(entry.getKey().getIdCriterio());
                    resultado.setPuntajeObtenido(entry.getValue());
                    return resultado;
                })
                .collect(Collectors.toList());


        boolean guardadoResultados = CriterioEvaluacionResultadoDAO.guardarResultados(idEvaluacionOV, resultados);
        if (!guardadoResultados) {
            mostrarAlerta("La evaluación se guardó, pero hubo un error al registrar los resultados individuales.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Evaluación como Imagen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PNG", "*.png"));
        File archivoSeleccionado = fileChooser.showSaveDialog((Stage) vbCriteriosEvaluados.getScene().getWindow());

        if (archivoSeleccionado == null) {
            System.out.println("El usuario canceló la selección del archivo.");
            return;
        }

        UtilidadImagen.generarImagen(archivoSeleccionado.getAbsolutePath(), estudiante, proyecto, organizacion, responsable, respuestas);

        mostrarAlerta("Evaluación guardada y resultados registrados correctamente.");
        ((Stage) vbCriteriosEvaluados.getScene().getWindow()).close();
    }


    private double calcularPuntajeTotal() {
        int sumaTotal = respuestas.values().stream().mapToInt(Integer::intValue).sum();
        return sumaTotal / 10.0;
    }

    private int obtenerIdExpediente() {
        Expediente expediente = ExpedienteDAO.obtenerExpedientePorEstudiante(estudiante.getIdUsuario());

        if (expediente == null) {
            System.err.println("Error: No se encontró expediente para el estudiante.");
            return -1;
        }

        return expediente.getIdExpediente();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Confirmación de Evaluación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}