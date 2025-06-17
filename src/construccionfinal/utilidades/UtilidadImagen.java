package construccionfinal.utilidades;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Map;

import construccionfinal.modelo.pojo.*;

public class UtilidadImagen {

    public static void generarImagen(String rutaArchivo, Estudiante estudiante, Proyecto proyecto,
                                     OrganizacionVinculada organizacion, ResponsableProyecto responsable,
                                     Map<CriterioEvaluacion, Integer> respuestas) {
        int width = 800;
        int height = 1000;

        BufferedImage imagen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = imagen.createGraphics();

        // Estilo del documento
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Evaluación de Organización Vinculada", 50, 50);

        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        int y = 100;
        g2d.drawString("Datos del Aspirante:", 50, y);
        y += 30;
        g2d.drawString("Nombre: " + estudiante.getNombre(), 50, y);
        y += 20;
        g2d.drawString("Matrícula: " + estudiante.getIdentificador(), 50, y);
        y += 20;
        g2d.drawString("Proyecto: " + proyecto.getNombre(), 50, y);
        y += 20;
        g2d.drawString("Organización: " + organizacion.getNombre(), 50, y);
        y += 20;
        g2d.drawString("Responsable: " + responsable.getNombre(), 50, y);

        y += 40;
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Respuestas de la Evaluación:", 50, y);
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        for (Map.Entry<CriterioEvaluacion, Integer> entry : respuestas.entrySet()) {
            y += 20;
            g2d.drawString(entry.getKey().getDescripcion() + ": " + entry.getValue(), 50, y);
        }

        g2d.dispose();

        try {
            ImageIO.write(imagen, "png", new File(rutaArchivo + ".png"));
            System.out.println("Imagen generada correctamente: " + rutaArchivo + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}