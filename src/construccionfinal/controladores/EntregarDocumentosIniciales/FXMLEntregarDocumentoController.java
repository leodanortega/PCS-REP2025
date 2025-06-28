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
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLEntregarDocumentoController implements Initializable {

    @FXML private Label lbDoc1, lbDoc2, lbDoc3, lbDoc4, lbDoc5;
    @FXML private Button btnCancelar;
    @FXML private Button btnDoc1, btnDoc2, btnDoc3, btnDoc4, btnDoc5;
    @FXML private Label lbFechaInicio, lbHoraInicio, lbFechaFin, lbHoraFin;

    private File archivo1, archivo2, archivo3, archivo4, archivo5;
    private int idExpediente;
    private static final String ESTADO_PENDIENTE = "Pendiente";
    private EntregaDocumento rangoEntregaGeneral;
    private boolean entregaPermitida = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rangoEntregaGeneral = EntregaDocumentoDAO.obtenerRangoPorTipo("Inicial");
        mostrarRangoEntrega();

        if (!validarRangoActual()) {
            entregaPermitida = false;
            mostrarAlertaFueraDeRango();
            deshabilitarBotonesSeleccion();
        }
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

    private boolean validarRangoActual() {
        if (rangoEntregaGeneral == null) return false;

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

        return dentroDeFecha && dentroDeHora;
    }

    private void mostrarAlertaFueraDeRango() {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
            "Entrega no disponible",
            "La entrega de documentos no está permitida en este momento.\n" +
            "Periodo permitido:\n" +
            rangoEntregaGeneral.getFechaInicio() + " " + rangoEntregaGeneral.getHoraInicio() + " a " +
            rangoEntregaGeneral.getFechaFin() + " " + rangoEntregaGeneral.getHoraFin());
    }

    private void deshabilitarBotonesSeleccion() {
        btnDoc1.setDisable(true);
        btnDoc2.setDisable(true);
        btnDoc3.setDisable(true);
        btnDoc4.setDisable(true);
        btnDoc5.setDisable(true);
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
        if (!entregaPermitida) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Entrega no permitida",
                    "No se puede guardar porque el periodo de entrega ha finalizado.");
            return;
        }

        if (archivo1 == null && archivo2 == null && archivo3 == null && archivo4 == null && archivo5 == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    "Advertencia", "Debes seleccionar al menos un documento para guardar.");
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

        ((Stage) btnCancelar.getScene().getWindow()).close();
    }

@FXML
private void clicSeleccionarDocumento1(ActionEvent event) {
    try {
        if (DocumentoInicialDAO.existeDocumentoAprobado(idExpediente, "Oficio de Asignacion")) {
            lbDoc1.setText("Ya aprobado");
            archivo1 = null;
            btnDoc1.setDisable(true);
            return;
        }
    } catch (NullPointerException e) {
        System.err.println("NullPointerException en clicSeleccionarDocumento1: " + e.getMessage());
        archivo1 = null;
        btnDoc1.setDisable(true);
        return;
    }
    archivo1 = seleccionarArchivo(lbDoc1);
}

@FXML
private void clicSeleccionarDocumento2(ActionEvent event) {
    try {
        if (DocumentoInicialDAO.existeDocumentoAprobado(idExpediente, "Carta de Aceptacion")) {
            lbDoc2.setText("Ya aprobado");
            archivo2 = null;
            btnDoc2.setDisable(true);
            return;
        }
    } catch (NullPointerException e) {
        System.err.println("NullPointerException en clicSeleccionarDocumento2: " + e.getMessage());
        archivo2 = null;
        btnDoc2.setDisable(true);
        return;
    }
    archivo2 = seleccionarArchivo(lbDoc2);
}

@FXML
private void clicSeleccionarDocumento3(ActionEvent event) {
    try {
        if (DocumentoInicialDAO.existeDocumentoAprobado(idExpediente, "Constancia de Seguro")) {
            lbDoc3.setText("Ya aprobado");
            archivo3 = null;
            btnDoc3.setDisable(true);
            return;
        }
    } catch (NullPointerException e) {
        System.err.println("NullPointerException en clicSeleccionarDocumento3: " + e.getMessage());
        archivo3 = null;
        btnDoc3.setDisable(true);
        return;
    }
    archivo3 = seleccionarArchivo(lbDoc3);
}

@FXML
private void clicSeleccionarDocumento4(ActionEvent event) {
    try {
        if (DocumentoInicialDAO.existeDocumentoAprobado(idExpediente, "Horario")) {
            lbDoc4.setText("Ya aprobado");
            archivo4 = null;
            btnDoc4.setDisable(true);
            return;
        }
    } catch (NullPointerException e) {
        System.err.println("NullPointerException en clicSeleccionarDocumento4: " + e.getMessage());
        archivo4 = null;
        btnDoc4.setDisable(true);
        return;
    }
    archivo4 = seleccionarArchivo(lbDoc4);
}

@FXML
private void clicSeleccionarDocumento5(ActionEvent event) {
    try {
        if (DocumentoInicialDAO.existeDocumentoAprobado(idExpediente, "Cronograma")) {
            lbDoc5.setText("Ya aprobado");
            archivo5 = null;
            btnDoc5.setDisable(true);
            return;
        }
    } catch (NullPointerException e) {
        System.err.println("NullPointerException en clicSeleccionarDocumento5: " + e.getMessage());
        archivo5 = null;
        btnDoc5.setDisable(true);
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
        try {
            byte[] bytesArchivo = Files.readAllBytes(archivo.toPath());
            DocumentoInicial doc = new DocumentoInicial(
                0,
                archivo.getName(),
                ESTADO_PENDIENTE,
                tipoDocumento,
                Date.valueOf(LocalDate.now()),
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