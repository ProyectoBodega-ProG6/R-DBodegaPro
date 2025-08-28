package org.esfe.AppRyDBodega_Pro.servicios.utilerias;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
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
import com.itextpdf.layout.properties.TextAlignment;
import org.esfe.AppRyDBodega_Pro.dto.KardexDTO;

import java.io.OutputStream;
import java.util.List;

public class PdfGenerator {

    public static void generarKardexPDF(List<KardexDTO> listaKardex, OutputStream outputStream) {
        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
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

            // Tabla con 14 columnas
            float[] columnWidths = {100, 50, 50, 50, 40, 40, 60, 60, 50, 60, 40, 60, 60, 100};
            Table table = new Table(columnWidths);

            String[] headers = {
                    "Producto", "Precio Compra", "Precio Venta", "Costo Promedio",
                    "Stock Actual", "Stock Min", "Categoría", "Proveedor",
                    "Estado", "Tipo Movimiento", "Cantidad", "Precio",
                    "Fecha", "Observaciones"
            };

            // Cabeceras con fondo gris
            for (String header : headers) {
                Cell cell = new Cell().add(new Paragraph(header)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(10f)
                );
                cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                cell.setTextAlignment(TextAlignment.CENTER);
                table.addHeaderCell(cell);
            }

            // Filas con color alterno
            boolean gris = false;
            for (KardexDTO k : listaKardex) {
                gris = !gris;
                Color rowColor = gris ? ColorConstants.WHITE : ColorConstants.LIGHT_GRAY;

                table.addCell(new Cell().add(new Paragraph(k.getNombreProducto())).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getPrecioCompra().toString())).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getPrecioVenta().toString())).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getCostoPromedio().toString())).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(k.getStockActual()))).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(k.getStockMinimo()))).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getCategoria())).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getProveedor())).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getEstadoStock())).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getTipoMovimiento())).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(k.getCantidad()))).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getPrecioMovimiento().toString())).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getFecha().toString())).setBackgroundColor(rowColor));
                table.addCell(new Cell().add(new Paragraph(k.getObservaciones() != null ? k.getObservaciones() : "")).setBackgroundColor(rowColor));
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
