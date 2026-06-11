package com.ometeotl.tlaxcalli.LOGICA;

import com.github.lgooddatepicker.components.DatePicker;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IGastosGeneralesDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IReportesDAO;
import java.io.File;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
public class GeneradorReportes {
    
    private final IReportesDAO reportesDao = DAOFactory.getReportesDAO();
    private final IGastosGeneralesDAO gastosGeneralesDao = DAOFactory.getGastosGeneralesDAO();

    public void prepararPDF(DatePicker FI, DatePicker FF, JComboBox EF) {
        // 1. Creamos modelos temporales para extraer la info de los DAOs
        DefaultTableModel modeloVentas = new DefaultTableModel();
        DefaultTableModel modeloGastosOp = new DefaultTableModel();
        String empleadoFiltro = EF.getSelectedItem().toString();
        String fechaInicio = FI.getDate().toString();
        String fechaFin = FF.getDate().toString();
        DefaultTableModel modeloGastosAdm = gastosGeneralesDao.obtenerGastosPorRango(fechaInicio, fechaFin);
        
        modeloVentas.addColumn("Empleado");
        modeloVentas.addColumn("Producto");
        modeloVentas.addColumn("Monto ($)");

        modeloGastosOp.addColumn("Empleado");
        modeloGastosOp.addColumn("Descripción");
        modeloGastosOp.addColumn("Monto ($)");

        // 2. Ejecutamos las consultas portátiles desde SQLite
        double totalV = reportesDao.llenarVentas(empleadoFiltro, fechaInicio, fechaFin, modeloVentas);
        double totalGOp = reportesDao.llenarGastos(empleadoFiltro, fechaInicio, fechaFin, modeloGastosOp);
        
        // 3. Calculamos totales generales de la tabla administrativa
        double totalGAdm = 0;

        for (int i = 0; i < modeloGastosAdm.getRowCount(); i++) {
            Object valorCelda = modeloGastosAdm.getValueAt(i, 2);
            if (valorCelda != null && !valorCelda.toString().trim().isEmpty()) {
                try {
                    totalGAdm += Double.parseDouble(valorCelda.toString().trim());
                } catch (NumberFormatException e) {
                    System.err.println("⚠️ Advertencia: Valor no numérico ignorado en la fila " + i);
                }
            }
        }
        // 4. Llamamos al generador de PDF enviando toda la información recolectada
        GeneradorReportes pdf = new GeneradorReportes();
        pdf.crearDocumento(fechaInicio, fechaFin, empleadoFiltro, modeloVentas, modeloGastosOp, modeloGastosAdm,
                           totalV, totalGOp, totalGAdm);
    }
    
