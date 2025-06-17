package construccionfinal.controladores.EvaluarEstudiante;

import construccionfinal.dao.EstudianteDAO;
import construccionfinal.modelo.pojo.Estudiante;
import construccionfinal.utilidades.Utilidad;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class FXMLEstudiantesPorEvaluarController {

    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<Estudiante> tablaEstudiantes;
    @FXML
    private TableColumn<Estudiante, String> colNombre;
    @FXML
    private TableColumn<Estudiante, String> colApellidoPaterno;
    @FXML
    private TableColumn<Estudiante, String> colApellidoMaterno;
    @FXML
    private TableColumn<Estudiante, String> colCorreo;
    @FXML
    private TableColumn<Estudiante, String> colTelefono;
    @FXML
    private TableColumn<Estudiante, String> colMatricula;

    private EstudianteDAO estudianteDAO = new EstudianteDAO();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apePaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory<>("apeMaterno"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("identificador"));

        cargarEstudiantes();

        txtBuscar.textProperty().addListener((obs, oldText, newText) -> {
            if (newText == null || newText.trim().isEmpty()) {
                cargarEstudiantes();
            } else {
                buscarEstudiantes(newText);
            }
        });
    }

    private void cargarEstudiantes() {
        List<Estudiante> lista = estudianteDAO.listar();
        tablaEstudiantes.setItems(FXCollections.observableArrayList(lista));
    }

    private void buscarEstudiantes(String filtro) {
        List<Estudiante> lista = estudianteDAO.buscarPorNombre(filtro);
        tablaEstudiantes.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void clicEvaluar() {
        Estudiante seleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            abrirVentanaEvaluacion("/construccionfinal/vistas/EvaluarEstudiante/FXMLEvaluarEstudiante.fxml", "Evaluar Estudiante", seleccionado);
            cerrarVentana();
        } else {
            mostrarAlerta("Debes seleccionar un estudiante para evaluar.");
        }
    }

    @FXML
    private void clicSalir() {
        Stage stage = (Stage) tablaEstudiantes.getScene().getWindow();
        Utilidad.mostrarAlertaConfirmacion("Salir", "¿Estás seguro de que deseas cancelar la evaluación?");
        stage.close();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tablaEstudiantes.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void abrirVentanaEvaluacion(String rutaFXML, String titulo, Estudiante estudiante) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            FXMLEvaluarEstudianteController controller = loader.getController();
            controller.inicializarDatos(estudiante);

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("No se pudo abrir la ventana: " + rutaFXML);
        }
    }
}