package org.esfe.AppRyDBodega_Pro.servicios.utilerias;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.esfe.AppRyDBodega_Pro.dto.KardexDTO;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class PdfGenerator {

    public static void generarKardexPDF(List<KardexDTO> listaKardex, OutputStream outputStream) {
        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);

            // ⬅ Cambiar orientación a horizontal
            pdfDoc.setDefaultPageSize(com.itextpdf.kernel.geom.PageSize.A4.rotate());

            Document document = new Document(pdfDoc);

            // LOGO (ajusta la ruta a tu imagen en resources/static/img/logo.png)
            try {
                Image logo = new Image(ImageDataFactory.create("src/main/resources/static/img/logo.png"));
                logo.setWidth(100);
                logo.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);
                document.add(logo);
            } catch (Exception e) {
                System.out.println("No se pudo cargar el logo: " + e.getMessage());
            }

            // Título centrado
            document.add(new Paragraph("KARDEX DE PRODUCTOS")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(10f)
                    .setMarginBottom(20f)
            );

            // Tabla con 14 columnas iguales y ancho 100%
            float[] columnWidths = new float[14];
            Arrays.fill(columnWidths, 1);
            Table table = new Table(columnWidths);
            table.setWidth(UnitValue.createPercentValue(100));
            table.setKeepTogether(false); // permite dividir en páginas si es necesario

            String[] headers = {
                    "Producto", "Precio Compra", "Precio Venta", "Costo Promedio",
                    "Stock Actual", "Stock Min", "Categoría", "Proveedor",
                    "Estado", "Tipo Movimiento", "Cantidad", "Precio",
                    "Fecha", "Observaciones"
            };

            // Cabeceras con fondo gris y fuente más pequeña
            for (String header : headers) {
                Cell cell = new Cell().add(new Paragraph(header)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(8f)
                );
                cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                cell.setTextAlignment(TextAlignment.CENTER);
                table.addHeaderCell(cell);
            }

            // Filas con color alterno y fuente más pequeña
            boolean gris = false;
            for (KardexDTO k : listaKardex) {
                gris = !gris;
                com.itextpdf.kernel.colors.Color rowColor = gris ? ColorConstants.WHITE : ColorConstants.LIGHT_GRAY;

                table.addCell(new Cell().add(new Paragraph(k.getNombreProducto()).setFontSize(8f)).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getPrecioCompra().toString()).setFontSize(8f)).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getPrecioVenta().toString()).setFontSize(8f)).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getCostoPromedio().toString()).setFontSize(8f)).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(k.getStockActual())).setFontSize(8f)).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(k.getStockMinimo())).setFontSize(8f)).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getCategoria()).setFontSize(8f)).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getProveedor()).setFontSize(8f)).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getEstadoStock()).setFontSize(8f)).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getTipoMovimiento()).setFontSize(8f)).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(k.getCantidad())).setFontSize(8f)).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getPrecioMovimiento().toString()).setFontSize(8f)).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getFecha().toString()).setFontSize(8f)).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getObservaciones() != null ? k.getObservaciones() : "").setFontSize(8f)).setBackgroundColor(rowColor));
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
