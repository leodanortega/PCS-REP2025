package construccionfinal.controladores;

import construccionfinal.dao.OrganizacionVinculadaDAO;
import construccionfinal.modelo.pojo.OrganizacionVinculada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FXMLRegistrarOVController {

    @FXML private TextField tfNombre;
    @FXML private TextField tfCorreo;
    @FXML private TextField tfDescripcion;
    @FXML private TextField tfRFC;
    @FXML private TextField tfTelefono;
    @FXML private ComboBox<String> cbTipo;

    @FXML
    public void initialize() {
        cbTipo.getItems().addAll("Privada", "Pública");
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        // Validación básica
        if (tfNombre.getText().isEmpty() || tfCorreo.getText().isEmpty() ||
                tfDescripcion.getText().isEmpty() || tfRFC.getText().isEmpty() ||
                tfTelefono.getText().isEmpty() || cbTipo.getValue() == null) {

            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor complete todos los campos.");
            return;
        }

        OrganizacionVinculada nueva = new OrganizacionVinculada();
        nueva.setNombre(tfNombre.getText());
        nueva.setCorreo(tfCorreo.getText());
        nueva.setDescripcion(tfDescripcion.getText());
        nueva.setRFC(tfRFC.getText());
        nueva.setTelefono(tfTelefono.getText());
        nueva.setTipo(cbTipo.getValue());

        boolean exito = new OrganizacionVinculadaDAO().agregar(nueva);
        if (exito) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "La organización fue registrada correctamente.");
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar la organización.");
        }
    }

    @FXML
    private void clicSalir(ActionEvent event) {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        tfNombre.clear();
        tfCorreo.clear();
        tfDescripcion.clear();
        tfRFC.clear();
        tfTelefono.clear();
        cbTipo.getSelectionModel().clearSelection();
    }
}
