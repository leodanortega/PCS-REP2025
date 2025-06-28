package construccionfinal.controladores.RegistrarProyecto;

import construccionfinal.modelo.pojo.Proyecto;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class FXMLConfirmarDatosProyectoController {

    @FXML
    private Label lbOrganizacionVinculada;
    @FXML
    private Label lbResponsableProyecto;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbDepartamento;
    @FXML
    private Label lbMetodologia;
    @FXML
    private Label lbEspacios;

    private boolean confirmado = false;
    private Proyecto proyecto;

    @FXML
    private Label lbDescripcion;

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;

        Platform.runLater(() -> {
            if (proyecto != null && lbNombre != null) {
                lbOrganizacionVinculada.setText(proyecto.getOrganizacionVinculada().getNombre());
                lbResponsableProyecto.setText(
                    proyecto.getResponsableProyecto().getNombre() + " " +
                    proyecto.getResponsableProyecto().getApePaterno() + " " +
                    proyecto.getResponsableProyecto().getApeMaterno()
                );
                lbNombre.setText(proyecto.getNombre());
                lbDepartamento.setText(proyecto.getDepartamento());
                lbDescripcion.setText(proyecto.getDescripcion());
                lbMetodologia.setText(proyecto.getMetodologia());
                lbEspacios.setText(proyecto.getEspacios());
            } else {
                System.err.println("Error: Proyecto o etiquetas no disponibles para asignar datos.");
            }
        });
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        confirmado = false;
        cerrarVentana();
    }

    @FXML
    private void clicAceptar(ActionEvent event) {
        confirmado = true;
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) lbNombre.getScene().getWindow();
        if (stage != null) {
            stage.close();
        } else {
            System.err.println("Error: no se pudo cerrar la ventana");
        }
    }
}
