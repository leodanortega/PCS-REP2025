package construccionfinal.dao;

import construccionfinal.conexionbd.ConexionBD;
import construccionfinal.modelo.pojo.CriterioEvaluacionObservable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CriteriosPresentacionDAO {

    public static List<CriterioEvaluacionObservable> obtenerTodos() {
        List<CriterioEvaluacionObservable> lista = new ArrayList<>();
        String query = "SELECT idCriterio, nombreCriterio, descripcion FROM criterios_presentacion";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CriterioEvaluacionObservable criterio = new CriterioEvaluacionObservable(
                        rs.getInt("idCriterio"),
                        rs.getString("nombreCriterio"),
                        rs.getString("descripcion")
                );
                lista.add(criterio);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener criterios de presentación: " + e.getMessage());
        }

        return lista;
    }

}
