/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ometeotl.tlaxcalli.LOGICA;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.File;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;

public class GeneradorReportes {
    
    public void crearDocumento(String fInicio, String fFin, String empleado, 
                               DefaultTableModel vtas, DefaultTableModel gOp, DefaultTableModel gAdm, 
                               double tV, double tGOp, double tGAdm) {
    
        Document documento = new Document(PageSize.A4);
    
        try {
            // 1. Ubicación del archivo (Ventana para guardar)
            String ruta = "";
            JFileChooser jf = new JFileChooser();
            if (jf.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                ruta = jf.getSelectedFile().getAbsolutePath() + ".pdf";
            }

            PdfWriter.getInstance(documento, new FileOutputStream(ruta));
            documento.open();

            // --- ESTILOS ---
            Font tituloF = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font subTituloF = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLUE);
            Font negrita = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

            // 2. ENCABEZADO
            Paragraph titulo = new Paragraph("TLAXCALLI - REPORTE DE ACTIVIDAD", tituloF);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(new Paragraph("Periodo: " + fInicio + " al " + fFin));
            documento.add(new Paragraph("Filtro Empleado: " + empleado));
            documento.add(Chunk.NEWLINE);

            // 3. SECCIÓN 1: RESUMEN EJECUTIVO (Caja de Totales)
            documento.add(new Paragraph("1. RESUMEN FINANCIERO", subTituloF));
            PdfPTable tablaResumen = new PdfPTable(2);
            tablaResumen.setWidthPercentage(100);
        
            double totalGastos = tGOp + tGAdm;
            double neto = tV - totalGastos;

            agregarCeldaResumen(tablaResumen, "Ventas Totales:", String.format("$%.2f", tV));
            agregarCeldaResumen(tablaResumen, "Gastos Totales (Adm + Op):", String.format("$%.2f", totalGastos));
            agregarCeldaResumen(tablaResumen, "GANANCIA NETA:", String.format("$%.2f", neto));
        
            documento.add(tablaResumen);
            documento.add(Chunk.NEWLINE);

            // 4. SECCIÓN 2: GASTOS ADMINISTRATIVOS (Materia Prima/Fijos)
            documento.add(new Paragraph("2. DESGLOSE ADMINISTRATIVO", subTituloF));
            PdfPTable tablaAdm = crearTablaDesdeModelo(gAdm, new String[]{"ID", "Descripción", "Monto ($)", "Fecha"});
            documento.add(tablaAdm);
            documento.add(new Paragraph("Subtotal Administrativo: $" + String.format("%.2f", tGAdm), negrita));
            documento.add(Chunk.NEWLINE);

            // 5. SECCIÓN 3: ACTIVIDAD OPERATIVA (Ventas y Gastos por Trabajador)
            documento.add(new Paragraph("3. ACTIVIDAD OPERATIVA (MOSTRADOR/REPARTIDORES)", subTituloF));
        
            documento.add(new Paragraph("Ventas registradas:", negrita));
            documento.add(crearTablaDesdeModelo(vtas, new String[]{"Empleado", "Producto", "Monto ($)"}));
        
            documento.add(new Paragraph("Gastos operativos registrados:", negrita));
            documento.add(crearTablaDesdeModelo(gOp, new String[]{"Empleado", "Descripción", "Monto ($)"}));
        
            documento.add(new Paragraph("Subtotal Operativo: $" + String.format("%.2f", tGOp), negrita));

            documento.add(new Paragraph("\n--- Fin del Reporte ---"));
            documento.close();
       
             java.awt.Desktop.getDesktop().open(new File(ruta)); // Abre el PDF automáticamente
        
        } catch (Exception e) {
            System.err.println("Error al dibujar PDF: " + e.getMessage());
        }
    }
        // Crea celdas elegantes para el resumen
    private void agregarCeldaResumen(PdfPTable tabla, String texto, String valor) {
        tabla.addCell(new Phrase(texto));
        PdfPCell celdaValor = new PdfPCell(new Phrase(valor));
        celdaValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(celdaValor);
    }
    
    // Convierte cualquier DefaultTableModel en una tabla de iText
    private PdfPTable crearTablaDesdeModelo(DefaultTableModel modelo, String[] titulos) throws DocumentException {
        PdfPTable tabla = new PdfPTable(titulos.length);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(10f);

        // Cabezales de la tabla
        for (String titulo : titulos) {
            PdfPCell h = new PdfPCell(new Phrase(titulo, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE)));
            h.setBackgroundColor(BaseColor.DARK_GRAY);
            h.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(h);
        }
    
        // Datos
        for (int i = 0; i < modelo.getRowCount(); i++) {
            for (int j = 0; j < modelo.getColumnCount(); j++) {
                tabla.addCell(modelo.getValueAt(i, j).toString());
            }
        }
        return tabla;
    }
    
}
