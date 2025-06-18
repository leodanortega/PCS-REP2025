package construccionfinal.controladores;

import construccionfinal.controladores.EvaluarOV.FXMLEvaluarOVController;
import construccionfinal.controladores.Expediente.FXMLExpedienteEstudianteController;
import construccionfinal.dao.*;
import construccionfinal.modelo.pojo.*;
import construccionfinal.utilidades.Utilidad;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import construccionfinal.utilidades.Utilidad;
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

    private Usuario usuario;
    @FXML
    private Button btnEvaluarOV;
    @FXML
    private Button btnExpediente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

            ExpedienteDAO expedienteDAO= new ExpedienteDAO();
            Expediente expediente = expedienteDAO.obtenerExpedientePorEstudiante(estudiante.getIdUsuario());

            if (expediente == null) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Aún no puedes realizar esta operación", "Aún no puedes realizar esta operación");
                return;
            }

            int idExpediente = expediente.getIdExpediente();

            if (EvaluacionOVDAO.tieneEvaluacionOrganizacion(idExpediente)) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Evaluación ya realizada", "Ya realizaste la evaluación");
                return;
            }

            ProyectoDAO proyectoDAO = new ProyectoDAO();
            Proyecto proyecto = proyectoDAO.buscarPorEstudiante(estudiante.getIdUsuario());

            OrganizacionVinculadaDAO orgDAO = new OrganizacionVinculadaDAO();
            OrganizacionVinculada org = orgDAO.buscarPorId(proyecto.getIdOrganizacion());

            ResponsableProyectoDAO responsableDAO = new ResponsableProyectoDAO();
            ResponsableProyecto responsable = responsableDAO.buscarPorId(proyecto.getIdResponsable());
            List<CriterioEvaluacion> criterios = CriterioEvaluacionOVDAO.obtenerTodos();
            controller.setDatos(estudiante, proyecto, org, responsable, criterios);

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
    
    private void abrirNuevaVentana(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("No se pudo abrir la ventana: " + rutaFXML);
        }
    }

    private void abrirNuevaVentanaConEstudiante(Estudiante estudiante) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/construccionfinal/vistas/Expediente/FXMLExpedienteEstudiante.fxml"));
        Parent root = loader.load();

        // Aquí se pasa el estudiante al nuevo controlador
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
    
    /*@FXML
    private void clicExpediente(ActionEvent event) {
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        Estudiante estudiante = estudianteDAO.buscarPorId(usuario.getIdUsuario());

        if (estudiante != null) {
        abrirNuevaVentanaConEstudiante(estudiante);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se encontro al estudiante");
        }*/

    @FXML
    private void clicExpediente(ActionEvent event) {
        ExpedienteDAO expedienteDAO= new ExpedienteDAO();
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        Estudiante estudiante = estudianteDAO.buscarPorId(usuario.getIdUsuario());
        Expediente expediente = expedienteDAO.obtenerExpedientePorEstudiante(estudiante.getIdUsuario());

        if (expediente == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Aún no puedes realizar esta operación", "Aún no puedes realizar esta operación");
            return;
        }
        abrirNuevaVentana("/construccionfinal/vistas/Expediente/FXMLExpedienteEstudiante.fxml", "Expendiente");
    }
}
