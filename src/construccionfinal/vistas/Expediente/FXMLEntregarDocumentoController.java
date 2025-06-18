package construccionfinal.vistas.Expediente;

import construccionfinal.dao.DocumentoInicialDAO;
import construccionfinal.modelo.pojo.DocumentoInicial;
import construccionfinal.utilidades.Utilidad;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.time.LocalDate;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLEntregarDocumentoController implements Initializable {

    @FXML private Pane paneDoc1;
    @FXML private Pane paneDoc2;
    @FXML private Pane paneDoc3;
    @FXML private Pane paneDoc4;
    @FXML private Pane paneDoc5;

    private File archivo1, archivo2, archivo3, archivo4, archivo5;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // No hay lógica inicial por ahora
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/construccionfinal/vistas/FXMLPantallaAnterior.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) paneDoc1.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo volver a la pantalla anterior");
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        DocumentoInicialDAO dao = new DocumentoInicialDAO();
        boolean exito = true;

        exito &= guardarDocumento(archivo1, "Oficio de Asignacion", dao);
        exito &= guardarDocumento(archivo2, "Carta de Aceptacion", dao);
        exito &= guardarDocumento(archivo3, "Constancia de Seguro", dao);
        exito &= guardarDocumento(archivo4, "Horario", dao);
        exito &= guardarDocumento(archivo5, "Cronograma", dao);

        if (exito) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Documentos guardados correctamente");
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Uno o más documentos no se pudieron guardar");
        }
    }

    @FXML
    private void clicSeleccionarDocumento1(ActionEvent event) {
        archivo1 = seleccionarArchivo(paneDoc1);
    }

    @FXML
    private void clicSeleccionarDocumento2(ActionEvent event) {
        archivo2 = seleccionarArchivo(paneDoc2);
    }

    @FXML
    private void clicSeleccionarDocumento3(ActionEvent event) {
        archivo3 = seleccionarArchivo(paneDoc3);
    }

    @FXML
    private void clicSeleccionarDocumento4(ActionEvent event) {
        archivo4 = seleccionarArchivo(paneDoc4);
    }

    @FXML
    private void clicSeleccionarDocumento5(ActionEvent event) {
        archivo5 = seleccionarArchivo(paneDoc5);
    }

    private File seleccionarArchivo(Pane pane) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Documento");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"),
            new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        if (file != null) {
            pane.setStyle("-fx-background-color: #a1d99b"); // Verde claro como confirmación
        }
        return file;
    }

    private boolean guardarDocumento(File archivo, String tipoDocumento, DocumentoInicialDAO dao) {
        if (archivo == null) return true; // Si no se seleccionó archivo, omitir

        try {
            byte[] bytesArchivo = Files.readAllBytes(archivo.toPath());
            DocumentoInicial doc = new DocumentoInicial(
                0,
                archivo.getName(),
                "Entregado",
                tipoDocumento,
                Date.valueOf(LocalDate.now()),
                bytesArchivo
            );
            return dao.agregar(doc);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

