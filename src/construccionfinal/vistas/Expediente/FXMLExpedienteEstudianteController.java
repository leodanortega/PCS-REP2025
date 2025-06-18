
package construccionfinal.vistas.Expediente;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class FXMLExpedienteEstudianteController implements Initializable {

    @FXML
    private Label lbMatricula;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbGrupo;
    @FXML
    private Label lbPeriodo;
    @FXML
    private Label lbCalificaciones;
    @FXML
    private Label lbHoras;
    @FXML
    private TableView<?> tvDocumentosIniciales;
    @FXML
    private TableColumn<?, ?> tcNombreDI;
    @FXML
    private TableColumn<?, ?> tcEstadoDI;
    @FXML
    private TableColumn<?, ?> tcTipoDI;
    @FXML
    private TableColumn<?, ?> tcFechaDI;
    @FXML
    private TableColumn<?, ?> tcArchivoDI;
    @FXML
    private TableView<?> tvReportes;
    @FXML
    private TableColumn<?, ?> tcNombreR;
    @FXML
    private TableColumn<?, ?> tcHorasR;
    @FXML
    private TableColumn<?, ?> tcEstadoR;
    @FXML
    private TableColumn<?, ?> tcCalificacionR;
    @FXML
    private TableColumn<?, ?> tcFechaR;
    @FXML
    private TableColumn<?, ?> tcArchivoR;
    @FXML
    private TableView<?> tvDocumentosFinales;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicSubirDocumentosIniciales(ActionEvent event) {
        abrirNuevaVentana("/construccionfinal/vistas/EntregarDocumentosIniciales/FXMLEntregarDocumento.fxml", "Entregar documentos iniciales");
    }
    
    private void abrirNuevaVentana(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
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
