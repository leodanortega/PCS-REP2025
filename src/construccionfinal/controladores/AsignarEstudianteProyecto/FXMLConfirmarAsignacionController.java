package construccionfinal.controladores.AsignarEstudianteProyecto;

import construccionfinal.modelo.pojo.Estudiante;
import construccionfinal.modelo.pojo.Proyecto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLConfirmarAsignacionController {

    @FXML
    private Label lbEstudiante;
    @FXML
    private Label lbProyecto;

    private boolean confirmado = false;

    public void setDatos(Estudiante estudiante, Proyecto proyecto) {
        lbEstudiante.setText(estudiante.getNombre() + " " + estudiante.getApePaterno() + " " + estudiante.getApeMaterno());
        lbProyecto.setText(proyecto.getNombre());
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    @FXML
    private void clicAceptar() {
        confirmado = true;
        cerrarVentana();
    }

    @FXML
    private void clicCancelar() {
        confirmado = false;
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) lbEstudiante.getScene().getWindow();
        stage.close();
    }
}