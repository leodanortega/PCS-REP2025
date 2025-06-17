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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLPrincipalProfesorController implements Initializable {

    @FXML
    private Label lblNombreUsuario;

    private Usuario usuario;
    @FXML
    private Button btnEvaluarEstudiante;
    @FXML
    private Button btnEstudiantes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aquí podrías inicializar algo si es necesario
    }

    public void setUsuario(Usuario usuarioSesion) {
        this.usuario = usuarioSesion;
        lblNombreUsuario.setText(usuario.getNombre()); // Mostrar nombre del estudiante
    }

    @FXML
    public void btnEvaluarEstudiante(ActionEvent actionEvent) {
        abrirNuevaVentana("/construccionfinal/vistas/EvaluarEstudiante/FXMLEstudiantesPorEvaluar.fxml", "Estudiantes por evaluar");
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

    @FXML
    private void clicEstudiantes(ActionEvent event) {
        abrirNuevaVentana("/construccionfinal/vistas/Expediente/FXMLListaEstudiantes.fxml", "Estudiantes");
    }
}
