package construccionfinal.controladores.RegistrarResponsable;

import construccionfinal.dao.OrganizacionVinculadaDAO;
import construccionfinal.modelo.pojo.OrganizacionVinculada;
import construccionfinal.modelo.pojo.ResponsableProyecto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class FXMLSeleccionarOrganizacionVinculadaController {

    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<OrganizacionVinculada> tablaOrganizaciones;
    @FXML
    private TableColumn<OrganizacionVinculada, String> colNombre;
    @FXML
    private TableColumn<OrganizacionVinculada, String> colCorreo;
    @FXML
    private TableColumn<OrganizacionVinculada, String> colRFC;
    @FXML
    private TableColumn<OrganizacionVinculada, String> colTelefono;
    @FXML
    private TableColumn<OrganizacionVinculada, String> colTipo;
    private boolean fueCancelado = false;
    private OrganizacionVinculadaDAO organizacionDAO = new OrganizacionVinculadaDAO();
    private ResponsableProyecto responsableProyecto;
    private Stage ventanaRegistrarResponsable;

    public void inicializarDatos(ResponsableProyecto responsableProyecto) {
        this.responsableProyecto = responsableProyecto;
    }
    public boolean isFueCancelado() {
        return fueCancelado;
    }

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colRFC.setCellValueFactory(new PropertyValueFactory<>("RFC"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        cargarOrganizaciones();

        txtBuscar.textProperty().addListener((obs, oldText, newText) -> {
            if (newText == null || newText.trim().isEmpty()) {
                cargarOrganizaciones();
            } else {
                buscarOrganizaciones(newText);
            }
        });
    }

    private void cargarOrganizaciones() {
        List<OrganizacionVinculada> lista = organizacionDAO.listar();
        tablaOrganizaciones.setItems(FXCollections.observableArrayList(lista));
    }

    private void buscarOrganizaciones(String filtro) {
        List<OrganizacionVinculada> lista = organizacionDAO.buscarPorNombre(filtro);
        tablaOrganizaciones.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void clicAceptar() {
        OrganizacionVinculada seleccionada = tablaOrganizaciones.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            responsableProyecto.setOrganizacionVinculada(seleccionada);
            abrirVentanaConfirmacion("/construccionfinal/vistas/RegistrarResponsable/FXMLConfirmarDatos.fxml", "Confirmar datos del responsable");
            cerrarVentana();
        } else {
            mostrarAlerta("Debes seleccionar una organización.");
        }
    }

    @FXML
    private void clicCancelar() {
        fueCancelado = true;
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tablaOrganizaciones.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void abrirVentanaConfirmacion(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            FXMLConfirmarDatosController controller = loader.getController();

            controller.inicializarDatos(responsableProyecto);
            controller.setVentanaRegistrarResponsable(ventanaRegistrarResponsable);

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("No se pudo abrir la ventana: " + rutaFXML);
        }
    }


    public void setVentanaRegistrarResponsable(Stage stage) {
        this.ventanaRegistrarResponsable = stage;
    }
}
