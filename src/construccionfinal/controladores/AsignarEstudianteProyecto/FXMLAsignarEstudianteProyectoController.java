package construccionfinal.controladores.AsignarEstudianteProyecto;

import construccionfinal.dao.EstudianteDAO;
import construccionfinal.dao.ExpedienteDAO;
import construccionfinal.dao.ProyectoDAO;
import construccionfinal.modelo.pojo.Estudiante;
import construccionfinal.modelo.pojo.Proyecto;
import construccionfinal.utilidades.Utilidad;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLAsignarEstudianteProyectoController implements Initializable {

    @FXML private TableView<Estudiante> tvEstudiantes;
    @FXML private TableColumn<Estudiante, String> tcMatricula;
    @FXML private TableColumn<Estudiante, String> tcNombre;

    @FXML private TableView<Proyecto> tvProyectos;
    @FXML private TableColumn<Proyecto, String> tcProyecto;
    @FXML private TableColumn<Proyecto, String> tcResponsable;
    @FXML private TableColumn<Proyecto, String> tcOrganizacion;
    @FXML private TableColumn<Proyecto, String> tcEspacios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEstudiantes();
        cargarProyectos();
    }

    private void cargarEstudiantes() {
        ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList(
            EstudianteDAO.listarEstudiantesNoAsignados()
        );

        tcMatricula.setCellValueFactory(new PropertyValueFactory<>("identificador"));

        tcNombre.setCellValueFactory(cellData -> {
            Estudiante e = cellData.getValue();
            String nombreCompleto = e.getNombre() + " " + e.getApePaterno() + " " + e.getApeMaterno();
            return new SimpleStringProperty(nombreCompleto);
        });

        tvEstudiantes.setItems(listaEstudiantes);
    }

    private void cargarProyectos() {
        ProyectoDAO dao = new ProyectoDAO();
        List<Proyecto> todos = dao.listar();

        List<Proyecto> disponibles = new ArrayList<>();
        for (Proyecto proyecto : todos) {
            try {
                int espacios = Integer.parseInt(proyecto.getEspacios());
                if (espacios > 0) {
                    disponibles.add(proyecto);
                }
            } catch (NumberFormatException e) {
            }
        }

        ObservableList<Proyecto> listaProyectos = FXCollections.observableArrayList(disponibles);

        tcProyecto.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        tcResponsable.setCellValueFactory(cellData -> {
            Proyecto p = cellData.getValue();
            String nombreResp = p.getResponsableProyecto().getNombre() + " " +
                               p.getResponsableProyecto().getApePaterno() + " " +
                               p.getResponsableProyecto().getApeMaterno();
            return new SimpleStringProperty(nombreResp);
        });

        tcOrganizacion.setCellValueFactory(cellData ->
            new SimpleStringProperty(proyectoNombreSeguro(cellData.getValue()))
        );

        tcEspacios.setCellValueFactory(new PropertyValueFactory<>("espacios"));

        tvProyectos.setItems(listaProyectos);
    }

    private String proyectoNombreSeguro(Proyecto proyecto) {
        return (proyecto.getOrganizacionVinculada() != null)
            ? proyecto.getOrganizacionVinculada().getNombre()
            : "Sin organización";
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        Stage stage = (Stage) tvEstudiantes.getScene().getWindow();
        if (Utilidad.mostrarAlertaConfirmacion("Cancelar", "¿Seguro que quieres cancelar?")) {
            stage.close();
        }
    }

    @FXML
    private void clicAsignar(ActionEvent event) {
        Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();
        Proyecto proyectoSeleccionado = tvProyectos.getSelectionModel().getSelectedItem();

        if (estudianteSeleccionado == null || proyectoSeleccionado == null) {
            mostrarAlerta("Debe seleccionar un estudiante y un proyecto.");
            return;
        }
        ExpedienteDAO daoExpediente = new ExpedienteDAO();
        int nuevoExpedienteId = daoExpediente.crearExpediente(
            estudianteSeleccionado.getIdUsuario(),
            12345,
            1,
            "0",
            "0",
            "",
            1,
            proyectoSeleccionado.getIdProyecto()
        );

        if (nuevoExpedienteId != -1) {
            // Restar espacio del proyecto
            new ProyectoDAO().restarEspacio(proyectoSeleccionado.getIdProyecto());

            mostrarAlerta("Estudiante asignado y expediente creado correctamente.");
            cargarProyectos();
            tvEstudiantes.getItems().remove(estudianteSeleccionado);
        } else {
            mostrarAlerta("No se pudo asignar el estudiante al proyecto.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Aviso");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}