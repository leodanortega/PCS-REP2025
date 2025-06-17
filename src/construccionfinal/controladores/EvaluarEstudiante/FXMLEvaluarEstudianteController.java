package construccionfinal.controladores.EvaluarEstudiante;

import construccionfinal.dao.CriterioEvaluacionResultadoDAO;
import construccionfinal.dao.CriteriosPresentacionDAO;
import construccionfinal.dao.EvaluacionPresentacionDAO;
import construccionfinal.modelo.pojo.CriterioEvaluacion;
import construccionfinal.modelo.pojo.CriterioEvaluacionObservable;
import construccionfinal.modelo.pojo.CriterioEvaluacionResultado;
import construccionfinal.modelo.pojo.Estudiante;
import construccionfinal.utilidades.Utilidad;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FXMLEvaluarEstudianteController {

    @FXML
    private TableView<CriterioEvaluacionObservable> tablaEvaluacion;
    @FXML
    private TableColumn<CriterioEvaluacionObservable, String> colCriterio;
    @FXML
    private TableColumn<CriterioEvaluacionObservable, RadioButton> colCompetente;
    @FXML
    private TableColumn<CriterioEvaluacionObservable, RadioButton> colIndependiente;
    @FXML
    private TableColumn<CriterioEvaluacionObservable, RadioButton> colBasicoAvanzado;
    @FXML
    private TableColumn<CriterioEvaluacionObservable, RadioButton> colBasicoUmbral;
    @FXML
    private TableColumn<CriterioEvaluacionObservable, RadioButton> colNoCompetente;
    @FXML
    private TextArea txtObservaciones;

    private Estudiante estudiante;
    private Map<CriterioEvaluacionObservable, Integer> respuestas = new HashMap<>();

    public void inicializarDatos(Estudiante estudiante) {
        this.estudiante = estudiante;
        cargarCriterios();
    }

    @FXML
    public void initialize() {
        colCriterio.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        configurarColumnasSeleccionables();
    }

    private void cargarCriterios() {
        List<CriterioEvaluacionObservable> criterios = CriteriosPresentacionDAO.obtenerTodos();
        tablaEvaluacion.setItems(FXCollections.observableArrayList(criterios));
    }

    private void configurarColumnasSeleccionables() {
        configurarRadioButtonColumn(colCompetente, 10);
        configurarRadioButtonColumn(colIndependiente, 9);
        configurarRadioButtonColumn(colBasicoAvanzado, 8);
        configurarRadioButtonColumn(colBasicoUmbral, 7);
        configurarRadioButtonColumn(colNoCompetente, 5);
    }

    private void configurarRadioButtonColumn(TableColumn<CriterioEvaluacionObservable, RadioButton> columna, int puntaje) {
        columna.setCellFactory(tc -> new TableCell<CriterioEvaluacionObservable, RadioButton>() {
            private final RadioButton radioButton = new RadioButton();

            @Override
            protected void updateItem(RadioButton item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    return;
                }

                Object rowItem = getTableRow().getItem();
                if (rowItem instanceof CriterioEvaluacionObservable) {
                    CriterioEvaluacionObservable criterio = (CriterioEvaluacionObservable) rowItem;
                    radioButton.setSelected(respuestas.getOrDefault(criterio, 0) == puntaje);
                    radioButton.setOnAction(e -> {
                        respuestas.put(criterio, puntaje);
                        actualizarSelección(criterio, puntaje);
                    });
                    setGraphic(radioButton);
                }
            }

        });
    }

    private void actualizarSelección(CriterioEvaluacionObservable criterio, int puntaje) {
        respuestas.put(criterio, puntaje);
        tablaEvaluacion.refresh();
    }

    @FXML
    private void clicEvaluar() {
        int idExpediente = obtenerIdExpediente();

        if (idExpediente == -1) {
            mostrarAlerta("No se encontró un expediente válido para el estudiante.");
            return;
        }

        java.sql.Date fechaActual = new java.sql.Date(System.currentTimeMillis());
        Map<CriterioEvaluacion, Integer> respuestasEvaluacion = new HashMap<>();

        for (Map.Entry<CriterioEvaluacionObservable, Integer> entry : respuestas.entrySet()) {
            CriterioEvaluacion criterio = new CriterioEvaluacion(
                    entry.getKey().getIdCriterio(),
                    entry.getKey().getNombreCriterio(),
                    entry.getKey().getDescripcion()
            );
            respuestasEvaluacion.put(criterio, entry.getValue());
        }

        // Ahora llamamos a guardarEvaluacion con los tipos correctos
        int idEvaluacion = EvaluacionPresentacionDAO.guardarEvaluacion(
                respuestasEvaluacion, "Evaluación presentación", fechaActual, idExpediente);

        if (idEvaluacion == -1) {
            mostrarAlerta("Ocurrió un error al guardar la evaluación.");
            return;
        }

        System.out.println("ID Evaluación guardado: " + idEvaluacion);

        // Generamos resultados con idEvaluacion
        List<CriterioEvaluacionResultado> resultados = respuestasEvaluacion.entrySet().stream()
                .map(entry -> new CriterioEvaluacionResultado(
                        idEvaluacion,
                        entry.getKey().getIdCriterio(),
                        entry.getValue()
                ))
                .collect(Collectors.toList());

        boolean guardadoResultados = CriterioEvaluacionResultadoDAO.guardarResultados(idEvaluacion, resultados);

        if (!guardadoResultados) {
            mostrarAlerta("La evaluación se guardó, pero ocurrió un error al guardar los resultados.");
        } else {
            mostrarAlerta("Evaluación de presentación y resultados guardados correctamente.");
            cerrarVentana();
        }
    }


    private int obtenerIdExpediente() {
        return construccionfinal.dao.ExpedienteDAO
                .obtenerExpedientePorEstudiante(estudiante.getIdUsuario())
                .getIdExpediente();
    }

    @FXML
    private void clicSalir() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tablaEvaluacion.getScene().getWindow();
        Utilidad.mostrarAlertaConfirmacion("Salir", "¿Estás seguro de que deseas cancelar la evaluación?");
        stage.close();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}