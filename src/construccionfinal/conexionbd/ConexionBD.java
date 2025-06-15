/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package construccionfinal.conexionbd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author leona
 */
public class ConexionBD {
    private static final String IP = "localhost";
    private static final String PUERTO = "3306";
    private static final String NOMBRE_BD = "construccion";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "081205";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    
    public static Connection abrirConexion(){
        Connection conexionBD = null;
        String URLConexion = String.format("jdbc:mysql://%s:%s/%s?allowPublicKeyRetrieval=true&useSSL=false", IP, PUERTO, NOMBRE_BD);
        
        try{
            Class.forName(DRIVER); 
            conexionBD = DriverManager.getConnection(URLConexion, USUARIO, PASSWORD);
        } catch (ClassNotFoundException e){
            System.err.println("Error clase no encontrada");
        } catch (SQLException s){
            System.err.println("Error en la conexión con la base de datos: " + s.getMessage());
        }
        
        return conexionBD;
    }
}