    public void crearDocumento(String fInicio, String fFin, String empleado, 
                               DefaultTableModel vtas, DefaultTableModel gOp, DefaultTableModel gAdm, 
                               double tV, double tGOp, double tGAdm) {
        // 1. Configuración de carpetas y nombre (Automatización intacta)
        String userHome = System.getProperty("user.home");
        File carpetaReportes = new File(userHome + File.separator + "Documents" + File.separator + "Tlaxcalli_Reportes");
        
        if (!carpetaReportes.exists()) {
            carpetaReportes.mkdirs(); 
        }

        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter formatoFechaHora = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        String fechaHoraStr = ahora.format(formatoFechaHora);
        String empleadoLimpio = empleado.trim().replace(" ", "_");
        String nombreFinalArchivo = "Reporte_" + empleadoLimpio + "_" + fechaHoraStr + ".pdf";
        
        java.io.File archivoPDF = new java.io.File(carpetaReportes, nombreFinalArchivo);
        String rutaFinal = archivoPDF.getAbsolutePath();
        
        // 🛡️ MOTOR DE ITEXT 9.6.0
        try {
            PdfWriter writer = new PdfWriter(rutaFinal);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document documento = new Document(pdfDoc, PageSize.A4);

            // Carga de fuentes base
            PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont fontNormal = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            // ==========================================
            // 🖼️ ENCABEZADO CON LOGO
            // ==========================================
            Table tablaEncabezado = new Table(UnitValue.createPercentArray(new float[]{1f, 4f}));
            tablaEncabezado.setWidth(UnitValue.createPercentValue(100));

            try {
                java.net.URL urlLogo = getClass().getResource("/imagen/transparencia.png");
                if (urlLogo != null) {
                    Image logo = new Image(ImageDataFactory.create(urlLogo));
                    logo.scaleToFit(45, 45);
                    
                    Cell celdaLogo = new Cell().add(logo);
                    celdaLogo.setBorder(com.itextpdf.layout.borders.Border.NO_BORDER);
                    celdaLogo.setTextAlignment(TextAlignment.CENTER);
                    celdaLogo.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    tablaEncabezado.addCell(celdaLogo);
                } else {
                    tablaEncabezado.addCell(new Cell().setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));
                }
            } catch (Exception ex) {
                System.err.println("No se pudo cargar el logo: " + ex.getMessage());
            }

            // Textos del encabezado
            Cell celdaTextos = new Cell().setBorder(com.itextpdf.layout.borders.Border.NO_BORDER);
            
            celdaTextos.add(new Paragraph("TLAXCALLI - REPORTE DE ACTIVIDAD")
                    .setFont(fontBold).setFontSize(18).setTextAlignment(TextAlignment.CENTER));
            
            celdaTextos.add(new Paragraph("Periodo: " + fInicio + " al " + fFin)
                    .setFont(fontNormal).setFontSize(10).setTextAlignment(TextAlignment.CENTER));
            
            celdaTextos.add(new Paragraph("Filtro Empleado: " + empleado)
                    .setFont(fontNormal).setFontSize(10).setTextAlignment(TextAlignment.CENTER));

            tablaEncabezado.addCell(celdaTextos);
            documento.add(tablaEncabezado);
            documento.add(new Paragraph("\n")); 

            // ==========================================
            // 📊 SECCIÓN 1: RESUMEN FINANCIERO
            // ==========================================
            documento.add(new Paragraph("1. RESUMEN FINANCIERO")
                    .setFont(fontBold).setFontSize(16).setFontColor(ColorConstants.BLUE));
            
            Table tablaResumen = new Table(UnitValue.createPercentArray(2));
            tablaResumen.setWidth(UnitValue.createPercentValue(100));
        
            double totalGastos = tGOp + tGAdm;
            double neto = tV - totalGastos;

            agregarCeldaResumen(tablaResumen, "Ventas Totales:", String.format("$%.2f", tV), fontNormal, fontBold);
            agregarCeldaResumen(tablaResumen, "Gastos Totales (Adm + Op):", String.format("$%.2f", totalGastos), fontNormal, fontBold);
            agregarCeldaResumen(tablaResumen, "GANANCIA NETA:", String.format("$%.2f", neto), fontNormal, fontBold);
        
            documento.add(tablaResumen);
            documento.add(new Paragraph("\n"));

            // ==========================================
            // 🏢 SECCIÓN 2: DESGLOSE ADMINISTRATIVO
            // ==========================================
            documento.add(new Paragraph("2. DESGLOSE ADMINISTRATIVO")
                    .setFont(fontBold).setFontSize(16).setFontColor(ColorConstants.BLUE));
            
            Table tablaAdm = crearTablaDesdeModelo(gAdm, new String[]{"ID", "Descripción", "Monto ($)", "Fecha"}, fontNormal, fontBold);
            documento.add(tablaAdm);
            
            documento.add(new Paragraph("Subtotal Administrativo: $" + String.format("%.2f", tGAdm))
                    .setFont(fontBold).setFontSize(18));
            documento.add(new Paragraph("\n"));

            // ==========================================
            // 🛵 SECCIÓN 3: ACTIVIDAD OPERATIVA
            // ==========================================
            documento.add(new Paragraph("3. ACTIVIDAD OPERATIVA (MOSTRADOR/REPARTIDORES)")
                    .setFont(fontBold).setFontSize(16).setFontColor(ColorConstants.BLUE));
        
            documento.add(new Paragraph("Ventas registradas:").setFont(fontBold).setFontSize(10));
            documento.add(crearTablaDesdeModelo(vtas, new String[]{"Empleado", "Producto", "Monto ($)"}, fontNormal, fontBold));
        
            documento.add(new Paragraph("Gastos operativos registrados:").setFont(fontBold).setFontSize(10));
            documento.add(crearTablaDesdeModelo(gOp, new String[]{"Empleado", "Descripción", "Monto ($)"}, fontNormal, fontBold));
        
            documento.add(new Paragraph("Subtotal Operativo: $" + String.format("%.2f", tGOp))
                    .setFont(fontBold).setFontSize(10));

            documento.add(new Paragraph("\n--- Fin del Reporte ---").setTextAlignment(TextAlignment.CENTER));
            
            // Cierre maestro del PDF
            documento.close();
            
            // Apertura automática
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(archivoPDF);
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "✅ Reporte guardado en:\n" + rutaFinal);
            }
            
        } catch (Exception e) {
            System.err.println("Error procesando iText 9: " + e.getMessage());
            javax.swing.JOptionPane.showMessageDialog(null, "❌ Error generando PDF:\n" + e.getMessage());
        }
    }

    private void agregarCeldaResumen(Table tabla, String texto, String valor, PdfFont normal, PdfFont bold) {
        tabla.addCell(new Cell().add(new Paragraph(texto).setFont(normal)));
        
        // La alineación en la celda se hace con TextAlignment
        Cell celdaValor = new Cell().add(new Paragraph(valor).setFont(bold));
        celdaValor.setTextAlignment(TextAlignment.RIGHT); 
        tabla.addCell(celdaValor);
    }
    
    private Table crearTablaDesdeModelo(DefaultTableModel modelo, String[] titulos, PdfFont normal, PdfFont bold) {
        Table tabla = new Table(UnitValue.createPercentArray(titulos.length));
        tabla.setWidth(UnitValue.createPercentValue(100));
        tabla.setMarginTop(10f);

        // Cabeceras (Background gris oscuro, texto blanco centrado)
        for (String titulo : titulos) {
            Cell h = new Cell().add(new Paragraph(titulo).setFont(bold).setFontColor(ColorConstants.WHITE));
            h.setBackgroundColor(ColorConstants.DARK_GRAY);
            h.setTextAlignment(TextAlignment.CENTER);
            tabla.addCell(h);
        }
    
        // Inyección de celdas a partir de su JTable
        for (int i = 0; i < modelo.getRowCount(); i++) {
            for (int j = 0; j < modelo.getColumnCount(); j++) {
                Object celda = modelo.getValueAt(i, j);
                tabla.addCell(new Cell().add(new Paragraph(celda != null ? celda.toString() : "").setFont(normal)));
            }
        }
        return tabla;
    }

    
}
