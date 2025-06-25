
package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.EntregaDocumento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntregaDocumentoDAO {

    public static EntregaDocumento obtenerRangoPorTipo(String tipoDocumento) {
        EntregaDocumento entrega = null;

        String sql = "SELECT * FROM entrega_documento WHERE tipoDocumento = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, tipoDocumento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                entrega = new EntregaDocumento(
                    rs.getInt("idEntrega"),
                    rs.getString("tipoDocumento"),
                    rs.getDate("fechaInicio").toLocalDate(),
                    rs.getTime("horaInicio").toLocalTime(),
                    rs.getDate("fechaFin").toLocalDate(),
                    rs.getTime("horaFin").toLocalTime()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entrega;
    }
}

