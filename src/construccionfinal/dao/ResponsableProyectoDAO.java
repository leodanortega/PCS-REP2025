package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.ResponsableProyecto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResponsableProyectoDAO {

    public List<ResponsableProyecto> listar() {
        List<ResponsableProyecto> lista = new ArrayList<>();
        String sql = "SELECT * FROM responsable_proyecto";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionBD.abrirConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ResponsableProyecto rp = new ResponsableProyecto();
                rp.setIdResponsable(rs.getInt("idResponsable"));
                rp.setNombre(rs.getString("nombre"));
                rp.setApePaterno(rs.getString("apePaterno"));
                rp.setApeMaterno(rs.getString("apeMaterno"));
                rp.setCorreo(rs.getString("correo"));
                rp.setTelefono(rs.getString("telefono"));
                rp.setPuesto(rs.getString("puesto"));
                rp.setIdOrganizacion(rs.getInt("idOrganizacion"));
                lista.add(rp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }

    public boolean agregar(ResponsableProyecto rp) {
        String sql = "INSERT INTO responsable_proyecto(nombre, apePaterno, apeMaterno, correo, telefono, puesto, idOrganizacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConexionBD.abrirConexion();
            ps = con.prepareStatement(sql);

            ps.setString(1, rp.getNombre());
            ps.setString(2, rp.getApePaterno());
            ps.setString(3, rp.getApeMaterno());
            ps.setString(4, rp.getCorreo());
            ps.setString(5, rp.getTelefono());
            ps.setString(6, rp.getPuesto());
            ps.setInt(7, rp.getIdOrganizacion());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean modificar(ResponsableProyecto rp) {
        String sql = "UPDATE responsable_proyecto SET nombre = ?, apePaterno = ?, apeMaterno = ?, correo = ?, telefono = ?, puesto = ?, idOrganizacion = ? WHERE idResponsable = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConexionBD.abrirConexion();
            ps = con.prepareStatement(sql);

            ps.setString(1, rp.getNombre());
            ps.setString(2, rp.getApePaterno());
            ps.setString(3, rp.getApeMaterno());
            ps.setString(4, rp.getCorreo());
            ps.setString(5, rp.getTelefono());
            ps.setString(6, rp.getPuesto());
            ps.setInt(7, rp.getIdOrganizacion());
            ps.setInt(8, rp.getIdResponsable());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean eliminar(int idResponsable) {
        String sql = "DELETE FROM responsable_proyecto WHERE idResponsable = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConexionBD.abrirConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idResponsable);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<ResponsableProyecto> buscarPorNombre(String nombreBusqueda) {
        List<ResponsableProyecto> lista = new ArrayList<>();
        String sql = "SELECT * FROM responsable_proyecto WHERE nombre LIKE ?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionBD.abrirConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nombreBusqueda + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                ResponsableProyecto rp = new ResponsableProyecto();
                rp.setIdResponsable(rs.getInt("idResponsable"));
                rp.setNombre(rs.getString("nombre"));
                rp.setApePaterno(rs.getString("apePaterno"));
                rp.setApeMaterno(rs.getString("apeMaterno"));
                rp.setCorreo(rs.getString("correo"));
                rp.setTelefono(rs.getString("telefono"));
                rp.setPuesto(rs.getString("puesto"));
                rp.setIdOrganizacion(rs.getInt("idOrganizacion"));
                lista.add(rp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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

    public ResponsableProyecto buscarPorId(int id) {
        ResponsableProyecto responsable = null;
        String sql = "SELECT idResponsable, nombre, apePaterno, apeMaterno, correo, telefono, puesto, idOrganizacion FROM responsable_proyecto WHERE idResponsable = ?";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    responsable = new ResponsableProyecto();
                    responsable.setIdResponsable(rs.getInt("idResponsable"));
                    responsable.setNombre(rs.getString("nombre"));
                    responsable.setApePaterno(rs.getString("apePaterno"));
                    responsable.setApeMaterno(rs.getString("apeMaterno"));
                    responsable.setCorreo(rs.getString("correo"));
                    responsable.setTelefono(rs.getString("telefono"));
                    responsable.setPuesto(rs.getString("puesto"));
                    responsable.setIdOrganizacion(rs.getInt("idOrganizacion"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return responsable;
    }
}
