package construccionfinal.controladores.EntregarDocumentosIniciales;

import construccionfinal.dao.DocumentoInicialDAO;
import construccionfinal.dao.EntregaDocumentoDAO;
import construccionfinal.modelo.pojo.DocumentoInicial;
import construccionfinal.modelo.pojo.EntregaDocumento;
import construccionfinal.utilidades.Utilidad;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLEntregarDocumentoController implements Initializable {

    @FXML private Label lbDoc1;
    @FXML private Label lbDoc2;
    @FXML private Label lbDoc3;
    @FXML private Label lbDoc4;
    @FXML private Label lbDoc5;
    @FXML private Button btnCancelar;
    @FXML
    private Label lbFechaInicio;
    @FXML
    private Label lbHoraInicio;
    @FXML
    private Label lbFechaFin;
    @FXML
    private Label lbHoraFin;

    private File archivo1, archivo2, archivo3, archivo4, archivo5;
    private int idExpediente;
    private static final String ESTADO_PENDIENTE = "Pendiente";
    private EntregaDocumento rangoEntregaGeneral;
    
@Override
    public void initialize(URL url, ResourceBundle rb) {
        rangoEntregaGeneral = EntregaDocumentoDAO.obtenerRangoPorTipo("Inicial");
        mostrarRangoEntrega();
    }

    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }
    
private void mostrarRangoEntrega() {
        if (rangoEntregaGeneral != null) {
            lbFechaInicio.setText(rangoEntregaGeneral.getFechaInicio().toString());
            lbHoraInicio.setText(rangoEntregaGeneral.getHoraInicio().toString());
            lbFechaFin.setText(rangoEntregaGeneral.getFechaFin().toString());
            lbHoraFin.setText(rangoEntregaGeneral.getHoraFin().toString());
        } else {
            lbFechaInicio.setText("No configurado");
            lbHoraInicio.setText("No configurado");
            lbFechaFin.setText("No configurado");
            lbHoraFin.setText("No configurado");
        }
    }

@FXML
    private void clicCancelar(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Seguro que quieres cancelar?");
        if (alerta.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            ((Stage) btnCancelar.getScene().getWindow()).close();
        }
    }

@FXML
private void clicGuardar(ActionEvent event) {
    if (archivo1 == null && archivo2 == null && archivo3 == null && archivo4 == null && archivo5 == null) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Advertencia", "Debes seleccionar al menos un documento para guardar.");
        return;
    }

    DocumentoInicialDAO dao = new DocumentoInicialDAO();
    int exitos = 0;

    if (guardarDocumento(archivo1, "Oficio de Asignacion", dao)) exitos++;
    if (guardarDocumento(archivo2, "Carta de Aceptacion", dao)) exitos++;
    if (guardarDocumento(archivo3, "Constancia de Seguro", dao)) exitos++;
    if (guardarDocumento(archivo4, "Horario", dao)) exitos++;
    if (guardarDocumento(archivo5, "Cronograma", dao)) exitos++;

    if (exitos == 5) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Todos los documentos fueron guardados correctamente");
    } else if (exitos > 0) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Guardado parcial", exitos + " documento(s) fueron guardados.");
    } else {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo guardar ningún documento");
        return;
    }
    Stage stage = (Stage) btnCancelar.getScene().getWindow();
    stage.close();
}


@FXML
private void clicSeleccionarDocumento1(ActionEvent event) {
    if (DocumentoInicialDAO.existeDocumentoAprobado(idExpediente, "Oficio de Asignacion")) {
        lbDoc1.setText("Ya aprobado");
        ((Button) event.getSource()).setDisable(true);
        return;
    }
    archivo1 = seleccionarArchivo(lbDoc1);
}

@FXML
private void clicSeleccionarDocumento2(ActionEvent event) {
    if (DocumentoInicialDAO.existeDocumentoAprobado(idExpediente, "Carta de Aceptacion")) {
        lbDoc2.setText("Ya aprobado");
        ((Button) event.getSource()).setDisable(true);
        return;
    }
    archivo2 = seleccionarArchivo(lbDoc2);
}

@FXML
private void clicSeleccionarDocumento3(ActionEvent event) {
    if (DocumentoInicialDAO.existeDocumentoAprobado(idExpediente, "Constancia de Seguro")) {
        lbDoc3.setText("Ya aprobado");
        ((Button) event.getSource()).setDisable(true);
        return;
    }
    archivo3 = seleccionarArchivo(lbDoc3);
}

@FXML
private void clicSeleccionarDocumento4(ActionEvent event) {
    if (DocumentoInicialDAO.existeDocumentoAprobado(idExpediente, "Horario")) {
        lbDoc4.setText("Ya aprobado");
        ((Button) event.getSource()).setDisable(true);
        return;
    }
    archivo4 = seleccionarArchivo(lbDoc4);
}

@FXML
private void clicSeleccionarDocumento5(ActionEvent event) {
    if (DocumentoInicialDAO.existeDocumentoAprobado(idExpediente, "Cronograma")) {
        lbDoc5.setText("Ya aprobado");
        ((Button) event.getSource()).setDisable(true);
        return;
    }
    archivo5 = seleccionarArchivo(lbDoc5);
}


 private File seleccionarArchivo(Label label) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Documento");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"),
            new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );
        File file = fileChooser.showOpenDialog(label.getScene().getWindow());
        if (file != null) {
            label.setText(file.getName());
        }
        return file;
    }

private boolean guardarDocumento(File archivo, String tipoDocumento, DocumentoInicialDAO dao) {
        if (archivo == null) return false;

        if (!archivo.getName().toLowerCase().endsWith(".pdf")) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Archivo inválido",
                    "El archivo seleccionado para " + tipoDocumento + " no es un PDF válido.");
            return false;
        }

        if (rangoEntregaGeneral == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Sin configuración",
                    "No se ha configurado el rango general de entrega.");
            return false;
        }

        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now();

        boolean dentroDeFecha = !hoy.isBefore(rangoEntregaGeneral.getFechaInicio()) &&
                                !hoy.isAfter(rangoEntregaGeneral.getFechaFin());

        boolean dentroDeHora = true;

        if (hoy.equals(rangoEntregaGeneral.getFechaInicio()) && ahora.isBefore(rangoEntregaGeneral.getHoraInicio())) {
            dentroDeHora = false;
        }

        if (hoy.equals(rangoEntregaGeneral.getFechaFin()) && ahora.isAfter(rangoEntregaGeneral.getHoraFin())) {
            dentroDeHora = false;
        }

        if (!dentroDeFecha || !dentroDeHora) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Fuera de horario",
                    "La entrega del documento '" + tipoDocumento + "' no está permitida en este momento.\n" +
                    "Periodo permitido:\n" +
                    rangoEntregaGeneral.getFechaInicio() + " " + rangoEntregaGeneral.getHoraInicio() + " a " +
                    rangoEntregaGeneral.getFechaFin() + " " + rangoEntregaGeneral.getHoraFin());
            return false;
        }

        try {
            byte[] bytesArchivo = Files.readAllBytes(archivo.toPath());
            DocumentoInicial doc = new DocumentoInicial(
                0,
                archivo.getName(),
                ESTADO_PENDIENTE,
                tipoDocumento,
                Date.valueOf(hoy),
                bytesArchivo,
                idExpediente
            );
            return dao.agregar(doc);
        } catch (IOException e) {
            e.printStackTrace();
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de archivo",
                    "No se pudo leer el archivo: " + archivo.getName());
            return false;
        }
    }
}