package construccionfinal.controladores.EvaluarOV;

import construccionfinal.dao.EvaluacionOVDAO;
import construccionfinal.dao.ExpedienteDAO;
import construccionfinal.modelo.pojo.*;
import construccionfinal.utilidades.UtilidadImagen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

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
        ((Stage) vbCriteriosEvaluados.getScene().getWindow()).close();
    }

    @FXML
    private void clicGuardar() {
        int idExpediente = obtenerIdExpediente();
        if (idExpediente == -1) {
            mostrarAlerta("Error: No se encontró un expediente válido para el estudiante.");
            return;
        }

        int idEvaluacionOV = EvaluacionOVDAO.guardarEvaluacion(respuestas, "", idExpediente);
        if (idEvaluacionOV == -1) {
            mostrarAlerta("Error al guardar la evaluación.");
            return;
        }

        // Permitir que el usuario seleccione la ruta para guardar la imagen
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Evaluación como Imagen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PNG", "*.png"));
        File archivoSeleccionado = fileChooser.showSaveDialog((Stage) vbCriteriosEvaluados.getScene().getWindow());

        if (archivoSeleccionado == null) {
            System.out.println("El usuario canceló la selección del archivo.");
            return;
        }

        // Generar imagen con la ruta seleccionada por el usuario
        UtilidadImagen.generarImagen(archivoSeleccionado.getAbsolutePath(), estudiante, proyecto, organizacion, responsable, respuestas);

        mostrarAlerta("Evaluación guardada y imagen generada correctamente.");
        ((Stage) vbCriteriosEvaluados.getScene().getWindow()).close();
    }

    private double calcularPuntajeTotal() {
        int sumaTotal = respuestas.values().stream().mapToInt(Integer::intValue).sum();
        return sumaTotal / 10.0; // Dividimos entre 10 para obtener el puntaje final
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