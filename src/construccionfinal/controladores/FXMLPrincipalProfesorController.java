package construccionfinal.controladores;

import construccionfinal.modelo.pojo.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class FXMLPrincipalProfesorController implements Initializable {

    private Usuario usuario;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void setUsuario(Usuario usuarioSesion) {
        this.usuario=usuario;
    }
    
}
