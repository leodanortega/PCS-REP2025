
package construccionfinal.controladores.RegistrarProyecto;

import construccionfinal.modelo.pojo.Proyecto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLConfirmarDatosProyectoController{
    
    private boolean confirmado = false;

    @FXML
    private Label lbOrganizacionVinculada;
    @FXML
    private Label lbResponsableProyecto;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbDepartamento;
    @FXML
    private Label lbDescripcion;
    @FXML
    private Label lbMetodologia;
    @FXML
    private Label lbEspacios;

     private Proyecto proyecto;
     
     public void setProyecto(Proyecto proy){
         this.proyecto = proy;
         lbOrganizacionVinculada.setText(proy.getOrganizacionVinculada().getNombre());
         lbResponsableProyecto.setText(proy.getResponsableProyecto().getNombre() + " " + proy.getResponsableProyecto().getApePaterno() + " " + proy.getResponsableProyecto().getApeMaterno());
         lbNombre.setText(proy.getNombre());
         lbDepartamento.setText(proy.getDepartamento());
         lbDescripcion.setText(proy.getDescripcion());
         lbMetodologia.setText(proy.getMetodologia());
         lbEspacios.setText(proy.getEspacios());
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
        stage.close();
    }
}
