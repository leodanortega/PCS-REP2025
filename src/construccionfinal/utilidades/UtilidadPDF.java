package construccionfinal.utilidades;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.FileNotFoundException;
import construccionfinal.modelo.pojo.*;

import java.io.IOException;
import java.util.Map;

public class UtilidadPDF {

    public static void generarPDF(String rutaArchivo, Estudiante estudiante, Proyecto proyecto,
                                  OrganizacionVinculada organizacion, ResponsableProyecto responsable,
                                  Map<CriterioEvaluacion, Integer> respuestas) {

        try {
            PdfWriter writer = new PdfWriter(rutaArchivo + ".pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.LETTER);
            document.setMargins(75, 80, 75, 80);

            // Encabezado
            Paragraph titulo = new Paragraph("Evaluación de Organización Vinculada")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(titulo);

            // Datos del aspirante
            document.add(new Paragraph("\nDatos del Aspirante:")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(16));

            document.add(new Paragraph("Nombre: " + estudiante.getNombre()));
            document.add(new Paragraph("Matrícula: " + estudiante.getIdentificador()));
            document.add(new Paragraph("Proyecto: " + proyecto.getNombre()));
            document.add(new Paragraph("Organización: " + organizacion.getNombre()));
            document.add(new Paragraph("Responsable: " + responsable.getNombre()));

            // Respuestas
            document.add(new Paragraph("\nRespuestas de la Evaluación:")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(16));

            for (Map.Entry<CriterioEvaluacion, Integer> entry : respuestas.entrySet()) {
                document.add(new Paragraph(entry.getKey().getDescripcion() + ": " + entry.getValue()));
            }

            document.close();
            System.out.println("PDF generado correctamente: " + rutaArchivo + ".pdf");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}