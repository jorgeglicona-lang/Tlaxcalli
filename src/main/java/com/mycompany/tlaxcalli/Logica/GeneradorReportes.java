/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tlaxcalli.Logica;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javax.swing.table.DefaultTableModel;
public class GeneradorReportes {
    
    public void crearReporteVentas(String rutaArchivo, DefaultTableModel modeloTabla, String totalDia) {
        try {
            // 1. CREAMOS EL DOCUMENTO (La hoja en blanco)
            Document documento = new Document();
            
            // 2. PREPARAMOS EL LÁPIZ (El Writer)
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            
            // 3. ABRIMOS EL DOCUMENTO PARA ESCRIBIR
            documento.open();
            
            // --- AQUI EMPIEZA EL DISEÑO ---
            
            // Título
            Paragraph titulo = new Paragraph("Reporte de Ventas - Tlaxcalli");
            titulo.setAlignment(Paragraph.ALIGN_CENTER);
            documento.add(titulo);
            
            documento.add(new Paragraph(" ")); // Espacio en blanco
            documento.add(new Paragraph("Fecha de impresión: " + new java.util.Date()));
            documento.add(new Paragraph(" ")); // Espacio
            
            // Tabla PDF (Le decimos cuántas columnas tiene tu tabla visual)
            PdfPTable tablaPDF = new PdfPTable(modeloTabla.getColumnCount());
            
            // A. AGREGAR ENCABEZADOS
            for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                tablaPDF.addCell(modeloTabla.getColumnName(i));
            }
            
            // B. AGREGAR LOS DATOS (Filas)
            for (int filas = 0; filas < modeloTabla.getRowCount(); filas++) {
                for (int cols = 0; cols < modeloTabla.getColumnCount(); cols++) {
                    tablaPDF.addCell(modeloTabla.getValueAt(filas, cols).toString());
                }
            }
            
            documento.add(tablaPDF);
            
            documento.add(new Paragraph(" "));
            Paragraph total = new Paragraph("Total Generado: " + totalDia);
            total.setAlignment(Paragraph.ALIGN_RIGHT);
            documento.add(total);
            
            // --- FIN DEL DISEÑO ---
            
            documento.close();
            System.out.println("PDF Creado exitosamente!");
            
        } catch (Exception e) {
            System.err.println("Error creando PDF: " + e.getMessage());
        }
    }
    
}
