package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.CriterioEvaluacionResultado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CriterioPresentacionResultadoDAO {

    public static boolean guardarResultados(int idEvaluacion, List<CriterioEvaluacionResultado> resultados) {
        String query = "INSERT INTO criterios_presentacion_resultado (idEvaluacion, idCriterio, puntajeObtenido) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (CriterioEvaluacionResultado resultado : resultados) {
                stmt.setInt(1, idEvaluacion);
                stmt.setInt(2, resultado.getIdCriterio());
                stmt.setDouble(3, resultado.getPuntajeObtenido());
                stmt.addBatch();
            }

            stmt.executeBatch();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al guardar resultados de presentación: " + e.getMessage());
            return false;
        }
    }
}
