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

/**
 * FXML Controller class
 *
 * @author lizbello
 */
public class FXMLIniciarSesionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfPassword;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                
    }    

    @FXML
    private void btnClicVerificarSesion(ActionEvent event) {
        String username = tfUsuario.getText();
        String password = tfPassword.getText();
        
        if(validarCampos(username, password))
            validarCredenciales(username, password);
        
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
    
    private void validarCredenciales(String username, String password){
        
        try{
            Usuario usuarioSesion = InicioSesionDAO.verificarCredenciales(username, password);
            
            if(usuarioSesion != null){
                //TODO flujo normal
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Credenciales correctas", "Bienvenid@ " + usuarioSesion.toString() + " al sistema");
                irPantallaPrincipal(usuarioSesion);
            } else{
                //TODO flujo alterno 1
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Credenciales incorrectas", "Usuario y/o contraseña incorrectos, por favor verifica tu informacion");
            }
        } catch (SQLException ex) {
            //TODO EXcepcion 1
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Problemas de conexión", ex.getMessage());
            
        }
    }
    
    private void irPantallaPrincipal(Usuario usuarioSesion){
        try {
            Stage escenarioBase = (Stage) tfPassword.getScene().getWindow();
            //Parent vista = FXMLLoader.load(JavaFXAppEscolar.class.getResource("vista/FXMLPrincipal.fxml"));
            FXMLLoader cargador = new FXMLLoader(ConstruccionFinal.class.getResource("vistas/FXMLPrincipalCoordinador.fxml"));
            Parent vista = cargador.load();
            FXMLPrincipalCoordinadorController controlador = cargador.getController();
            controlador.inicializarInformacion(usuarioSesion);
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Home");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
}

