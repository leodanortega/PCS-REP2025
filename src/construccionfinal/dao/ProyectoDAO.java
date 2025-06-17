package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.Proyecto;

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
}