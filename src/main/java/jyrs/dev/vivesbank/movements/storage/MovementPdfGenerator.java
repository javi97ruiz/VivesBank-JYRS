package jyrs.dev.vivesbank.movements.storage;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import jyrs.dev.vivesbank.movements.models.Movement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MovementPdfGenerator {

    private static final Logger logger = LoggerFactory.getLogger(MovementPdfGenerator.class);
    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private static final String NA = "N/A";

    /**
     * Genera un archivo PDF con los detalles de un movimiento bancario.
     * @param filePath La ruta del archivo donde se guardará el PDF.
     * @param movement El objeto movimiento con los detalles a incluir en el PDF.
     */

    public static void generateMovementPdf(String filePath, Movement movement) {
        try {
            ensureDirectoryExists(filePath);

            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Detalles del Movimiento").setFontSize(14));

            addMovementDetailsToDocument(document, movement);

            document.close();
            logger.info("PDF generado exitosamente: {}", filePath);
        } catch (IOException e) {
            logger.error("Error al generar el PDF: {}", e.getMessage(), e);
        }
    }

    /**
     * Genera un archivo PDF con una lista de movimientos bancarios.
     * @param filePath La ruta del archivo donde se guardará el PDF.
     * @param movements La lista de movimientos a incluir en el PDF.
     */

    public static void generateMovementsPdf(String filePath, List<Movement> movements) {
        try {
            ensureDirectoryExists(filePath);

            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Lista de Movimientos").setFontSize(14));

            Table table = new Table(6); // Número de columnas
            table.addCell("ID");
            table.addCell("Tipo");
            table.addCell("Fecha");
            table.addCell("Cantidad");
            table.addCell("Saldo");
            table.addCell("Reversible");

            for (Movement movement : movements) {
                table.addCell(movement.getId());
                table.addCell(movement.getTypeMovement());
                table.addCell(formatDateTime(movement.getDate()));
                table.addCell(String.valueOf(movement.getAmount()));
                table.addCell(String.valueOf(movement.getBalance()));
                table.addCell(String.valueOf(movement.getIsReversible()));
            }

            document.add(table);
            document.close();
            logger.info("PDF generado exitosamente: {}", filePath);
        } catch (IOException e) {
            logger.error("Error al generar el PDF: {}", e.getMessage(), e);
        }
    }

    private static void addMovementDetailsToDocument(Document document, Movement movement) {
        document.add(new Paragraph("ID: " + movement.getId()));
        document.add(new Paragraph("Tipo de Movimiento: " + movement.getTypeMovement()));
        document.add(new Paragraph("Fecha: " + formatDateTime(movement.getDate())));
        document.add(new Paragraph("Cantidad: " + movement.getAmount()));
        document.add(new Paragraph("Saldo: " + movement.getBalance()));
        document.add(new Paragraph("Reversible: " + movement.getIsReversible()));
        document.add(new Paragraph("Fecha Límite: " + formatDateTime(movement.getTransferDeadlineDate())));
        document.add(new Paragraph("Cuenta Origen: " + (movement.getOrigin() != null ? movement.getOrigin().getId() : NA)));
        document.add(new Paragraph("Cuenta Destino: " + (movement.getDestination() != null ? movement.getDestination().getId() : NA)));
        document.add(new Paragraph("Cliente Remitente: " + (movement.getSenderClient() != null ? movement.getSenderClient().getId() : NA)));
        document.add(new Paragraph("Cliente Destinatario: " + (movement.getRecipientClient() != null ? movement.getRecipientClient().getId() : NA)));
    }

    /**
     * Formatea la fecha y hora en el formato dd/MM/yyyy HH:mm:ss.
     * @param dateTime El objeto LocalDateTime que se formateará.
     * @return La fecha formateada como cadena.
     */

    private static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return NA;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dateTime.format(formatter);
    }

    /**
     * Verifica si el directorio donde se guardará el archivo PDF existe, y lo crea si no es así.
     * @param filePath La ruta del archivo PDF.
     * @throws IOException Si ocurre un error al crear el directorio.
     */

    private static void ensureDirectoryExists(String filePath) throws IOException {
        Path directory = Path.of(filePath).getParent();
        if (directory != null && !Files.exists(directory)) {
            Files.createDirectories(directory);
            logger.info("Directorio creado: {}", directory);
        }
    }
}