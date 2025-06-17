package construccionfinal.controladores.Expediente;

import construccionfinal.dao.EstudianteDAO;
import construccionfinal.modelo.pojo.Estudiante;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

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
}

