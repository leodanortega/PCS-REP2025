package construccionfinal.controladores;

import java.io.IOException;

import construccionfinal.modelo.pojo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLPrincipalCoordinadorController {

    @FXML
    private Label lblNombreUsuario;
    private Usuario usuario;
    @FXML
    private Button btnRegistrarOrganizacion;
    @FXML
    private Button btnRegistrarResponsable;
    @FXML
    private Button btnRegistrarProyecto;
    @FXML
    private Button btnAsignar;
    @FXML
    private Button btnEstudiantes;

    void setUsuario(Usuario usuarioSesion) {
        this.usuario=usuario;
        if (usuario != null && usuario.getNombre() != null) {
            lblNombreUsuario.setText(usuario.getNombre());
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

            // Cierra la ventana actual
            Stage actual = (Stage) lblNombreUsuario.getScene().getWindow();
            actual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void btnRegistrarOrganizacion(ActionEvent event) {
        abrirNuevaVentana("/construccionfinal/vistas/RegistrarOV/FXMLRegistrarOV.fxml", "Registrar Organización Vinculada");
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

    @FXML
    public void btnRegistrarResponsable(ActionEvent actionEvent) {
        abrirNuevaVentana("/construccionfinal/vistas/RegistrarResponsable/FXMLRegistrarResponsable.fxml", "Registrar Responsable de proyecto");
    }

    @FXML
    private void clicRegistrarProyecto(ActionEvent actionEvent) {
        abrirNuevaVentana("/construccionfinal/vistas/RegistrarProyecto/FXMLFormularioProyecto.fxml", "Registrar Proyecto");
    }

    @FXML
    private void clicAsignar(ActionEvent event) {
        abrirNuevaVentana("/construccionfinal/vistas/AsignarEstudianteProyecto/FXMLAsignarEstudianteProyecto.fxml", "Asignar Estudiante a Proyecto");
    }

    @FXML
    private void clicEstudiantes(ActionEvent event) {
        abrirNuevaVentana("/construccionfinal/vistas/ExpedienteEstudiante/FXMLListaEstudiantes.fxml", "Estudiantes");
    }
}
