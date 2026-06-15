package com.ometeotl.tlaxcalli.LOGICA;

import com.github.lgooddatepicker.components.DatePicker;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import static com.itextpdf.io.font.constants.StandardFonts.HELVETICA;
import static com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD;
import static com.itextpdf.kernel.colors.ColorConstants.BLUE;
import static com.itextpdf.kernel.colors.ColorConstants.DARK_GRAY;
import static com.itextpdf.kernel.colors.ColorConstants.WHITE;
import static com.itextpdf.kernel.geom.PageSize.A4;
import static com.itextpdf.layout.borders.Border.NO_BORDER;
import static com.itextpdf.layout.properties.TextAlignment.CENTER;
import static com.itextpdf.layout.properties.TextAlignment.RIGHT;
import com.itextpdf.layout.properties.UnitValue;
import static com.itextpdf.layout.properties.VerticalAlignment.MIDDLE;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IGastosGeneralesDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IReportesDAO;
import java.awt.Desktop;
import java.io.File;
import static java.io.File.separator;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JComboBox;
import static javax.swing.JOptionPane.showMessageDialog;
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
        File carpetaReportes = new File(userHome + separator + "Documents" + separator + "Tlaxcalli_Reportes");
        
        if (!carpetaReportes.exists()) {
            carpetaReportes.mkdirs(); 
        }

        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        String fechaHoraStr = ahora.format(formatoFechaHora);
        String empleadoLimpio = empleado.trim().replace(" ", "_");
        String nombreFinalArchivo = "Reporte_" + empleadoLimpio + "_" + fechaHoraStr + ".pdf";
        
        File archivoPDF = new File(carpetaReportes, nombreFinalArchivo);
        String rutaFinal = archivoPDF.getAbsolutePath();
        
        // 🛡️ MOTOR DE ITEXT 9.6.0
        try (PdfWriter writer = new PdfWriter(rutaFinal);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document documento = new Document(pdfDoc,A4);){
            
            // Carga de fuentes base
            PdfFont fontBold = PdfFontFactory.createFont(HELVETICA_BOLD);
            PdfFont fontNormal = PdfFontFactory.createFont(HELVETICA);

            // ENCABEZADO
            Table tablaEncabezado = new Table(UnitValue.createPercentArray(new float[]{1f, 4f}));
            tablaEncabezado.setWidth(UnitValue.createPercentValue(100));

            try {
                URL urlLogo = getClass().getResource("/imagen/transparencia.png");
                if (urlLogo != null) {
                    Image logo = new Image(ImageDataFactory.create(urlLogo));
                    logo.scaleToFit(45, 45);
                    
                    Cell celdaLogo = new Cell().add(logo);
                    celdaLogo.setBorder(NO_BORDER);
                    celdaLogo.setTextAlignment(CENTER);
                    celdaLogo.setVerticalAlignment(MIDDLE);
                    tablaEncabezado.addCell(celdaLogo);
                } else {
                    tablaEncabezado.addCell(new Cell().setBorder(NO_BORDER));
                }
            } catch (Exception ex) {
                System.err.println("No se pudo cargar el logo: " + ex.getMessage());
            }

            // Textos del encabezado
            Cell celdaTextos = new Cell().setBorder(NO_BORDER);
            
            celdaTextos.add(new Paragraph("TLAXCALLI - REPORTE DE ACTIVIDAD")
                    .setFont(fontBold).setFontSize(18).setTextAlignment(CENTER));
            
            celdaTextos.add(new Paragraph("Periodo: " + fInicio + " al " + fFin)
                    .setFont(fontNormal).setFontSize(10).setTextAlignment(CENTER));
            
            celdaTextos.add(new Paragraph("Filtro Empleado: " + empleado)
                    .setFont(fontNormal).setFontSize(10).setTextAlignment(CENTER));

            tablaEncabezado.addCell(celdaTextos);
            documento.add(tablaEncabezado);
            documento.add(new Paragraph("\n")); 

            // RESUMEN FINANCIERO
            documento.add(new Paragraph("1. RESUMEN FINANCIERO")
                    .setFont(fontBold).setFontSize(15).setFontColor(BLUE));
            
            Table tablaResumen = new Table(UnitValue.createPercentArray(2));
            tablaResumen.setWidth(UnitValue.createPercentValue(100));
        
            double totalGastos = tGOp + tGAdm;
            double neto = tV - totalGastos;

            agregarCeldaResumen(tablaResumen, "Ventas Totales:", String.format("$%.2f", tV), fontNormal, fontBold);
            agregarCeldaResumen(tablaResumen, "Gastos Totales (Adm + Op):", String.format("$%.2f", totalGastos), fontNormal, fontBold);
            agregarCeldaResumen(tablaResumen, "GANANCIA NETA:", String.format("$%.2f", neto), fontNormal, fontBold);
        
            documento.add(tablaResumen);
            documento.add(new Paragraph("\n"));

            // DESGLOSE ADMINISTRATIVO
            documento.add(new Paragraph("2. DESGLOSE ADMINISTRATIVO")
                    .setFont(fontBold).setFontSize(15).setFontColor(BLUE));
            
            Table tablaAdm = crearTablaDesdeModelo(gAdm, new String[]{"ID", "Descripción", "Monto ($)", "Fecha"}, fontNormal, fontBold);
            documento.add(tablaAdm);
            
            documento.add(new Paragraph("Subtotal Administrativo: $" + String.format("%.2f", tGAdm))
                    .setFont(fontBold).setFontSize(12));
            documento.add(new Paragraph("\n"));

            // ==========================================
            // 🛵 SECCIÓN 3: ACTIVIDAD OPERATIVA
            // ==========================================
            documento.add(new Paragraph("3. ACTIVIDAD OPERATIVA (MOSTRADOR/REPARTIDORES)")
                    .setFont(fontBold).setFontSize(15).setFontColor(BLUE));
        
            documento.add(new Paragraph("Ventas registradas:").setFont(fontBold).setFontSize(14));
            documento.add(crearTablaDesdeModelo(vtas, new String[]{"Empleado", "Producto", "Monto ($)"}, fontNormal, fontBold));
        
            documento.add(new Paragraph("Gastos operativos registrados:").setFont(fontBold).setFontSize(14));
            documento.add(crearTablaDesdeModelo(gOp, new String[]{"Empleado", "Descripción", "Monto ($)"}, fontNormal, fontBold));
        
            documento.add(new Paragraph("Subtotal Operativo: $" + String.format("%.2f", tGOp))
                    .setFont(fontBold).setFontSize(12));

            documento.add(new Paragraph("\n--- Fin del Reporte ---").setTextAlignment(CENTER));
            
            // Cierre maestro del PDF
            documento.close();
            
            // Apertura automática
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivoPDF);
            } else {
                showMessageDialog(null, "✅ Reporte guardado en:\n" + rutaFinal);
            }
            
        } catch (Exception e) {
            System.err.println("Error procesando iText 9: " + e.getMessage());
            showMessageDialog(null, "❌ Error generando PDF:\n" + e.getMessage());
        }
    }

    private void agregarCeldaResumen(Table tabla, String texto, String valor, PdfFont normal, PdfFont bold) {
        tabla.addCell(new Cell().add(new Paragraph(texto).setFont(normal)));
        
        Cell celdaValor = new Cell().add(new Paragraph(valor).setFont(bold));
        celdaValor.setTextAlignment(RIGHT); 
        tabla.addCell(celdaValor);
    }
    
    private Table crearTablaDesdeModelo(DefaultTableModel modelo, String[] titulos, PdfFont normal, PdfFont bold) {
        Table tabla = new Table(UnitValue.createPercentArray(titulos.length));
        tabla.setWidth(UnitValue.createPercentValue(100));
        tabla.setMarginTop(10f);

        // Cabeceras (Background gris oscuro, texto blanco centrado)
        for (String titulo : titulos) {
            Cell h = new Cell().add(new Paragraph(titulo).setFont(bold).setFontColor(WHITE));
            h.setBackgroundColor(DARK_GRAY);
            h.setTextAlignment(CENTER);
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
