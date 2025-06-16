package construccionfinal.controladores;

import java.io.IOException;

import construccionfinal.modelo.pojo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLPrincipalCoordinadorController {

    @FXML
    private Label lblNombreUsuario;
    private Usuario usuario;

    void setUsuario(Usuario usuarioSesion) {
        this.usuario=usuario;
        if (usuario != null && usuario.getNombre() != null) {
            lblNombreUsuario.setText("Usuario: " + usuario.getNombre());
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

    public void btnRegistrarResponsable(ActionEvent actionEvent) {
        abrirNuevaVentana("/construccionfinal/vistas/RegistrarResponsable/FXMLRegistrarResponsable.fxml", "Registrar Responsable de proyecto");
    }
}
