package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstudianteDAO {

    public static Estudiante buscarPorId(int idUsuario) {
        Estudiante estudiante = null;
        String sql = "SELECT idUsuario, nombre, apePaterno, apeMaterno, identificador, correo, telefono FROM usuario WHERE idUsuario = ? AND rol = 'estudiante'";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    estudiante = new Estudiante();
                    estudiante.setIdUsuario(rs.getInt("idUsuario"));
                    estudiante.setNombre(rs.getString("nombre"));
                    estudiante.setApePaterno(rs.getString("apePaterno"));
                    estudiante.setApeMaterno(rs.getString("apeMaterno"));
                    estudiante.setIdentificador(rs.getString("identificador"));
                    estudiante.setCorreo(rs.getString("correo"));
                    estudiante.setTelefono(rs.getString("telefono"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estudiante;
    }

}