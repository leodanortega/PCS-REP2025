package construccionfinal.controladores.EvaluarOV;

import construccionfinal.modelo.pojo.*;
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
            mostrarAlerta("Completa todos los criterios antes de enviar la evaluación.");
            return;
        }
        System.out.println("Respuestas capturadas:");
        for (Map.Entry<CriterioEvaluacion, Integer> entry : respuestas.entrySet()) {
            System.out.println(entry.getKey().getDescripcion() + ": " + entry.getValue());
        }
        abrirConfirmacionEvaluacion(respuestas);
    }

    private void abrirConfirmacionEvaluacion(Map<CriterioEvaluacion, Integer> respuestas) {
        System.out.println("Enviando respuestas a FXMLConfirmarDatosController:");
        respuestas.forEach((criterio, valor) -> System.out.println(criterio.getDescripcion() + ": " + valor));

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/construccionfinal/vistas/EvaluarOV/FXMLConfirmarDatos.fxml"));
            Parent root = loader.load();

            // Obtén el controlador correctamente
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
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Evaluación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}