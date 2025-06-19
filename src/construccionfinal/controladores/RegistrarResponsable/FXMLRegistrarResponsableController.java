package construccionfinal.controladores.RegistrarResponsable;

import construccionfinal.dao.ResponsableProyectoDAO;
import construccionfinal.modelo.pojo.OrganizacionVinculada;
import construccionfinal.modelo.pojo.ResponsableProyecto;
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

public class FXMLRegistrarResponsableController {

    @FXML private TextField tfNombre;
    @FXML private TextField tfApePaterno;
    @FXML private TextField tfApeMaterno;
    @FXML private TextField tfCorreo;
    @FXML private TextField tfTelefono;
    @FXML private TextField tfPuesto;

    private ResponsableProyecto responsableProyecto;

    @FXML
    private void initialize() {
        responsableProyecto = new ResponsableProyecto();
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        if (tfNombre.getText().isEmpty() || tfApePaterno.getText().isEmpty() ||
                tfApeMaterno.getText().isEmpty() || tfCorreo.getText().isEmpty() ||
                tfTelefono.getText().isEmpty() || tfPuesto.getText() == null) {

            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Existen campos inválidos, por favor corregir");
            return;
        }
        if (!tfNombre.getText().matches("[A-Za-zÁÉÍÓÚáéíóúñÑ \\-()]+")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nombre inválido", "El nombre contiene caracteres no permitidos.");
            return;
        }

        if (!tfApePaterno.getText().matches("[A-Za-zÁÉÍÓÚáéíóúñÑ \\-()]+")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nombre inválido", "El nombre contiene caracteres no permitidos.");
            return;
        }

        if (!tfApeMaterno.getText().matches("[A-Za-zÁÉÍÓÚáéíóúñÑ \\-()]+")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nombre inválido", "El nombre contiene caracteres no permitidos.");
            return;
        }

        if (!tfTelefono.getText().matches("\\d{10}")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Teléfono inválido", "El número de teléfono debe contener exactamente 10 dígitos numéricos.");
            return;
        }
        if (!ResponsableProyectoDAO.hayConexion()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Sin conexión", "Error: No hay conexión con la base de datos");
            return;
        }

        responsableProyecto.setNombre(tfNombre.getText());
        responsableProyecto.setApePaterno(tfApePaterno.getText());
        responsableProyecto.setApeMaterno(tfApeMaterno.getText());
        responsableProyecto.setCorreo(tfCorreo.getText());
        responsableProyecto.setTelefono(tfTelefono.getText());
        responsableProyecto.setPuesto(tfPuesto.getText());

        ResponsableProyectoDAO dao = new ResponsableProyectoDAO();
        if (dao.existeResponsable(responsableProyecto.getNombre(), responsableProyecto.getApePaterno(), responsableProyecto.getApeMaterno())) {
            mostrarAlerta(Alert.AlertType.WARNING, "Duplicado", "Ya existe un responsable con el mismo nombre y apellidos.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/construccionfinal/vistas/RegistrarResponsable/FXMLSeleccionarOrganizacionVinculada.fxml"));
            Parent root = loader.load();

            FXMLSeleccionarOrganizacionVinculadaController controller = loader.getController();
            controller.inicializarDatos(responsableProyecto);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Seleccionar Organización Vinculada");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (!controller.isFueCancelado()) {
                OrganizacionVinculada org = responsableProyecto.getOrganizacionVinculada();
                if (org != null) {
                    System.out.println("Organización seleccionada: " + org.getNombre());
                } else {
                    mostrarAlerta(Alert.AlertType.WARNING,"Seleccionar Organización Vinculada", "Debe seleccionar una organización vinculada");
                }
            }

        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR,"Error abrir ventana","No se pudo abrir la ventana de selección.");
            e.printStackTrace();
        }
    }

    @FXML
    private void clicSalir() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Seguro que quieres cancelar la operación?");

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
}
