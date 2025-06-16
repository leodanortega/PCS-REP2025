/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package construccionfinal.controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import construccionfinal.ConstruccionFinal;
import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.dao.InicioSesionDAO;
import construccionfinal.modelo.pojo.Usuario;
import construccionfinal.utilidades.Utilidad;

public class FXMLIniciarSesionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfPassword;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorPassword;
    private Usuario usuario;
    private ConexionBD ConexionBD;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void btnClicVerificarSesion(ActionEvent event) {
        String username = tfUsuario.getText();
        String password = tfPassword.getText();

        if (validarCampos(username, password)) {
            Usuario usuarioSesion = validarCredenciales(username, password);
            if (usuarioSesion != null) {
                irPantallaPrincipal(usuarioSesion);
            }
        }
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
    
    private void irPantallaPrincipal(Usuario usuarioSesion){
        try {
            Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
            FXMLLoader cargador;

            // Determina qué vista cargar según el tipo de usuario
            if (usuarioSesion.getRol() == "coordinador") { 
                cargador = new FXMLLoader(getClass().getResource("/vistas/FXMLPrincipalCoordinador.fxml"));
            } else if (usuarioSesion.getRol() == "estudiante") { 
                cargador = new FXMLLoader(getClass().getResource("/vistas/FXMLPrincipalEstudiante.fxml"));
            }else if (usuarioSesion.getRol() == "evaluador"){
                cargador = new FXMLLoader(getClass().getResource("/vistas/FXMLPrincipalEvaluador.fxml"));
            }else if (usuarioSesion.getRol() == "profesor"){
                cargador = new FXMLLoader(getClass().getResource("/vistas/FXMLPrincipalProfesor.fxml"));
            }else {
                System.err.println("Tipo de usuario desconocido.");
                return;
            }

            Parent vista = cargador.load();

            // Asigna el usuario al controlador correspondiente
            Object controlador = cargador.getController();
            if (controlador instanceof FXMLPrincipalCoordinadorController) {
            ((FXMLPrincipalCoordinadorController) controlador).setUsuario(usuarioSesion);
            } else if (controlador instanceof FXMLPrincipalEstudianteController) {
                ((FXMLPrincipalEstudianteController) controlador).setUsuario(usuarioSesion);
            } else if (controlador instanceof FXMLPrincipalEvaluadorController) {
                ((FXMLPrincipalEvaluadorController) controlador).setUsuario(usuarioSesion);
            } else if (controlador instanceof FXMLPrincipalProfesorController) {
                ((FXMLPrincipalProfesorController) controlador).setUsuario(usuarioSesion);
            }

            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Pantalla Principal");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Error al cargar la pantalla principal.");
        }
    }
}

