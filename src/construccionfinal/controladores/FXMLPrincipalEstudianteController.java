package construccionfinal.controladores;

import construccionfinal.controladores.EvaluarOV.FXMLEvaluarOVController;
import construccionfinal.controladores.Expediente.FXMLExpedienteEstudianteController;
import construccionfinal.dao.*;
import construccionfinal.modelo.pojo.*;
import construccionfinal.utilidades.Utilidad;
import construccionfinal.dao.ProyectoDAO;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLPrincipalEstudianteController implements Initializable {

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private Button btnEvaluarOV;

    @FXML
    private Button btnExpediente;

    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // No se requiere inicialización aquí por ahora
    }

    public void setUsuario(Usuario usuarioSesion) {
        this.usuario = usuarioSesion;
        lblNombreUsuario.setText(usuario.getNombre());
    }

    @FXML
    public void btnEvaluarOV(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/construccionfinal/vistas/EvaluarOV/FXMLEvaluarOV.fxml"));
            Parent root = loader.load();

            FXMLEvaluarOVController controller = loader.getController();

            EstudianteDAO estudianteDAO = new EstudianteDAO();
            Estudiante estudiante = estudianteDAO.buscarPorId(usuario.getIdUsuario());

            if (estudiante == null) {
                System.err.println("No se encontró estudiante para el usuario.");
                return;
            }

            ExpedienteDAO expedienteDAO = new ExpedienteDAO();
            Expediente expediente = expedienteDAO.obtenerExpedientePorEstudiante(estudiante.getIdUsuario());

            if (expediente == null) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Aún no puedes realizar esta operación", "No tienes asignado un proyecto.");
                return;
            }

            int idExpediente = expediente.getIdExpediente();
            int horas = ExpedienteDAO.obtenerHorasPorExpediente(idExpediente);

            if (horas < 420) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Aún no puedes realizar esta operación", "No cumples 420 horas como mínimo para poder evaluar la organización.");
                return;
            }

            if (EvaluacionOVDAO.tieneEvaluacionOrganizacion(idExpediente)) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Evaluación ya realizada", "Ya realizaste la evaluación.");
                return;
            }

            ProyectoDAO proyectoDAO = new ProyectoDAO();
            Proyecto proyecto = proyectoDAO.buscarPorId(expediente.getIdProyecto());

            if (proyecto == null) {
                System.err.println("No se encontró el proyecto con ID: " + expediente.getIdProyecto());
                return;
            }

            OrganizacionVinculadaDAO orgDAO = new OrganizacionVinculadaDAO();
            OrganizacionVinculada organizacion = orgDAO.buscarPorId(proyecto.getIdOrganizacion());

            ResponsableProyectoDAO responsableDAO = new ResponsableProyectoDAO();
            ResponsableProyecto responsable = responsableDAO.buscarPorId(proyecto.getIdResponsable());

            List<CriterioEvaluacion> criterios = CriterioEvaluacionOVDAO.obtenerTodos();

            controller.setDatos(estudiante, proyecto, organizacion, responsable, criterios);

            Stage stage = new Stage();
            stage.setTitle("Evaluar Organización Vinculada");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("No se pudo abrir la ventana FXMLEvaluarOV.fxml");
        }
    }

    @FXML
    private void btnCerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/construccionfinal/vistas/FXMLIniciarSesion.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Inicio de Sesión");
            stage.setScene(new Scene(root));
            stage.show();

            Stage actual = (Stage) lblNombreUsuario.getScene().getWindow();
            actual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirNuevaVentanaConEstudiante(Estudiante estudiante) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/construccionfinal/vistas/Expediente/FXMLExpedienteEstudiante.fxml"));
            Parent root = loader.load();

            FXMLExpedienteEstudianteController controller = loader.getController();
            controller.inicializarDatos(estudiante);

            Stage stage = new Stage();
            stage.setTitle("Expediente del Estudiante");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clicExpediente(ActionEvent event) {
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        ExpedienteDAO expedienteDAO = new ExpedienteDAO();

        Estudiante estudiante = estudianteDAO.buscarPorId(usuario.getIdUsuario());

        if (estudiante == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Error", "No se encontró información del estudiante.");
            return;
        }

        Expediente expediente = expedienteDAO.obtenerExpedientePorEstudiante(estudiante.getIdUsuario());

        if (expediente == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Aún no puedes realizar esta operación", "Aún no puedes realizar esta operación.");
            return;
        }

        abrirNuevaVentanaConEstudiante(estudiante);
    }
}
