package construccionfinal.utilidades;

import java.io.File;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class Utilidad {
    
    public static void mostrarAlertaSimple(Alert.AlertType tipo, String titulo, String contenido){
        Alert alerta = new Alert(tipo);
                alerta.setTitle(titulo);
                alerta.setHeaderText(null);
                alerta.setContentText(contenido);
                alerta.showAndWait();
    }
    
    public static boolean mostrarAlertaConfirmacion(String titulo, String contenido){
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle(titulo);
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setContentText(contenido);
       
        return (alertaConfirmacion.showAndWait().get()) == ButtonType.OK;
    }
    public static Stage getEscenarioComponente(Control componente){
        return (Stage) componente.getScene().getWindow();
    }
    
    public static boolean estaVacio(String texto){
        return texto.isEmpty();
    }
    
    public static boolean fechaSinSeleccionar(DatePicker fechaSeleccionada){
        return fechaSeleccionada.getValue() == null;
    }
    
    public static boolean comboBoxSeleccionado(ComboBox<?> combo){
        return combo.getValue() == null;
    }
    
    public static boolean fechaInvalida(DatePicker dp){
        LocalDate hoy = LocalDate.now();
        LocalDate fechaDada = dp.getValue();
        LocalDate hace100Anios = hoy.minusYears(100);
        
        boolean esInvalida = false;
        
        if(fechaDada.isAfter(hoy) || fechaDada.isBefore(hace100Anios))
            esInvalida = true;
        
        return esInvalida;
        
    }
    
    public static boolean sinFoto(File archivoFoto){
        return archivoFoto == null;
    }
}