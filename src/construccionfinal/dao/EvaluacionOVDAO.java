package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.CriterioEvaluacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class EvaluacionOVDAO {

    public static int guardarEvaluacion(Map<CriterioEvaluacion, Integer> respuestas, String comentarios, int idExpediente) {
        double puntajeTotal = calcularPuntajeTotal(respuestas);

        String query = "INSERT INTO evaluacion_organizacion_vinculada (puntajeTotalObtenido, comentarios, idExpediente) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, puntajeTotal);
            stmt.setString(2, comentarios);
            stmt.setInt(3, idExpediente);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al guardar la evaluación OV: " + e.getMessage());
        }

        return -1;
    }

    private static double calcularPuntajeTotal(Map<CriterioEvaluacion, Integer> respuestas) {
        int sumaTotal = 0;

        for (Integer puntaje : respuestas.values()) {
            sumaTotal += puntaje;
        }

        return sumaTotal / 10.0; // Dividimos entre 10 para obtener el puntaje final
    }
}