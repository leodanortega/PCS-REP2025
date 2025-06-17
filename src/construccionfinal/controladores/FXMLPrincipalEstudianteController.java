package construccionfinal.controladores;

import construccionfinal.controladores.EvaluarOV.FXMLEvaluarOVController;
import construccionfinal.dao.*;
import construccionfinal.modelo.pojo.*;

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
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLPrincipalEstudianteController implements Initializable {

    @FXML
    private Label lblNombreUsuario;

    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aquí podrías inicializar algo si es necesario
    }

    public void setUsuario(Usuario usuarioSesion) {
        this.usuario = usuarioSesion;
        lblNombreUsuario.setText(usuario.getNombre()); // Mostrar nombre del estudiante
    }

    public void btnEvaluarOV(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/construccionfinal/vistas/EvaluarOV/FXMLEvaluarOV.fxml"));
            Parent root = loader.load();

            FXMLEvaluarOVController controller = loader.getController();

            // Obtener el estudiante desde la base de datos
            EstudianteDAO estudianteDAO = new EstudianteDAO();
            Estudiante estudiante = estudianteDAO.buscarPorId(usuario.getIdUsuario());

            if (estudiante == null) {
                System.err.println("No se encontró estudiante para el usuario.");
                return;
            }

            // Obtener el proyecto real del estudiante
            ProyectoDAO proyectoDAO = new ProyectoDAO();
            Proyecto proyecto = proyectoDAO.buscarPorEstudiante(estudiante.getIdUsuario());

            OrganizacionVinculadaDAO orgDAO = new OrganizacionVinculadaDAO();
            OrganizacionVinculada org = orgDAO.buscarPorId(proyecto.getIdOrganizacion());

            ResponsableProyectoDAO responsableDAO = new ResponsableProyectoDAO();
            ResponsableProyecto responsable = responsableDAO.buscarPorId(proyecto.getIdResponsable());

            // Obtener los criterios de evaluación desde la base de datos
            List<CriterioEvaluacion> criterios = CriterioEvaluacionOVDAO.obtenerTodos();

            // Pasar los datos reales al controlador
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

            // Cerrar la ventana actual
            Stage actual = (Stage) lblNombreUsuario.getScene().getWindow();
            actual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
