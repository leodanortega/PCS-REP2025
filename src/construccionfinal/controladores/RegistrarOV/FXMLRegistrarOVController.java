package construccionfinal.controladores.RegistrarOV;

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
        if (tfNombre.getText().isEmpty() || tfCorreo.getText().isEmpty() ||
                tfDescripcion.getText().isEmpty() || tfRFC.getText().isEmpty() ||
                tfTelefono.getText().isEmpty() || cbTipo.getValue() == null) {

            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Existen campos inválidos, por favor corregir");
            return;
        }

        if (!tfNombre.getText().matches("[A-Za-zÁÉÍÓÚáéíóúñÑ \\-()]+")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nombre inválido", "El nombre contiene caracteres no permitidos.");
            return;
        }

        if (tfNombre.getText().trim().length() < 3){
            mostrarAlerta(Alert.AlertType.WARNING, "Nombre inválido", "El nombre debe tener al menos 3 letras");
            return;
        }

        if (!tfCorreo.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Correo inválido", "El correo electrónico no tiene un formato válido.");
            return;
        }

        if (tfDescripcion.getText().trim().length() < 10 || tfDescripcion.getText().trim().length() > 30){
            mostrarAlerta(Alert.AlertType.WARNING, "Descripción no válida", "La descripción debe tener de 10 a 30 caracteres");
            return;
        }


        if (!tfRFC.getText().matches("^[A-ZÑ&]{3}[0-9]{6}[A-Z0-9]{3}$")) {
            mostrarAlerta(Alert.AlertType.WARNING, "RFC inválido", "El RFC no tiene un formato válido.");
            return;
        }
        if (!tfTelefono.getText().matches("\\d{10}")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Teléfono inválido", "El número de teléfono debe contener exactamente 10 dígitos numéricos.");
            return;
        }
        if (!esEnteroPositivo(tfTelefono.getText())) {
            mostrarAlerta(Alert.AlertType.WARNING, "Valor inválido", "El número de teléfono debe contener exactamente 10 dígitos numéricos.");
            return;
        }

        if (!tfCorreo.getText().matches("[A-Za-zÁÉÍÓÚáéíóúñÑ0-9 .@\\-()]+")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Correo inválido", "El correo no tiene un formato válido.");
            return;
        }

        if (!OrganizacionVinculadaDAO.hayConexion()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Sin conexión", "Error: No hay conexión con la base de datos");
            return;
        }

        OrganizacionVinculadaDAO dao = new OrganizacionVinculadaDAO();
        if (dao.existeNombre(tfRFC.getText())) {
            mostrarAlerta(Alert.AlertType.WARNING, "Organización ya registrada", "Ya existe una organización vinculada con ese RFC.");
            return;
        }

        OrganizacionVinculada nueva = new OrganizacionVinculada();
        nueva.setNombre(tfNombre.getText());
        nueva.setCorreo(tfCorreo.getText());
        nueva.setDescripcion(tfDescripcion.getText());
        nueva.setRFC(tfRFC.getText());
        nueva.setTelefono(tfTelefono.getText());
        nueva.setTipo(cbTipo.getValue());

        try {
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
                boolean exito = dao.agregar(nueva);
                if (exito) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "La organización Vinculada se registró con éxito");
                    Stage currentStage= (Stage) tfNombre.getScene().getWindow();
                    currentStage.close();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar la organización.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de confirmación.");
        }
    }

    private boolean esEnteroPositivo(String texto) {
        try {
            int valor = Integer.parseInt(texto);
            return valor > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @FXML
    private void clicSalir() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Seguro que quieres salir?");

        if (alerta.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            Stage stage = (Stage) tfNombre.getScene().getWindow();
            stage.close();
        }
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
