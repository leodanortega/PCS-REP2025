package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.Estudiante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstudianteDAO {

    /**
     * Busca un estudiante por su ID de usuario.
     */
    public static Estudiante buscarPorId(int idUsuario) {
        Estudiante estudiante = null;
        String sql = "SELECT idUsuario, nombre, apePaterno, apeMaterno, identificador, correo, telefono " +
                "FROM usuario WHERE idUsuario = ? AND rol = 'estudiante'";

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
            System.err.println("Error al buscar estudiante por ID: " + e.getMessage());
        }

        return estudiante;
    }

    /**
     * Obtiene todos los estudiantes de la base de datos.
     */
    public static ObservableList<Estudiante> listar() {
        ObservableList<Estudiante> lista = FXCollections.observableArrayList();
        String sql = "SELECT idUsuario, nombre, apePaterno, apeMaterno, identificador, correo, telefono " +
                "FROM usuario WHERE rol = 'estudiante'";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setIdUsuario(rs.getInt("idUsuario"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApePaterno(rs.getString("apePaterno"));
                estudiante.setApeMaterno(rs.getString("apeMaterno"));
                estudiante.setIdentificador(rs.getString("identificador"));
                estudiante.setCorreo(rs.getString("correo"));
                estudiante.setTelefono(rs.getString("telefono"));

                lista.add(estudiante);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar estudiantes: " + e.getMessage());
        }

        return lista;
    }


    public static ObservableList<Estudiante> buscarPorNombre(String filtro) {
        ObservableList<Estudiante> lista = FXCollections.observableArrayList();
        String sql = "SELECT idUsuario, nombre, apePaterno, apeMaterno, identificador, correo, telefono " +
                "FROM usuario WHERE rol = 'estudiante' AND " +
                "(nombre LIKE ? OR apePaterno LIKE ? OR apeMaterno LIKE ? OR identificador LIKE ?)";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String parametroBusqueda = "%" + filtro + "%";
            ps.setString(1, parametroBusqueda);
            ps.setString(2, parametroBusqueda);
            ps.setString(3, parametroBusqueda);
            ps.setString(4, parametroBusqueda);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdUsuario(rs.getInt("idUsuario"));
                    estudiante.setNombre(rs.getString("nombre"));
                    estudiante.setApePaterno(rs.getString("apePaterno"));
                    estudiante.setApeMaterno(rs.getString("apeMaterno"));
                    estudiante.setIdentificador(rs.getString("identificador"));
                    estudiante.setCorreo(rs.getString("correo"));
                    estudiante.setTelefono(rs.getString("telefono"));

                    lista.add(estudiante);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar estudiantes por nombre o matrícula: " + e.getMessage());
        }

        return lista;
    }
}