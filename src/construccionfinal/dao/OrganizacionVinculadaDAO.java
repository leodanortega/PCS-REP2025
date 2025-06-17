package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.OrganizacionVinculada;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizacionVinculadaDAO {

    public List<OrganizacionVinculada> listar() {
        List<OrganizacionVinculada> lista = new ArrayList<>();
        String sql = "SELECT * FROM organizacion_vinculada";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OrganizacionVinculada o = new OrganizacionVinculada();
                o.setIdOrganizacion(rs.getInt("idOrganizacion"));
                o.setNombre(rs.getString("nombre"));
                o.setCorreo(rs.getString("correo"));
                o.setDescripcion(rs.getString("descripcion"));
                o.setRFC(rs.getString("RFC"));
                o.setTelefono(rs.getString("telefono"));
                o.setTipo(rs.getString("tipo"));
                lista.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean agregar(OrganizacionVinculada o) {
        String sql = "INSERT INTO organizacion_vinculada(nombre, correo, descripcion, RFC, telefono, tipo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, o.getNombre());
            ps.setString(2, o.getCorreo());
            ps.setString(3, o.getDescripcion());
            ps.setString(4, o.getRFC());
            ps.setString(5, o.getTelefono());
            ps.setString(6, o.getTipo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificar(OrganizacionVinculada o) {
        String sql = "UPDATE organizacion_vinculada SET nombre = ?, correo = ?, descripcion = ?, RFC = ?, telefono = ?, tipo = ? WHERE idOrganizacion = ?";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, o.getNombre());
            ps.setString(2, o.getCorreo());
            ps.setString(3, o.getDescripcion());
            ps.setString(4, o.getRFC());
            ps.setString(5, o.getTelefono());
            ps.setString(6, o.getTipo());
            ps.setInt(7, o.getIdOrganizacion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int idOrganizacion) {
        String sql = "DELETE FROM organizacion_vinculada WHERE idOrganizacion = ?";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idOrganizacion);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<OrganizacionVinculada> buscarPorNombre(String nombreBusqueda) {
        List<OrganizacionVinculada> lista = new ArrayList<>();
        String sql = "SELECT * FROM organizacion_vinculada WHERE nombre LIKE ?";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nombreBusqueda + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrganizacionVinculada o = new OrganizacionVinculada();
                    o.setIdOrganizacion(rs.getInt("idOrganizacion"));
                    o.setNombre(rs.getString("nombre"));
                    o.setCorreo(rs.getString("correo"));
                    o.setDescripcion(rs.getString("descripcion"));
                    o.setRFC(rs.getString("RFC"));
                    o.setTelefono(rs.getString("telefono"));
                    o.setTipo(rs.getString("tipo"));
                    lista.add(o);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean existeNombre(String nombre) {
        String sql = "SELECT COUNT(*) FROM organizacion_vinculada WHERE nombre = ?";
        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean hayConexion() {
        try (Connection conn = ConexionBD.abrirConexion()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public OrganizacionVinculada buscarPorId(int id) {
        OrganizacionVinculada org = null;
        String sql = "SELECT * FROM organizacion_vinculada WHERE idOrganizacion = ?";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    org = new OrganizacionVinculada();
                    org.setIdOrganizacion(rs.getInt("idOrganizacion"));
                    org.setNombre(rs.getString("nombre"));
                    org.setCorreo(rs.getString("correo"));
                    org.setDescripcion(rs.getString("descripcion"));
                    org.setRFC(rs.getString("RFC"));
                    org.setTelefono(rs.getString("telefono"));
                    org.setTipo(rs.getString("tipo"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return org;
    }
}
