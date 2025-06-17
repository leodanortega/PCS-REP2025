package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.CriterioEvaluacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class EvaluacionOVDAO {

    /**
     * Guarda la evaluación de organización vinculada y devuelve el ID generado.
     *
     * @param respuestas     Mapa de criterios con su puntaje.
     * @param comentarios    Comentarios generales de la evaluación.
     * @param idExpediente   ID del expediente relacionado.
     * @return ID generado de la evaluación o -1 si falló.
     */
    public static int guardarEvaluacion(Map<CriterioEvaluacion, Integer> respuestas, String comentarios, int idExpediente) {
        double puntajeTotal = calcularPuntajeTotal(respuestas);
        int idGenerado = -1;

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
                        idGenerado = generatedKeys.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al guardar la evaluación OV: " + e.getMessage());
        }

        return idGenerado;
    }

    /**
     * Calcula el puntaje total a partir de los puntajes individuales.
     */
    private static double calcularPuntajeTotal(Map<CriterioEvaluacion, Integer> respuestas) {
        int sumaTotal = respuestas.values().stream().mapToInt(Integer::intValue).sum();
        return sumaTotal / 10.0;
    }
}
