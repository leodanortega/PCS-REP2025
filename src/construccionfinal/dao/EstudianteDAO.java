package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.Estudiante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

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
    
    public static List<Estudiante> obtenerTodos() {
    List<Estudiante> estudiantes = new ArrayList<>();
    String sql = "SELECT * FROM usuario WHERE rol = 'estudiante'";

    try (Connection con = ConexionBD.abrirConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Estudiante estudiante = new Estudiante();
            estudiante.setIdUsuario(rs.getInt("idUsuario"));
            estudiante.setNombre(rs.getString("nombre"));
            estudiante.setApePaterno(rs.getString("apePaterno"));
            estudiante.setApeMaterno(rs.getString("apeMaterno"));
            estudiante.setCorreo(rs.getString("correo"));
            estudiante.setTelefono(rs.getString("telefono"));
            estudiante.setIdentificador(rs.getString("identificador"));
            estudiantes.add(estudiante);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return estudiantes;
}

    public static ObservableList<Estudiante> listarDatosAcademicos() {
    ObservableList<Estudiante> lista = FXCollections.observableArrayList();
        String sql = "SELECT u.idUsuario, u.nombre, u.apePaterno, u.apeMaterno, u.identificador, " +
                "e.carrera, e.creditos, e.semestre, e.estado, e.calificacion " +
                "FROM usuario u " +
                "INNER JOIN estudiante e ON u.idUsuario = e.idUsuario " +
                "INNER JOIN expediente ex ON e.idUsuario = ex.idEstudiante " +
                "WHERE u.rol = 'estudiante' " +
                "GROUP BY u.idUsuario";
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

            estudiante.setCarrera(rs.getString("carrera"));
            estudiante.setCreditos(rs.getInt("creditos"));
            estudiante.setSemestre(rs.getInt("semestre"));
            estudiante.setEstado(rs.getString("estado"));
            estudiante.setCalificacion(rs.getBigDecimal("calificacion"));

            lista.add(estudiante);
        }

    } catch (SQLException e) {
        System.err.println("Error al listar datos académicos de estudiantes: " + e.getMessage());
    }

    return lista;
}

    public static ObservableList<Estudiante> buscarPorNombre(String filtro) {
        ObservableList<Estudiante> lista = FXCollections.observableArrayList();
        String sql = "SELECT u.idUsuario, u.nombre, u.apePaterno, u.apeMaterno, u.identificador, u.correo, u.telefono " +
                "FROM usuario u " +
                "JOIN proyecto p ON u.idUsuario = p.idEstudiante " +
                "JOIN expediente e ON p.idProyecto = e.idProyecto " +
                "LEFT JOIN evaluacion ev ON e.idExpediente = ev.idExpediente " +
                "WHERE u.rol = 'estudiante' " +
                "AND ev.idEvaluacion IS NULL " +
                "AND (u.nombre LIKE ? OR u.apePaterno LIKE ? OR u.apeMaterno LIKE ? OR u.identificador LIKE ?)";

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
            System.err.println("Error al filtrar estudiantes sin evaluación: " + e.getMessage());
        }

        return lista;
    }
    
    public static List<Estudiante> listarEstudiantesNoAsignados() {
    List<Estudiante> lista = new ArrayList<>();
    Connection conexion = ConexionBD.abrirConexion();

    if (conexion != null) {
        try {
            String consulta = "SELECT u.idUsuario, u.nombre, u.apePaterno, u.apeMaterno, u.correo, u.telefono, u.identificador " +
                              "FROM usuario u " +
                              "WHERE u.rol = 'estudiante' AND u.idUsuario NOT IN (SELECT idEstudiante FROM proyecto WHERE idEstudiante IS NOT NULL)";
            PreparedStatement stmt = conexion.prepareStatement(consulta);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setIdUsuario(rs.getInt("idUsuario"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApePaterno(rs.getString("apePaterno"));
                estudiante.setApeMaterno(rs.getString("apeMaterno"));
                estudiante.setCorreo(rs.getString("correo"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setIdentificador(rs.getString("identificador"));
                lista.add(estudiante);
            }

            rs.close();
            stmt.close();
            conexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    return lista;
    }

    public static List<Estudiante> listarEstudiantesSinEvaluacion() {
        List<Estudiante> lista = new ArrayList<>();
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            try {
                String consulta = "SELECT u.idUsuario, u.nombre, u.apePaterno, u.apeMaterno, u.correo, u.telefono, u.identificador " +
                    "FROM usuario u " +
                    "JOIN expediente e ON u.idUsuario = e.idEstudiante " +
                    "WHERE u.rol = 'estudiante' " +
                    "AND e.idExpediente NOT IN (SELECT idExpediente FROM evaluacion_presentacion) " +
                    "AND CAST(e.horas AS UNSIGNED) > 120";
            PreparedStatement stmt = conexion.prepareStatement(consulta);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdUsuario(rs.getInt("idUsuario"));
                    estudiante.setNombre(rs.getString("nombre"));
                    estudiante.setApePaterno(rs.getString("apePaterno"));
                    estudiante.setApeMaterno(rs.getString("apeMaterno"));
                    estudiante.setCorreo(rs.getString("correo"));
                    estudiante.setTelefono(rs.getString("telefono"));
                    estudiante.setIdentificador(rs.getString("identificador"));
                    lista.add(estudiante);
                }

                rs.close();
                stmt.close();
                conexion.close();
            } catch (SQLException ex) {
                System.err.println("Error al listar estudiantes sin evaluación: " + ex.getMessage());
            }
        }

        return lista;
    }
}