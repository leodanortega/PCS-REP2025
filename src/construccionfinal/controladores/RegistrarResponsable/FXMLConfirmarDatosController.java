package construccionfinal.controladores.RegistrarResponsable;

import construccionfinal.dao.ResponsableProyectoDAO;
import construccionfinal.modelo.pojo.OrganizacionVinculada;
import construccionfinal.modelo.pojo.ResponsableProyecto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLConfirmarDatosController {

    @FXML
    private Label lblNombre;
    @FXML
    private Label lblApePaterno;
    @FXML
    private Label lblApeMaterno;
    @FXML
    private Label lblCorreoResponsable;
    @FXML
    private Label lblTelefonoResponsable;
    @FXML
    private Label lblPuesto;

    @FXML
    private Label lblNombreOrg;
    @FXML
    private Label lblCorreoOrg;
    @FXML
    private Label lblRFC;
    @FXML
    private Label lblTelefonoOrg;
    @FXML
    private Label lblTipo;

    private ResponsableProyecto responsableProyecto;
    private  Stage ventanaRegistrarResponsable;
    public void inicializarDatos(ResponsableProyecto responsableProyecto) {
        this.responsableProyecto = responsableProyecto;

        lblNombre.setText(responsableProyecto.getNombre());
        lblApePaterno.setText(responsableProyecto.getApePaterno());
        lblApeMaterno.setText(responsableProyecto.getApeMaterno());
        lblCorreoResponsable.setText(responsableProyecto.getCorreo());
        lblTelefonoResponsable.setText(responsableProyecto.getTelefono());
        lblPuesto.setText(responsableProyecto.getPuesto());

        OrganizacionVinculada organizacion = responsableProyecto.getOrganizacionVinculada();
        if (organizacion != null) {
            lblNombreOrg.setText(organizacion.getNombre());
            lblCorreoOrg.setText(organizacion.getCorreo());
            lblRFC.setText(organizacion.getRFC());
            lblTelefonoOrg.setText(organizacion.getTelefono());
            lblTipo.setText(organizacion.getTipo());
        }
    }

    @FXML
    private void clicAceptar() {
        if (responsableProyecto == null || responsableProyecto.getOrganizacionVinculada() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Datos incompletos", "No se puede guardar porque faltan datos.");
            return;
        }

        responsableProyecto.setIdOrganizacion(responsableProyecto.getOrganizacionVinculada().getIdOrganizacion());

        boolean exito = new ResponsableProyectoDAO().agregar(responsableProyecto);

        if (exito) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "El responsable de proyecto se ha registrado con éxito");
            cerrarVentana();

            if (ventanaRegistrarResponsable != null) {
                ventanaRegistrarResponsable.close();
            }

        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo registrar el responsable", "Intenta nuevamente más tarde.");
        }
    }

    @FXML
    private void clicModificar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) lblNombre.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    public void setVentanaRegistrarResponsable(Stage ventanaRegistrarResponsable) {
        this.ventanaRegistrarResponsable = ventanaRegistrarResponsable;
    }

}
