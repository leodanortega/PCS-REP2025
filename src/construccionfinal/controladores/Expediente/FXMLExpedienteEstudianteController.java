package construccionfinal.controladores.Expediente;

import construccionfinal.controladores.EntregarDocumentosIniciales.FXMLEntregarDocumentoController;
import construccionfinal.dao.DocumentoInicialDAO;
import construccionfinal.dao.ExpedienteDAO;
import construccionfinal.modelo.pojo.DocumentoInicial;
import construccionfinal.modelo.pojo.Estudiante;
import construccionfinal.modelo.pojo.Expediente;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class FXMLExpedienteEstudianteController implements Initializable {

    @FXML private Label lbMatricula;
    @FXML private Label lbNombre;
    @FXML private Label lbGrupo;
    @FXML private Label lbPeriodo;
    @FXML private Label lbCalificaciones;
    @FXML private Label lbHoras;

    @FXML private TableView<DocumentoInicial> tvDocumentosIniciales;
    @FXML private TableColumn<DocumentoInicial, String> tcNombreDI;
    @FXML private TableColumn<DocumentoInicial, String> tcEstadoDI;
    @FXML private TableColumn<DocumentoInicial, String> tcTipoDI;
    @FXML private TableColumn<DocumentoInicial, Date> tcFechaDI;
    @FXML private TableColumn<?, ?> tcArchivoDI;

    @FXML private TableView<?> tvReportes;
    @FXML private TableView<?> tvDocumentosFinales;
    @FXML private TableColumn<?, ?> tcNombreR;
    @FXML private TableColumn<?, ?> tcHorasR;
    @FXML private TableColumn<?, ?> tcEstadoR;
    @FXML private TableColumn<?, ?> tcCalificacionR;
    @FXML private TableColumn<?, ?> tcFechaR;
    @FXML private TableColumn<?, ?> tcArchivoR;

    private Estudiante estudiante;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnasDocumentosIniciales();
    }

    public void inicializarDatos(Estudiante estudiante) {
        this.estudiante = estudiante;

        lbMatricula.setText(estudiante.getIdentificador());
        lbNombre.setText(estudiante.getNombreCompleto());

        Expediente expediente = ExpedienteDAO.obtenerExpedientePorEstudiante(estudiante.getIdUsuario());

        if (expediente != null) {
            lbCalificaciones.setText(expediente.getCalificaciones());
            lbHoras.setText(expediente.getHoras());

            cargarDocumentosIniciales(expediente.getIdExpediente());
        } else {
            mostrarAlerta("Aviso", "Este estudiante aún no tiene expediente registrado.", Alert.AlertType.INFORMATION);
        }
    }

    private void configurarColumnasDocumentosIniciales() {
        tcNombreDI.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcEstadoDI.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tcTipoDI.setCellValueFactory(new PropertyValueFactory<>("tipoDocumento"));
        tcFechaDI.setCellValueFactory(new PropertyValueFactory<>("fecha"));
    }

    private void cargarDocumentosIniciales(int idExpediente) {
        ObservableList<DocumentoInicial> documentos = DocumentoInicialDAO.obtenerDocumentosPorIdExpediente(idExpediente);
        tvDocumentosIniciales.setItems(documentos);
    }

    @FXML
    private void clicSubirDocumentosIniciales(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/construccionfinal/vistas/EntregarDocumentosIniciales/FXMLEntregarDocumento.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasar el id del expediente
            FXMLEntregarDocumentoController controller = loader.getController();
            Expediente expediente = ExpedienteDAO.obtenerExpedientePorEstudiante(estudiante.getIdUsuario());

            if (expediente != null) {
                controller.setIdExpediente(expediente.getIdExpediente());
            } else {
                mostrarAlerta("Aviso", "No se puede subir documentos porque el estudiante no tiene expediente registrado.", Alert.AlertType.WARNING);
                return;
            }

            Stage stage = new Stage();
            stage.setTitle("Entregar Documentos Iniciales");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana para subir documentos.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
