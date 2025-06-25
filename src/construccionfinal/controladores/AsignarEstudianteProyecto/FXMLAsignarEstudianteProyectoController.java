package construccionfinal.controladores.AsignarEstudianteProyecto;

import construccionfinal.dao.EstudianteDAO;
import construccionfinal.dao.ExpedienteDAO;
import construccionfinal.dao.ProyectoDAO;
import construccionfinal.modelo.pojo.DocumentoInicial;
import construccionfinal.modelo.pojo.Estudiante;
import construccionfinal.modelo.pojo.Proyecto;
import construccionfinal.utilidades.Utilidad;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class FXMLAsignarEstudianteProyectoController implements Initializable {

    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableColumn<Estudiante, String> tcMatricula;
    @FXML
    private TableColumn<Estudiante, String> tcNombre;

    @FXML
    private TableView<Proyecto> tvProyectos;
    @FXML
    private TableColumn<Proyecto, String> tcProyecto;
    @FXML
    private TableColumn<Proyecto, String> tcResponsable;
    @FXML
    private TableColumn<Proyecto, String> tcOrganizacion;
    @FXML
    private TableColumn<Proyecto, String> tcEspacios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEstudiantes();
        cargarProyectos();
    }

    private void cargarEstudiantes() {
        ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList(EstudianteDAO.listarEstudiantesNoAsignados());

        tcMatricula.setCellValueFactory(new PropertyValueFactory<>("identificador"));

        tcNombre.setCellValueFactory(cellData -> {
            Estudiante estudiante = cellData.getValue();
            String nombreCompleto = estudiante.getNombre() + " " + estudiante.getApePaterno() + " " + estudiante.getApeMaterno();
            return new SimpleStringProperty(nombreCompleto);
        });

        tvEstudiantes.setItems(listaEstudiantes);
    }

    /*private void cargarProyectos() {
        ProyectoDAO dao = new ProyectoDAO();
        ObservableList<Proyecto> listaProyectos = FXCollections.observableArrayList(dao.listar());

        tcProyecto.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        tcResponsable.setCellValueFactory(cellData -> {
            Proyecto proyecto = cellData.getValue();
            String nombreResponsable = proyecto.getResponsableProyecto().getNombre() + " "
                                     + proyecto.getResponsableProyecto().getApePaterno() + " "
                                     + proyecto.getResponsableProyecto().getApeMaterno();
            return new SimpleStringProperty(nombreResponsable);
        });

        tcOrganizacion.setCellValueFactory(cellData -> 
            new SimpleStringProperty(proyectoNombreSeguro(cellData.getValue()))
        );

        tcEspacios.setCellValueFactory(new PropertyValueFactory<>("espacios"));

        tvProyectos.setItems(listaProyectos);
    }*/
    
private void cargarProyectos() {
    ProyectoDAO dao = new ProyectoDAO();
    List<Proyecto> todos = dao.listar();

    // Filtrar proyectos que tengan al menos un espacio disponible
    List<Proyecto> disponibles = new ArrayList<>();
    for (Proyecto proyecto : todos) {
        try {
            int espacios = Integer.parseInt(proyecto.getEspacios());
            if (espacios > 0) {
                disponibles.add(proyecto);
            }
        } catch (NumberFormatException e) {
            // Omitir proyectos con formato inválido
        }
    }

    ObservableList<Proyecto> listaProyectos = FXCollections.observableArrayList(disponibles);

    tcProyecto.setCellValueFactory(new PropertyValueFactory<>("nombre"));

    tcResponsable.setCellValueFactory(cellData -> {
        Proyecto proyecto = cellData.getValue();
        String nombreResponsable = proyecto.getResponsableProyecto().getNombre() + " "
                + proyecto.getResponsableProyecto().getApePaterno() + " "
                + proyecto.getResponsableProyecto().getApeMaterno();
        return new SimpleStringProperty(nombreResponsable);
    });

    tcOrganizacion.setCellValueFactory(cellData ->
            new SimpleStringProperty(proyectoNombreSeguro(cellData.getValue()))
    );

    tcEspacios.setCellValueFactory(new PropertyValueFactory<>("espacios"));

    tvProyectos.setItems(listaProyectos);
}

    private String proyectoNombreSeguro(Proyecto proyecto) {
        return proyecto.getOrganizacionVinculada() != null
            ? proyecto.getOrganizacionVinculada().getNombre()
            : "Sin organización";
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        Stage stage = (Stage) tvEstudiantes.getScene().getWindow();
        Utilidad.mostrarAlertaConfirmacion("Cancelar", "¿Seguro que quieres cancelar?");
        stage.close();
    }

@FXML
private void clicAsignar(ActionEvent event) {
    Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();
    Proyecto proyectoSeleccionado = tvProyectos.getSelectionModel().getSelectedItem();

    if (estudianteSeleccionado == null || proyectoSeleccionado == null) {
        mostrarAlerta("Debe seleccionar un estudiante y un proyecto.");
        return;
    }

    ProyectoDAO dao = new ProyectoDAO();
    boolean asignado = dao.asignarEstudianteAProyecto(proyectoSeleccionado.getIdProyecto(), estudianteSeleccionado.getIdUsuario());

    if (asignado) {
        boolean espacioRestado = dao.restarEspacio(proyectoSeleccionado.getIdProyecto());

        mostrarAlerta("Estudiante asignado correctamente al proyecto.");

        int nuevoExpedienteId = crearExpediente(estudianteSeleccionado);
        if (nuevoExpedienteId != -1) {
            mostrarAlerta("Expediente creado correctamente para el estudiante.");
        } else {
            mostrarAlerta("No se pudo crear el expediente.");
        }
        cargarProyectos();
        tvEstudiantes.getItems().remove(estudianteSeleccionado);
    } else {
        mostrarAlerta("No se pudo asignar el estudiante al proyecto.");
    }
}

    private int crearExpediente(Estudiante estudiante) {
        ExpedienteDAO daoExpediente = new ExpedienteDAO();
        
        int idGrupoEE = 12345;
        int idPeriodo = 1;
        String calificaciones = "0";
        String horas = "0";
        String informe = "";
        int idDocumentoInicial = 1;

        return daoExpediente.crearExpediente(estudiante.getIdUsuario(), idGrupoEE, idPeriodo, calificaciones, horas, informe, idDocumentoInicial);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Aviso");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}