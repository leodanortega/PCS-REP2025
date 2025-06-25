package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.Expediente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpedienteDAO {

    public static Expediente obtenerExpedientePorEstudiante(int idEstudiante) {
        Expediente expediente = null;
        String sql = "SELECT * FROM expediente WHERE idEstudiante = ?";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEstudiante);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    expediente = new Expediente();
                    expediente.setIdExpediente(rs.getInt("idExpediente"));
                    expediente.setIdEstudiante(rs.getInt("idEstudiante"));
                    expediente.setIdGrupoEE(rs.getInt("idGrupoEE"));
                    expediente.setIdPeriodo(rs.getInt("idPeriodo"));
                    expediente.setCalificaciones(rs.getString("califaciones")); // corregido
                    expediente.setHoras(rs.getString("horas"));
                    expediente.setInforme(rs.getString("informe"));
                    expediente.setIdDocumentoInicial(rs.getInt("idDocumentoInicial"));
                    expediente.setIdProyecto(rs.getInt("idProyecto")); // nuevo campo
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expediente;
    }

    public static int obtenerHorasPorExpediente(int idExpediente) {
        int horas = 0;
        String sql = "SELECT horas FROM expediente WHERE idExpediente = ?";

        try (Connection con = ConexionBD.abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idExpediente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String horasStr = rs.getString("horas");
                    if (horasStr != null && horasStr.matches("\\d+")) {
                        horas = Integer.parseInt(horasStr);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return horas;
    }

public static int crearExpediente(int idEstudiante, int idGrupoEE, int idPeriodo,
                                  String calificaciones, String horas, String informe,
                                  int idDocumentoInicial, int idProyecto) {
    int idExpediente = -1;
    String sql = "INSERT INTO expediente (idEstudiante, idGrupoEE, idPeriodo, califaciones, horas, informe, idDocumentoInicial, idProyecto) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = ConexionBD.abrirConexion();
         PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

        ps.setInt(1, idEstudiante);
        ps.setInt(2, idGrupoEE);
        ps.setInt(3, idPeriodo);
        ps.setString(4, calificaciones);
        ps.setString(5, horas);
        ps.setString(6, informe);
        ps.setInt(7, idDocumentoInicial);
        ps.setInt(8, idProyecto);

        ps.executeUpdate();

        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                idExpediente = generatedKeys.getInt(1);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return idExpediente;
}
}