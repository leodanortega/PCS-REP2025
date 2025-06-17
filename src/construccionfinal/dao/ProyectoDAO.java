package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.Estudiante;
import construccionfinal.modelo.pojo.OrganizacionVinculada;
import construccionfinal.modelo.pojo.Proyecto;
import construccionfinal.modelo.pojo.ResponsableProyecto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProyectoDAO {

public List<Proyecto> listar() {
    List<Proyecto> lista = new ArrayList<>();
    String sql = "SELECT * FROM proyecto";

    try (Connection con = ConexionBD.abrirConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Proyecto p = new Proyecto();
            p.setIdProyecto(rs.getInt("idProyecto"));
            p.setNombre(rs.getString("nombre"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setMetodologia(rs.getString("metodologia"));
            p.setEspacios(rs.getString("espacios"));
            p.setDepartamento(rs.getString("departamento"));
            p.setIdResponsable(rs.getInt("idResponsable"));
            p.setIdOrganizacion(rs.getInt("idOrganizacion"));

            // Cargar objeto ResponsableProyecto
            ResponsableProyecto responsable;
            responsable = ResponsableProyectoDAO.buscarPorId(p.getIdResponsable());
            p.setResponsableProyecto(responsable);

            // Cargar objeto OrganizacionVinculada
            OrganizacionVinculada organizacion;
            organizacion = OrganizacionVinculadaDAO.buscarPorId(p.getIdOrganizacion());
            p.setOrganizacionVinculada(organizacion);

            lista.add(p);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}


    public boolean agregar(Proyecto p) {
        String sql = "INSERT INTO proyecto(nombre, descripcion, metodologia, espacios, departamento, idResponsable, idOrganizacion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setString(3, p.getMetodologia());
            ps.setString(4, p.getEspacios());
            ps.setString(5, p.getDepartamento());
            ps.setInt(6, p.getIdResponsable());
            ps.setInt(7, p.getIdOrganizacion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificar(Proyecto p) {
        String sql = "UPDATE proyecto SET nombre = ?, descripcion = ?, metodologia = ?, espacios = ?, departamento = ?, " +
                     "idResponsable = ?, idOrganizacion = ? WHERE idProyecto = ?";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setString(3, p.getMetodologia());
            ps.setString(4, p.getEspacios());
            ps.setString(5, p.getDepartamento());
            ps.setInt(6, p.getIdResponsable());
            ps.setInt(7, p.getIdOrganizacion());
            ps.setInt(8, p.getIdProyecto());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int idProyecto) {
        String sql = "DELETE FROM proyecto WHERE idProyecto = ?";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProyecto);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Proyecto> buscarPorNombre(String nombreBusqueda) {
        List<Proyecto> lista = new ArrayList<>();
        String sql = "SELECT * FROM proyecto WHERE nombre LIKE ?";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nombreBusqueda + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Proyecto p = new Proyecto();
                    p.setIdProyecto(rs.getInt("idProyecto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setMetodologia(rs.getString("metodologia"));
                    p.setEspacios(rs.getString("espacios"));
                    p.setDepartamento(rs.getString("departamento"));
                    p.setIdResponsable(rs.getInt("idResponsable"));
                    p.setIdOrganizacion(rs.getInt("idOrganizacion"));
                    lista.add(p);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static boolean hayConexion() {
        try (Connection conn = ConexionBD.abrirConexion()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public Proyecto buscarPorEstudiante(int idUsuario) {
        Proyecto proyecto = null;
        String sql = "SELECT * FROM proyecto WHERE idEstudiante = ? LIMIT 1"; // 🔹 Solo devuelve un proyecto

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // 🔹 Solo un resultado
                    proyecto = new Proyecto();
                    proyecto.setIdProyecto(rs.getInt("idProyecto"));
                    proyecto.setNombre(rs.getString("nombre"));
                    proyecto.setDescripcion(rs.getString("descripcion"));
                    proyecto.setMetodologia(rs.getString("metodologia"));
                    proyecto.setEspacios(rs.getString("espacios"));
                    proyecto.setDepartamento(rs.getString("departamento"));
                    proyecto.setIdResponsable(rs.getInt("idResponsable"));
                    proyecto.setIdOrganizacion(rs.getInt("idOrganizacion"));

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proyecto;
    }
    public static List<Proyecto> obtenerTodos() {
    List<Proyecto> lista = new ArrayList<>();
    String sql = "SELECT p.*, " +
                 "rp.nombre AS nombreResponsable, rp.apePaterno AS apePaternoResponsable, rp.apeMaterno AS apeMaternoResponsable, " +
                 "o.nombre AS nombreOrganizacion " +
                 "FROM proyecto p " +
                 "INNER JOIN responsableproyecto rp ON p.idResponsable = rp.idResponsable " +
                 "INNER JOIN organizacion o ON p.idOrganizacion = o.idOrganizacion";

    try (Connection con = ConexionBD.abrirConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Proyecto p = new Proyecto();
            p.setIdProyecto(rs.getInt("idProyecto"));
            p.setNombre(rs.getString("nombre"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setMetodologia(rs.getString("metodologia"));
            p.setEspacios(rs.getString("espacios"));
            p.setDepartamento(rs.getString("departamento"));
            p.setIdResponsable(rs.getInt("idResponsable"));
            p.setIdOrganizacion(rs.getInt("idOrganizacion"));

            // Construir objeto ResponsableProyecto
            ResponsableProyecto responsable = new ResponsableProyecto();
            responsable.setIdResponsable(rs.getInt("idResponsable"));
            responsable.setNombre(rs.getString("nombreResponsable"));
            responsable.setApePaterno(rs.getString("apePaternoResponsable"));
            responsable.setApeMaterno(rs.getString("apeMaternoResponsable"));
            p.setResponsableProyecto(responsable);

            // Construir objeto Organizacion
            OrganizacionVinculada organizacion = new OrganizacionVinculada();
            organizacion.setIdOrganizacion(rs.getInt("idOrganizacion"));
            organizacion.setNombre(rs.getString("nombreOrganizacion"));
            p.setOrganizacionVinculada(organizacion);

            lista.add(p);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}

    public boolean asignarEstudianteAProyecto(int idProyecto, int idEstudiante) {
    String sql = "UPDATE proyecto SET idEstudiante = ? WHERE idProyecto = ? AND (idEstudiante IS NULL OR idEstudiante = 0)";

    try (Connection con = ConexionBD.abrirConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idEstudiante);
        ps.setInt(2, idProyecto);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
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
    
    public List<Proyecto> listarProyectosNoAsignados() {
    List<Proyecto> lista = new ArrayList<>();
    Connection conexion = ConexionBD.abrirConexion();

    if (conexion != null) {
        try {
            String consulta = "SELECT * FROM proyecto WHERE idEstudiante IS NULL";
            PreparedStatement stmt = conexion.prepareStatement(consulta);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Proyecto proyecto = new Proyecto();
                proyecto.setIdProyecto(rs.getInt("idProyecto"));
                proyecto.setNombre(rs.getString("nombre"));
                proyecto.setDescripcion(rs.getString("descripcion"));
                // ... otros campos
                lista.add(proyecto);
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

}