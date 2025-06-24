package construccionfinal.controladores.RegistrarOV;

import construccionfinal.modelo.pojo.OrganizacionVinculada;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class FXMLConfirmacionDatosController {
    private boolean confirmado = false;

    @FXML private Label lblNombre;
    @FXML private Label lblCorreo;
    @FXML private Label lblDescripcion;
    @FXML private Label lblRFC;
    @FXML private Label lblTelefono;
    @FXML private Label lblTipo;

    private OrganizacionVinculada organizacion;

    public void setOrganizacion(OrganizacionVinculada org) {
        this.organizacion = org;
        lblNombre.setText(org.getNombre());
        lblCorreo.setText(org.getCorreo());
        lblDescripcion.setText(org.getDescripcion());
        lblDescripcion.setWrapText(true);
        lblRFC.setText(org.getRFC());
        lblTelefono.setText(org.getTelefono());
        lblTipo.setText(org.getTipo());
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    @FXML
    private void clicAceptar(ActionEvent event) {
        confirmado = true;
        cerrarVentana();
    }

    @FXML
    private void clicModificar(ActionEvent event) {
        confirmado = false;
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) lblNombre.getScene().getWindow();
        stage.close();
    }
}
