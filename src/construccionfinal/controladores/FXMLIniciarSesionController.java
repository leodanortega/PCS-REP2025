package construccionfinal.controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import construccionfinal.dao.InicioSesionDAO;
import construccionfinal.modelo.pojo.Usuario;
import construccionfinal.utilidades.Utilidad;

public class FXMLIniciarSesionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btnClicVerificarSesion(ActionEvent event) {
        String username = tfUsuario.getText().trim();
        String password = tfPassword.getText().trim();

        if (validarCampos(username, password)) {
            Usuario usuarioSesion = validarCredenciales(username, password);
            if (usuarioSesion != null) {
                irPantallaPrincipal(usuarioSesion);
            } else {
                limpiarCampos();
            }
        }
    }

    private void limpiarCampos() {
        tfUsuario.clear();
        tfPassword.clear();
    }

    private boolean validarCampos(String username, String password){
        lbErrorUsuario.setText("");
        lbErrorPassword.setText("");

        boolean camposValidos = true;

        if(username.isEmpty()){
            lbErrorUsuario.setText("Usuario requerido");
            camposValidos = false;
        }

        if(password.isEmpty()){
            lbErrorPassword.setText("Contraseña requerida");
            camposValidos = false;
        }

        return camposValidos;
    }

    private Usuario validarCredenciales(String username, String password){
        try {
            Usuario usuarioSesion = InicioSesionDAO.verificarCredenciales(username, password);
            if (usuarioSesion != null) {
                Utilidad.mostrarAlertaSimple(
                        Alert.AlertType.INFORMATION,
                        "Credenciales correctas",
                        String.format("Bienvenido(a) %s", usuarioSesion.toString()));
                return usuarioSesion;
            } else {
                Utilidad.mostrarAlertaSimple(
                        Alert.AlertType.WARNING,
                        "Credenciales incorrectas",
                        "El usuario y/o contraseña no coinciden. Inténtelo de nuevo.");
                return null;
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.ERROR,
                    "Problema de conexión",
                    ex.getMessage());
            return null;
        }
    }

    private void irPantallaPrincipal(Usuario usuarioSesion) {
        String rutaFXML;
        String rol = usuarioSesion.getRol();

        if (rol.equalsIgnoreCase("coordinador")) {
            rutaFXML = "/construccionfinal/vistas/FXMLPrincipalCoordinador.fxml";
        } else if (rol.equalsIgnoreCase("estudiante")) {
            rutaFXML = "/construccionfinal/vistas/FXMLPrincipalEstudiante.fxml";
        } else if (rol.equalsIgnoreCase("evaluador")) {
            rutaFXML = "/construccionfinal/vistas/FXMLPrincipalEvaluador.fxml";
        } else if (rol.equalsIgnoreCase("profesor")) {
            rutaFXML = "/construccionfinal/vistas/FXMLPrincipalProfesor.fxml";
        } else {
            System.err.println("Tipo de usuario desconocido: " + rol);
            return;
        }

        abrirNuevaVentana(rutaFXML, "Pantalla Principal", usuarioSesion);
    }

    private void abrirNuevaVentana(String rutaFXML, String titulo, Usuario usuarioSesion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            Object controlador = loader.getController();
            if (controlador instanceof FXMLPrincipalCoordinadorController) {
                ((FXMLPrincipalCoordinadorController) controlador).setUsuario(usuarioSesion);
            } else if (controlador instanceof FXMLPrincipalEstudianteController) {
                ((FXMLPrincipalEstudianteController) controlador).setUsuario(usuarioSesion);
            } else if (controlador instanceof FXMLPrincipalEvaluadorController) {
                ((FXMLPrincipalEvaluadorController) controlador).setUsuario(usuarioSesion);
            } else if (controlador instanceof FXMLPrincipalProfesorController) {
                ((FXMLPrincipalProfesorController) controlador).setUsuario(usuarioSesion);
            }

            Stage stage = (Stage) tfUsuario.getScene().getWindow();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("No se pudo abrir la ventana: " + rutaFXML);
        }
    }

}
