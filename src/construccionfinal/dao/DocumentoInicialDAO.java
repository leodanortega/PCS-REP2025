package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.DocumentoInicial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentoInicialDAO {

    public boolean agregar(DocumentoInicial doc) {
        String sql = "INSERT INTO documento_inicial (nombre, estado, tipoDocumento, fecha, archivo) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, doc.getNombre());
            ps.setString(2, doc.getEstado());
            ps.setString(3, doc.getTipoDocumento());
            ps.setDate(4, doc.getFecha());
            ps.setBytes(5, doc.getArchivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificar(DocumentoInicial doc) {
        String sql = "UPDATE documento_inicial SET nombre = ?, estado = ?, tipoDocumento = ?, fecha = ?, archivo = ? WHERE idDocumentoInicial = ?";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, doc.getNombre());
            ps.setString(2, doc.getEstado());
            ps.setString(3, doc.getTipoDocumento());
            ps.setDate(4, doc.getFecha());
            ps.setBytes(5, doc.getArchivo());
            ps.setInt(6, doc.getIdDocumentoInicial());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM documento_inicial WHERE idDocumentoInicial = ?";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DocumentoInicial buscarPorId(int id) {
        String sql = "SELECT * FROM documento_inicial WHERE idDocumentoInicial = ?";
        DocumentoInicial doc = null;

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    doc = new DocumentoInicial();
                    doc.setIdDocumentoInicial(rs.getInt("idDocumentoInicial"));
                    doc.setNombre(rs.getString("nombre"));
                    doc.setEstado(rs.getString("estado"));
                    doc.setTipoDocumento(rs.getString("tipoDocumento"));
                    doc.setFecha(rs.getDate("fecha"));
                    doc.setArchivo(rs.getBytes("archivo"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doc;
    }

    public List<DocumentoInicial> listarTodos() {
        List<DocumentoInicial> lista = new ArrayList<>();
        String sql = "SELECT * FROM documento_inicial";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DocumentoInicial doc = new DocumentoInicial();
                doc.setIdDocumentoInicial(rs.getInt("idDocumentoInicial"));
                doc.setNombre(rs.getString("nombre"));
                doc.setEstado(rs.getString("estado"));
                doc.setTipoDocumento(rs.getString("tipoDocumento"));
                doc.setFecha(rs.getDate("fecha"));
                doc.setArchivo(rs.getBytes("archivo"));

                lista.add(doc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}

