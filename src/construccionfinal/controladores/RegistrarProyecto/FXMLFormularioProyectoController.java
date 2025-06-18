package construccionfinal.controladores.RegistrarProyecto;

import construccionfinal.dao.OrganizacionVinculadaDAO;
import construccionfinal.dao.ProyectoDAO;
import construccionfinal.dao.ResponsableProyectoDAO;
import construccionfinal.modelo.pojo.OrganizacionVinculada;
import construccionfinal.modelo.pojo.Proyecto;
import construccionfinal.modelo.pojo.ResponsableProyecto;
import construccionfinal.utilidades.Utilidad;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLFormularioProyectoController implements Initializable {

    @FXML
    private ComboBox<OrganizacionVinculada> cbOrganizaciones;
    @FXML
    private ComboBox<ResponsableProyecto> cbResponsables;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDepartamento;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private TextField tfMetodologia;
    @FXML
    private TextField tfEspacios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarOrganizaciones();
        cargarResponsables();
    }

    private void cargarOrganizaciones() {
        OrganizacionVinculadaDAO dao = new OrganizacionVinculadaDAO();
        List<OrganizacionVinculada> lista = dao.listar();
        cbOrganizaciones.setItems(FXCollections.observableArrayList(lista));
    }

    private void cargarResponsables() {
        ResponsableProyectoDAO dao = new ResponsableProyectoDAO();
        List<ResponsableProyecto> lista = dao.listar();
        cbResponsables.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Seguro que quieres cancelar?");

        if (alerta.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            Stage stage = (Stage) tfNombre.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        if (tfNombre.getText().isEmpty() || taDescripcion.getText().isEmpty() ||
            tfMetodologia.getText().isEmpty() || tfEspacios.getText().isEmpty() ||
            tfDepartamento.getText().isEmpty() || cbOrganizaciones.getValue() == null ||
            cbResponsables.getValue() == null) {

            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor completa todos los campos requeridos.");
            return;
        }
        try {
            int valor = Integer.parseInt(tfEspacios.getText());
            if (valor < 1 || valor > 100) {
                mostrarAlerta(Alert.AlertType.WARNING, "Valor fuera de rango", "El número debe estar entre 1 y 100.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Entrada inválida", "Debes ingresar un número válido entre 1 y 100.");
            return;
        }


        if (!ProyectoDAO.hayConexion()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Sin conexión", "No hay conexión con la base de datos.");
            return;
        }

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(tfNombre.getText());
        proyecto.setDescripcion(taDescripcion.getText());
        proyecto.setMetodologia(tfMetodologia.getText());
        proyecto.setEspacios(tfEspacios.getText());
        proyecto.setDepartamento(tfDepartamento.getText());

        OrganizacionVinculada ov = cbOrganizaciones.getValue();
        ResponsableProyecto responsable = cbResponsables.getValue();

        proyecto.setIdOrganizacion(ov.getIdOrganizacion());
        proyecto.setIdResponsable(responsable.getIdResponsable());

        proyecto.setOrganizacionVinculada(ov);
        proyecto.setResponsableProyecto(responsable);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/construccionfinal/vistas/RegistrarProyecto/FXMLConfirmarDatosProyecto.fxml"));
            Parent root = loader.load();

            FXMLConfirmarDatosProyectoController controller = loader.getController();
            controller.setProyecto(proyecto);

            Stage stage = new Stage();
            stage.setTitle("Confirmar Proyecto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            if (controller.isConfirmado()) {
                ProyectoDAO dao = new ProyectoDAO();
                boolean exito = dao.agregar(proyecto);

                if (exito) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "El proyecto fue registrado exitosamente.");
                    limpiarCampos();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el proyecto.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error de carga", "No se pudo cargar la ventana de confirmación.");
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
        taDescripcion.clear();
        tfMetodologia.clear();
        tfEspacios.clear();
        tfDepartamento.clear();
        cbOrganizaciones.getSelectionModel().clearSelection();
        cbResponsables.getSelectionModel().clearSelection();
    }
}