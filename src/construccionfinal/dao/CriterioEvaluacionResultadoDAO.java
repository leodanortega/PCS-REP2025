package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.CriterioEvaluacionResultado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CriterioEvaluacionResultadoDAO {

    public static boolean guardarResultados(int idEvaluacionOV, List<CriterioEvaluacionResultado> resultados) {
        String query = "INSERT INTO criterio_evaluacion_resultado (idEvaluacionOV, idCriterio, puntajeObtenido) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (CriterioEvaluacionResultado resultado : resultados) {
                stmt.setInt(1, idEvaluacionOV);
                stmt.setInt(2, resultado.getIdCriterio());
                stmt.setDouble(3, resultado.getPuntajeObtenido());
                stmt.addBatch();
            }

            stmt.executeBatch();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al guardar resultados de evaluación: " + e.getMessage());
            return false;
        }
    }
}
