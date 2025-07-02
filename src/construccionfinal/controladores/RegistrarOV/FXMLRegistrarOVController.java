package construccionfinal.controladores.RegistrarOV;

import construccionfinal.dao.OrganizacionVinculadaDAO;
import construccionfinal.modelo.pojo.OrganizacionVinculada;
import construccionfinal.utilidades.Utilidad;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class FXMLRegistrarOVController {

    @FXML private TextField tfNombre;
    @FXML private TextField tfCorreo;
    @FXML private TextField tfDescripcion;
    @FXML private TextField tfRFC;
    @FXML private TextField tfTelefono;
    @FXML private ComboBox<String> cbTipo;

    @FXML
    public void initialize() {
        cbTipo.getItems().addAll("Privada", "Pública");
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        if (tfNombre.getText().isEmpty() || tfCorreo.getText().isEmpty() ||
                tfDescripcion.getText().isEmpty() || tfRFC.getText().isEmpty() ||
                tfTelefono.getText().isEmpty() || cbTipo.getValue() == null) {

            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Existen campos inválidos, por favor corregir");
            return;
        }

        String nombre = tfNombre.getText().trim();
        if (!nombre.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ \\-()]+")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nombre inválido", "El nombre contiene caracteres no permitidos.");
            return;
        }
        if (nombre.length() < 3) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nombre inválido", "El nombre debe tener al menos 3 letras.");
            return;
        }
        if (nombre.length() > 45) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nombre inválido", "El nombre no puede exceder los 45 caracteres.");
            return;
        }

        String correo = tfCorreo.getText().trim();
        if (!correo.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Correo inválido", "El correo electrónico no tiene un formato válido.");
            return;
        }
        if (correo.length() > 45) {
            mostrarAlerta(Alert.AlertType.WARNING, "Correo inválido", "El correo no puede exceder los 45 caracteres.");
            return;
        }

        String descripcion = tfDescripcion.getText().trim();
        if (descripcion.length() < 10 || descripcion.length() > 45) {
            mostrarAlerta(Alert.AlertType.WARNING, "Descripción no válida", "La descripción debe tener de 10 a 45 caracteres.");
            return;
        }


        String rfc = tfRFC.getText().trim();

        if (!rfc.matches("^[A-ZÑ&]{3}[0-9]{6}[A-Z0-9]{3}$")) {
            mostrarAlerta(Alert.AlertType.WARNING, "RFC inválido", "El RFC no tiene un formato válido.");
            return;
        }

        if (rfc.length() == 12 && !esRFCCoherenteConNombre(nombre, rfc)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nombre no coincide con RFC",
                    "Las iniciales del nombre no coinciden con las del RFC.");
            return;
        }

        if (cbTipo.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Tipo no seleccionado", "Debes seleccionar el tipo de organización (Privada o Pública).");
            return;
        }


        try {
            String fechaStr = rfc.substring(3, 9);
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .parseStrict()
                    .appendValueReduced(ChronoField.YEAR, 2, 2, 1900)
                    .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                    .appendValue(ChronoField.DAY_OF_MONTH, 2)
                    .toFormatter(Locale.US)
                    .withResolverStyle(ResolverStyle.STRICT);

            LocalDate fecha = LocalDate.parse(fechaStr, formatter);

        } catch (DateTimeParseException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "RFC inválido", "La fecha de nacimiento del RFC no es válida.");
            return;
        } catch (StringIndexOutOfBoundsException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "RFC inválido", "El RFC es demasiado corto.");
            return;
        }

        if (!tfTelefono.getText().matches("\\d{10}")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Teléfono inválido", "El número de teléfono debe contener exactamente 10 dígitos numéricos.");
            return;
        }
        if (!esEnteroPositivo(tfTelefono.getText())) {
            mostrarAlerta(Alert.AlertType.WARNING, "Valor inválido", "El número de teléfono debe contener exactamente 10 dígitos numéricos.");
            return;
        }

        if (!tfCorreo.getText().matches("[A-Za-zÁÉÍÓÚáéíóúñÑ0-9 .@\\-()]+")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Correo inválido", "El correo no tiene un formato válido.");
            return;
        }

        if (!OrganizacionVinculadaDAO.hayConexion()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Sin conexión", "Error: No hay conexión con la base de datos");
            return;
        }

        OrganizacionVinculadaDAO dao = new OrganizacionVinculadaDAO();
        if (dao.existeNombre(tfRFC.getText())) {
            mostrarAlerta(Alert.AlertType.WARNING, "Organización ya registrada", "Ya existe una organización vinculada con ese RFC.");
            return;
        }

        OrganizacionVinculada nueva = new OrganizacionVinculada();
        nueva.setNombre(tfNombre.getText());
        nueva.setCorreo(tfCorreo.getText());
        nueva.setDescripcion(tfDescripcion.getText());
        nueva.setRFC(tfRFC.getText());
        nueva.setTelefono(tfTelefono.getText());
        nueva.setTipo(cbTipo.getValue());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/construccionfinal/vistas/RegistrarOV/FXMLConfirmacionDatos.fxml"));
            Parent root = loader.load();

            FXMLConfirmacionDatosController controller = loader.getController();
            controller.setOrganizacion(nueva);

            Stage stage = new Stage();
            stage.setTitle("Confirmar Organización");

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setResizable(true);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            if (controller.isConfirmado()) {
                boolean exito = dao.agregar(nueva);
                if (exito) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "La organización Vinculada se registró con éxito");
                    Stage currentStage = (Stage) tfNombre.getScene().getWindow();
                    currentStage.close();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar la organización.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de confirmación.");
        }

    }

    private boolean esEnteroPositivo(String texto) {
        try {
            long valor = Long.parseLong(texto);
            return valor > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean esRFCCoherenteConNombre(String nombre, String rfc) {
        if (rfc == null || rfc.length() < 3) return false;

        String[] palabrasIgnoradas = {"de", "del", "la", "las", "los", "y", "sa", "cv", "s", "rl", "s.", "s.a.", "s.a", "srl"};

        // Convertir nombre a minúsculas y separar por espacios
        String[] palabras = nombre.toLowerCase().split("\\s+");

        // Filtrar palabras ignoradas
        java.util.List<String> palabrasUtiles = new java.util.ArrayList<>();
        for (String palabra : palabras) {
            if (!esPalabraIgnorable(palabra, palabrasIgnoradas)) {
                palabrasUtiles.add(palabra);
            }
        }

        StringBuilder iniciales = new StringBuilder();

        if (palabrasUtiles.size() >= 3) {
            // Tomar la primera letra de las primeras 3 palabras útiles
            for (int i = 0; i < 3; i++) {
                iniciales.append(Character.toUpperCase(palabrasUtiles.get(i).charAt(0)));
            }
        } else if (palabrasUtiles.size() == 2) {
            // Primera letra de la primera palabra
            iniciales.append(Character.toUpperCase(palabrasUtiles.get(0).charAt(0)));

            // Primera letra de la segunda palabra
            iniciales.append(Character.toUpperCase(palabrasUtiles.get(1).charAt(0)));

            // Primera consonante interna de la segunda palabra (ignorando la primera letra)
            char primeraConsonante = buscarPrimeraConsonanteInterna(palabrasUtiles.get(1));
            iniciales.append(Character.toUpperCase(primeraConsonante));
        } else if (palabrasUtiles.size() == 1) {
            // Tomar las primeras 3 letras de la única palabra útil (o las que tenga si son menos)
            String palabra = palabrasUtiles.get(0);
            for (int i = 0; i < 3 && i < palabra.length(); i++) {
                iniciales.append(Character.toUpperCase(palabra.charAt(i)));
            }
        } else {
            // No hay palabras útiles
            return false;
        }

        String letrasRFC = rfc.substring(0, 3).toUpperCase();

        return letrasRFC.equals(iniciales.toString());
    }

    private char buscarPrimeraConsonanteInterna(String palabra) {
        String vocales = "aeiouáéíóú";
        for (int i = 1; i < palabra.length(); i++) {
            char c = palabra.charAt(i);
            if (!vocales.contains(String.valueOf(c))) {
                return c;
            }
        }
        // Si no hay consonante interna, repetir la primera letra
        return palabra.charAt(0);
    }

    private boolean esPalabraIgnorable(String palabra, String[] ignoradas) {
        for (String ign : ignoradas) {
            if (palabra.equals(ign)) return true;
        }
        return false;
    }



    @FXML
    private void clicSalir() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Seguro que quieres salir?");

        if (alerta.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            Stage stage = (Stage) tfNombre.getScene().getWindow();
            stage.close();
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

}
