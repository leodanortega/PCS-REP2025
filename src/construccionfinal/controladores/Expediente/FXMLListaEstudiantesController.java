package construccionfinal.controladores.Expediente;

import construccionfinal.dao.EstudianteDAO;
import construccionfinal.modelo.pojo.Estudiante;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLListaEstudiantesController implements Initializable {

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
        abrirNuevaVentana("");
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
    
    private void abrirNuevaVentana(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("No se pudo abrir la ventana: " + rutaFXML);
        }
    }
}

