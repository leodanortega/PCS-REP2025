package construccionfinal.controladores.Expediente;

import construccionfinal.controladores.EntregarDocumentosIniciales.FXMLEntregarDocumentoController;
import construccionfinal.dao.DocumentoInicialDAO;
import construccionfinal.dao.ExpedienteDAO;
import construccionfinal.modelo.pojo.DocumentoInicial;
import construccionfinal.modelo.pojo.EntregaDocumento;
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

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.util.Callback;
import javafx.event.EventHandler;


public class FXMLExpedienteEstudianteController implements Initializable {
    @FXML
    private javafx.scene.control.Button btnRegresar;
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
    @FXML private TableColumn<DocumentoInicial, Void> tcArchivoDI;

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
            lbCalificaciones.setText("Califcaciones: "+expediente.getCalificaciones());
            lbHoras.setText("Horas:"+expediente.getHoras());

            cargarDocumentosIniciales(expediente.getIdExpediente());
        } else {
            mostrarAlerta("Aviso", "Este estudiante aún no tiene expediente registrado.", Alert.AlertType.INFORMATION);
        }
    }

    private void configurarColumnasDocumentosIniciales() {
        tcNombreDI.setCellValueFactory(new PropertyValueFactory<DocumentoInicial, String>("nombre"));
        tcEstadoDI.setCellValueFactory(new PropertyValueFactory<DocumentoInicial, String>("estado"));
        tcTipoDI.setCellValueFactory(new PropertyValueFactory<DocumentoInicial, String>("tipoDocumento"));
        tcFechaDI.setCellValueFactory(new PropertyValueFactory<DocumentoInicial, Date>("fecha"));

        // Configurar columna con botón para ver PDF (compatible con Java 8)
        tcArchivoDI.setCellFactory(new Callback<TableColumn<DocumentoInicial, Void>, TableCell<DocumentoInicial, Void>>() {
            @Override
            public TableCell<DocumentoInicial, Void> call(final TableColumn<DocumentoInicial, Void> param) {
                return new TableCell<DocumentoInicial, Void>() {
                    private final Button btnVer = new Button("Ver");

                    {
                        btnVer.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                DocumentoInicial doc = getTableView().getItems().get(getIndex());
                                mostrarPDF(doc);
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnVer);
                        }
                    }
                };
            }
        });
    }

    private void cargarDocumentosIniciales(int idExpediente) {
        ObservableList<DocumentoInicial> documentos = DocumentoInicialDAO.obtenerDocumentosPorIdExpediente(idExpediente);
        tvDocumentosIniciales.setItems(documentos);
    }

    private void mostrarPDF(DocumentoInicial doc) {
        try {
            File temp = File.createTempFile("doc_" + doc.getIdDocumentoInicial(), ".pdf");
            FileOutputStream fos = new FileOutputStream(temp);
            fos.write(doc.getArchivo());
            fos.close();

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(temp);
            } else {
                mostrarAlerta("No soportado", "Tu sistema no puede abrir archivos automáticamente.", Alert.AlertType.WARNING);
            }
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir el archivo PDF.", Alert.AlertType.ERROR);
        }
    }

@FXML
private void clicSubirDocumentosIniciales(ActionEvent event) {
    Expediente expediente = ExpedienteDAO.obtenerExpedientePorEstudiante(estudiante.getIdUsuario());

    if (expediente == null) {
        mostrarAlerta("Aviso", "No se puede subir documentos porque el estudiante no tiene expediente registrado.", Alert.AlertType.WARNING);
        return;
    }

    EntregaDocumento rango = construccionfinal.dao.EntregaDocumentoDAO.obtenerRangoPorTipo("Inicial");
    if (rango == null) {
        mostrarAlerta("Configuración faltante", "No se ha configurado el periodo de entrega de documentos iniciales.", Alert.AlertType.WARNING);
        return;
    }

    LocalDate hoy = LocalDate.now();
    LocalTime ahora = LocalTime.now();

    boolean dentroDeFecha = !hoy.isBefore(rango.getFechaInicio()) && !hoy.isAfter(rango.getFechaFin());
    boolean dentroDeHora = true;

    if (hoy.equals(rango.getFechaInicio()) && ahora.isBefore(rango.getHoraInicio())) {
        dentroDeHora = false;
    }
    if (hoy.equals(rango.getFechaFin()) && ahora.isAfter(rango.getHoraFin())) {
        dentroDeHora = false;
    }

    if (!dentroDeFecha || !dentroDeHora) {
        mostrarAlerta("Entrega no disponible", "La entrega de documentos iniciales no está permitida en este momento.\n" +
            "Periodo permitido:\n" +
            rango.getFechaInicio() + " " + rango.getHoraInicio() + " a " +
            rango.getFechaFin() + " " + rango.getHoraFin(), Alert.AlertType.INFORMATION);
        return;
    }

    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/construccionfinal/vistas/EntregarDocumentosIniciales/FXMLEntregarDocumento.fxml"));
        Parent root = loader.load();

        FXMLEntregarDocumentoController controller = loader.getController();
        controller.setIdExpediente(expediente.getIdExpediente());

        Stage stage = new Stage();
        stage.setTitle("Entregar Documentos Iniciales");
        stage.setScene(new Scene(root));
        stage.setOnHiding(e -> cargarDocumentosIniciales(expediente.getIdExpediente()));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        mostrarAlerta("Error", "Hubo un error al cargar la ventana de entrega de documentos.", Alert.AlertType.ERROR);
    }
}

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void regresar(ActionEvent event) {
        Stage stage = (Stage) btnRegresar.getScene().getWindow();
        stage.close();
    }
}