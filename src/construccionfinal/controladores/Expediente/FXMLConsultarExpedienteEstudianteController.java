package construccionfinal.controladores.Expediente;

import construccionfinal.dao.DocumentoInicialDAO;
import construccionfinal.modelo.pojo.DocumentoInicial;
import construccionfinal.modelo.pojo.Estudiante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLConsultarExpedienteEstudianteController implements Initializable {

    @FXML
    private Label lbMatricula;
    @FXML
    private Label lbNombre;
    @FXML
    private TableView<DocumentoInicial> tvDocumentosIniciales;
    @FXML
    private TableColumn<DocumentoInicial, String> tcNombreDI;
    @FXML
    private TableColumn<DocumentoInicial, String> tcEstadoDI;
    @FXML
    private TableColumn<DocumentoInicial, String> tcTipoDI;
    @FXML
    private TableColumn<DocumentoInicial, java.sql.Date> tcFechaDI;
    @FXML
    private TableColumn<DocumentoInicial, Void> tcArchivoDI;

    private Estudiante estudiante;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
    }

    public void inicializarDatos(Estudiante estudiante) {
        this.estudiante = estudiante;

        lbMatricula.setText(estudiante.getIdentificador());
        lbNombre.setText(estudiante.getNombreCompleto());
        cargarDocumentosIniciales();
    }

    private void configurarColumnas() {
        tcNombreDI.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcEstadoDI.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tcTipoDI.setCellValueFactory(new PropertyValueFactory<>("tipoDocumento"));
        tcFechaDI.setCellValueFactory(new PropertyValueFactory<>("fecha"));

    }

    private void cargarDocumentosIniciales() {
        DocumentoInicialDAO dao = new DocumentoInicialDAO();
        List<DocumentoInicial> lista = dao.listarTodos();

        ObservableList<DocumentoInicial> documentos = FXCollections.observableArrayList(lista);
        tvDocumentosIniciales.setItems(documentos);
    }
}
