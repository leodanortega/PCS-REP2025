package construccionfinal.controladores;

import construccionfinal.controladores.RegistrarOV.*;
import construccionfinal.dao.OrganizacionVinculadaDAO;
import construccionfinal.modelo.pojo.OrganizacionVinculada;
import construccionfinal.utilidades.Utilidad;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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

            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Existen campos inválidos, por favor corregir");
            return;
        }

        // Crear objeto con los datos actuales
        OrganizacionVinculada nueva = new OrganizacionVinculada();
        nueva.setNombre(tfNombre.getText());
        nueva.setCorreo(tfCorreo.getText());
        nueva.setDescripcion(tfDescripcion.getText());
        nueva.setRFC(tfRFC.getText());
        nueva.setTelefono(tfTelefono.getText());
        nueva.setTipo(cbTipo.getValue());

        try {
            // Validar conexión a base de datos antes de continuar
            if (!OrganizacionVinculadaDAO.hayConexion()) {
                mostrarAlerta(Alert.AlertType.ERROR, "Sin conexión", "Error: No hay conexión con la base de datos");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/construccionfinal/vistas/RegistrarOV/FXMLConfirmacionDatos.fxml"));
            Parent root = loader.load();

            FXMLConfirmacionDatosController controller = loader.getController();
            controller.setOrganizacion(nueva);

            Stage stage = new Stage();
            stage.setTitle("Confirmar Organización");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            if (controller.isConfirmado()) {
                boolean exito = new OrganizacionVinculadaDAO().agregar(nueva);
                if (exito) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "La organización Vinculada se registró con éxito");
                    limpiarCampos();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar la organización.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de confirmación.");
        }
    }



    @FXML
    private void clicSalir(ActionEvent event) {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        Utilidad.mostrarAlertaConfirmacion("Salir", "¿Seguro que quieres salir?");
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
