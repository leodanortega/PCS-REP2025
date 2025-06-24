package construccionfinal.controladores.RegistrarProyecto;

import construccionfinal.dao.OrganizacionVinculadaDAO;
import construccionfinal.dao.ProyectoDAO;
import construccionfinal.dao.ResponsableProyectoDAO;
import construccionfinal.modelo.pojo.OrganizacionVinculada;
import construccionfinal.modelo.pojo.Proyecto;
import construccionfinal.modelo.pojo.ResponsableProyecto;

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

    @FXML private ComboBox<OrganizacionVinculada> cbOrganizaciones;
    @FXML private ComboBox<ResponsableProyecto> cbResponsables;
    @FXML private TextField tfNombre;
    @FXML private TextField tfDepartamento;
    @FXML private TextArea taDescripcion;
    @FXML private TextField tfMetodologia;
    @FXML private TextField tfEspacios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarOrganizaciones();
        cargarResponsables();
    }

    private void cargarOrganizaciones() {
        List<OrganizacionVinculada> lista = new OrganizacionVinculadaDAO().listar();
        cbOrganizaciones.setItems(FXCollections.observableArrayList(lista));

        cbOrganizaciones.setOnAction(event -> {
            OrganizacionVinculada seleccionada = cbOrganizaciones.getValue();
            if (seleccionada != null) {
                cargarResponsablesPorOrganizacion(seleccionada.getIdOrganizacion());
            } else {
                cbResponsables.getItems().clear();
            }
        });
    }

    private void cargarResponsablesPorOrganizacion(int idOrganizacion) {
        List<ResponsableProyecto> lista = new ResponsableProyectoDAO().listarPorOrganizacion(idOrganizacion);
        cbResponsables.setItems(FXCollections.observableArrayList(lista));
    }
    
    private void cargarResponsables() {
        List<ResponsableProyecto> lista = new ResponsableProyectoDAO().listar();
        cbResponsables.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        if (confirmarAccion("¿Seguro que quieres cancelar?")) {
            ((Stage) tfNombre.getScene().getWindow()).close();
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        if (!validarCampos()) return;

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(tfNombre.getText().trim());
        proyecto.setDescripcion(taDescripcion.getText().trim());
        proyecto.setMetodologia(tfMetodologia.getText().trim());
        proyecto.setEspacios(tfEspacios.getText().trim());
        proyecto.setDepartamento(tfDepartamento.getText().trim());

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
                boolean exito = new ProyectoDAO().agregar(proyecto);
                mostrarAlerta(exito ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                    exito ? "Registro exitoso" : "Error",
                    exito ? "El proyecto fue registrado exitosamente." : "No se pudo registrar el proyecto.");
                if (exito) limpiarCampos();
            }

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error de carga", "No se pudo cargar la ventana de confirmación.");
        }
    }

    private boolean validarCampos() {
        String nombre = tfNombre.getText().trim();
        String descripcion = taDescripcion.getText().trim();
        String metodologia = tfMetodologia.getText().trim();
        String espaciosStr = tfEspacios.getText().trim();
        String departamento = tfDepartamento.getText().trim();
        OrganizacionVinculada organizacion = cbOrganizaciones.getValue();
        ResponsableProyecto responsable = cbResponsables.getValue();
        
        if (nombre.isEmpty() && descripcion.isEmpty() && metodologia.isEmpty() &&
            espaciosStr.isEmpty() && departamento.isEmpty() &&
            organizacion == null && responsable == null) {

            mostrarAlerta(Alert.AlertType.WARNING, "Sin datos", "No has ingresado ningún dato requerido.");
            return false;
        }
        
        if (organizacion == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Organización requerida", "Debes seleccionar una organización vinculada.");
            return false;
        }

        if (responsable == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Responsable requerido", "Debes seleccionar un responsable de proyecto.");
            return false;
        }

        if (nombre.isEmpty() || nombre.length() < 3 || nombre.length() > 100) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nombre inválido", "El nombre debe tener entre 3 y 100 caracteres.");
            return false;
        }

        if (!nombre.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ0-9 \\-()]+")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nombre inválido", "El nombre contiene caracteres no permitidos.");
            return false;
        }

        if (ProyectoDAO.existeNombreProyecto(nombre)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nombre duplicado", "Ya existe un proyecto con ese nombre.");
            return false;
        }

        if (departamento.isEmpty() || departamento.length() > 100) {
            mostrarAlerta(Alert.AlertType.WARNING, "Departamento inválido", "Debe tener entre 1 y 100 caracteres.");
            return false;
        }
        
        if (descripcion.isEmpty() || descripcion.length() < 10 || descripcion.length() > 500) {
            mostrarAlerta(Alert.AlertType.WARNING, "Descripción inválida", "Debe tener entre 10 y 500 caracteres.");
            return false;
        }

        if (metodologia.isEmpty() || metodologia.length() > 100) {
            mostrarAlerta(Alert.AlertType.WARNING, "Metodología inválida", "Debe estar entre 1 y 100 caracteres.");
            return false;
        }

        if(espaciosStr.isEmpty()){
            mostrarAlerta(Alert.AlertType.WARNING, "Espacio vacio", "Debe ingresar un dígito");
            return false;
        }
    
        if (!espaciosStr.matches("\\d+")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Espacio inválido", "Debes ingresar solo números enteros.");
            return false;
        }

        int espacios = Integer.parseInt(espaciosStr);
        if (espacios < 1 || espacios > 100) {
            mostrarAlerta(Alert.AlertType.WARNING, "Espacio fuera de rango", "Debe estar entre 1 y 100.");
            return false;
        }

        if (!departamento.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ0-9 \\-()]+")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Departamento inválido", "El nombre del departamento contiene caracteres no permitidos.");
            return false;
        }
        return true;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    private boolean confirmarAccion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        return alerta.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
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