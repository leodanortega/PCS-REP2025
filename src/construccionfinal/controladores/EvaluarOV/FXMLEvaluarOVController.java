package construccionfinal.controladores.EvaluarOV;

import construccionfinal.dao.OrganizacionVinculadaDAO;
import construccionfinal.modelo.pojo.*;
import construccionfinal.utilidades.Utilidad;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class FXMLEvaluarOVController implements Initializable {

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
    private VBox vbCriterios;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;

    private final Map<CriterioEvaluacion, ToggleGroup> criteriosToggleGroups = new LinkedHashMap<>();
    private Estudiante estudiante;
    private Proyecto proyecto;
    private OrganizacionVinculada organizacion;
    private ResponsableProyecto responsable;
    private List<CriterioEvaluacion> criterios;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void setDatos(Estudiante estudiante, Proyecto proyecto, OrganizacionVinculada organizacion, ResponsableProyecto responsable, List<CriterioEvaluacion> criterios) {
        this.estudiante = estudiante;
        this.proyecto = proyecto;
        this.organizacion = organizacion;
        this.responsable = responsable;
        this.criterios = criterios;

        lblNombreAlumno.setText(estudiante.getNombre());
        lblMatricula.setText(estudiante.getIdentificador());
        lblNombreProyecto.setText(proyecto.getNombre());
        lblOrganizacion.setText(organizacion.getNombre());
        lblResponsableProyecto.setText(responsable.getNombre());

        cargarCriterios(criterios);
    }

    private void cargarCriterios(List<CriterioEvaluacion> criterios) {
        vbCriterios.getChildren().clear();
        for (CriterioEvaluacion criterio : criterios) {
            Label lblCriterio = new Label(criterio.getDescripcion());
            ToggleGroup toggleGroup = new ToggleGroup();
            HBox hboxOpciones = new HBox(10);
            hboxOpciones.getChildren().add(lblCriterio);
            for (int i = 1; i <= 5; i++) {
                RadioButton rb = new RadioButton(String.valueOf(i));
                rb.setToggleGroup(toggleGroup);
                hboxOpciones.getChildren().add(rb);
            }
            vbCriterios.getChildren().add(hboxOpciones);
            criteriosToggleGroups.put(criterio, toggleGroup);
        }
    }

    @FXML
    private void btnAceptar() {
        Map<CriterioEvaluacion, Integer> respuestas = new LinkedHashMap<>();
        boolean faltanRespuestas = false;
        if (!OrganizacionVinculadaDAO.hayConexion()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Sin conexión", "Error: No hay conexión con la base de datos");
            return;
        }
        for (Map.Entry<CriterioEvaluacion, ToggleGroup> entry : criteriosToggleGroups.entrySet()) {
            Toggle seleccionado = entry.getValue().getSelectedToggle();
            if (seleccionado == null) {
                faltanRespuestas = true;
                break;
            } else {
                int valor = Integer.parseInt(((RadioButton) seleccionado).getText());
                respuestas.put(entry.getKey(), valor);
            }
        }

        if (faltanRespuestas) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Datos inválidos", "Existen campos inválidos o incompletos, por favor verifica tu información");
            return;
        }
        abrirConfirmacionEvaluacion(respuestas);
    }

    private void abrirConfirmacionEvaluacion(Map<CriterioEvaluacion, Integer> respuestas) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/construccionfinal/vistas/EvaluarOV/FXMLConfirmarDatos.fxml"));
            Parent root = loader.load();

            FXMLConfirmarDatosController controller = loader.getController();
            if (controller == null) {
                System.err.println("Error: No se pudo obtener el controlador de FXMLConfirmarDatosController.");
                return;
            }

            controller.setDatos(estudiante, proyecto, organizacion, responsable, respuestas);

            Stage stage = new Stage();
            stage.setTitle("Confirmación de Evaluación");
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) btnAceptar.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error al abrir la ventana de confirmación.");
        }
    }

    @FXML
    private void btnCancelar() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        Utilidad.mostrarAlertaConfirmacion("Salir", "¿Estás seguro de que deseas cancelar la evaluación?");
        stage.close();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Evaluación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}