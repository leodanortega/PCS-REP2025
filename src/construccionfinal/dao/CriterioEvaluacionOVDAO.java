package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.CriterioEvaluacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CriterioEvaluacionOVDAO {

    public static List<CriterioEvaluacion> obtenerCriterios() {
        List<CriterioEvaluacion> criterios = new ArrayList<>();
        String query = "SELECT idCriterio, nombreCriterio, descripcion FROM criterios_evaluacion_organizacion_vinculada";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CriterioEvaluacion criterio = new CriterioEvaluacion();
                criterio.setIdCriterio(rs.getInt("idCriterio"));
                criterio.setNombreCriterio(rs.getString("nombreCriterio"));
                criterio.setDescripcion(rs.getString("descripcion"));
                criterios.add(criterio);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener criterios: " + e.getMessage());
        }

        return criterios;
    }

    public static List<CriterioEvaluacion> obtenerTodos() {
        return obtenerCriterios();
    }
}
