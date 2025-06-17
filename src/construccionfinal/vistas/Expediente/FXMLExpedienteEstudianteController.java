/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package construccionfinal.vistas.Expediente;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author marti
 */
public class FXMLExpedienteEstudianteController implements Initializable {

    @FXML
    private Label lbMatricula;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbGrupo;
    @FXML
    private Label lbPeriodo;
    @FXML
    private Label lbCalificaciones;
    @FXML
    private Label lbHoras;
    @FXML
    private TableView<?> tvDocumentosIniciales;
    @FXML
    private TableColumn<?, ?> tcNombreDI;
    @FXML
    private TableColumn<?, ?> tcEstadoDI;
    @FXML
    private TableColumn<?, ?> tcTipoDI;
    @FXML
    private TableColumn<?, ?> tcFechaDI;
    @FXML
    private TableColumn<?, ?> tcArchivoDI;
    @FXML
    private TableView<?> tvReportes;
    @FXML
    private TableColumn<?, ?> tcNombreR;
    @FXML
    private TableColumn<?, ?> tcHorasR;
    @FXML
    private TableColumn<?, ?> tcEstadoR;
    @FXML
    private TableColumn<?, ?> tcCalificacionR;
    @FXML
    private TableColumn<?, ?> tcFechaR;
    @FXML
    private TableColumn<?, ?> tcArchivoR;
    @FXML
    private TableView<?> tvDocumentosFinales;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
