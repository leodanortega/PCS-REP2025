/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package construccionfinal;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author leona
 */
public class ConstruccionFinal extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try{
           Parent vista = FXMLLoader.load(getClass().getResource("vistas/FXMLIniciarSesion.fxml"));
           Scene escenaInicioSesion = new Scene(vista);
           
           primaryStage.setScene(escenaInicioSesion);
           primaryStage.setTitle("Inicio de sesion");
           primaryStage.show();
           
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
