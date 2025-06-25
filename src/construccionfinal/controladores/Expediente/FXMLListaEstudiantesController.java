package construccionfinal.controladores.Expediente;

import construccionfinal.dao.EstudianteDAO;
import construccionfinal.modelo.pojo.Estudiante;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLListaEstudiantesController implements Initializable {
    @FXML
    private javafx.scene.control.Button btnRegresar;
    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableColumn<Estudiante, String> tcMatricula;
    @FXML
    private TableColumn<Estudiante, String> tcNombre;
    @FXML
    private TableColumn<Estudiante, String> tcCarrera;
    @FXML
    private TableColumn<Estudiante, Integer> tcCreditos;
    @FXML
    private TableColumn<Estudiante, Integer> tcSemestre;
    @FXML
    private TableColumn<Estudiante, String> tcEstado;
    @FXML
    private TableColumn<Estudiante, Double> tcCalificacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarEstudiantes();

        tvEstudiantes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tvEstudiantes.getSelectionModel().getSelectedItem() != null) {
                Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();
                abrirExpedienteEstudiante(estudianteSeleccionado);
            }
        });
    }

    private void configurarColumnas() {
        tcMatricula.setCellValueFactory(new PropertyValueFactory<>("identificador"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        tcCarrera.setCellValueFactory(new PropertyValueFactory<>("carrera"));
        tcCreditos.setCellValueFactory(new PropertyValueFactory<>("creditos"));
        tcSemestre.setCellValueFactory(new PropertyValueFactory<>("semestre"));
        tcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tcCalificacion.setCellValueFactory(new PropertyValueFactory<>("calificacion"));
    }

    private void cargarEstudiantes() {
        ObservableList<Estudiante> lista = EstudianteDAO.listarDatosAcademicos();
        tvEstudiantes.setItems(lista);
    }

    private void abrirExpedienteEstudiante(Estudiante estudiante) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/construccionfinal/vistas/Expediente/FXMLConsultarExpedienteEstudiante.fxml"));
            Parent root = loader.load();

            FXMLConsultarExpedienteEstudianteController controller = loader.getController();
            controller.inicializarDatos(estudiante);

            Stage stage = new Stage();
            stage.setTitle("Consultar expediente del estudiante");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("No se pudo abrir la ventana FXMLConsultarExpedienteEstudiante.fxml");
        }
    }

    @FXML
    private void regresar(ActionEvent event) {
        Stage stage = (Stage) btnRegresar.getScene().getWindow();
        stage.close();
    }
}
