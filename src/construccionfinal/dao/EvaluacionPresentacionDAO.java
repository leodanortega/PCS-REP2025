package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.CriterioEvaluacion;
import construccionfinal.modelo.pojo.CriterioEvaluacionObservable;

import java.sql.*;
import java.util.Map;

public class EvaluacionPresentacionDAO {

    public static int guardarEvaluacion(Map<CriterioEvaluacion, Integer> respuestas, String nombre, Date fecha, int idExpediente) {
        double calificacion = calcularCalificacion(respuestas);

        String query = "INSERT INTO evaluacion_presentacion (nombre, fecha, calificacion, idExpediente) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nombre);
            stmt.setDate(2, fecha);
            stmt.setDouble(3, calificacion);
            stmt.setInt(4, idExpediente);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al guardar evaluación de presentación: " + e.getMessage());
        }

        return -1;
    }

    private static double calcularCalificacion(Map<CriterioEvaluacion, Integer> respuestas) {
        int suma = 0;
        for (Integer puntaje : respuestas.values()) {
            suma += puntaje;
        }
        return suma / 5.0;
    }
}
