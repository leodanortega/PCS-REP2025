/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InicioSesionDAO {
    
    public static Usuario verificarCredenciales(String identificador, String contrasenia) throws SQLException{
        Usuario usuarioSesion = null;
        
        Connection conexionBD = ConexionBD.abrirConexion();
        
        if(conexionBD != null){
           String consulta = "SELECT idUsuario, nombre, apePaterno, apeMaterno, correo, telefono, rol FROM usuario WHERE identificador = ? AND contrasenia = ?";
           PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
           
           sentencia.setString(1, identificador);
           sentencia.setString(2, contrasenia);
           
           ResultSet resultado = sentencia.executeQuery();
           
           if(resultado.next()){
               usuarioSesion = convertirRegistroUsuario(resultado);
           }
           
           conexionBD.close();
           sentencia.close();
           resultado.close();
           
        } else{
            throw new SQLException("Error: Sin conexion a la base de datos");
        }
        
        return usuarioSesion;
    }
    
    private static Usuario convertirRegistroUsuario(ResultSet resultado) throws SQLException{
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(resultado.getInt("idUsuario"));
        usuario.setNombre(resultado.getString("nombre"));
        usuario.setApeMaterno(resultado.getString("apePaterno"));
        usuario.setApeMaterno(resultado.getString("apeMaterno"));
        usuario.setCorreo(resultado.getString("correo"));
        usuario.setTelefono(resultado.getString("telefono"));
        usuario.setRol(resultado.getString("rol"));
        usuario.setIdentificador("identificador");
        
        return usuario;
        
    }
}