package com.ometeotl.tlaxcalli.LOGICA;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.util.List;

public class C_NR {
    
    private double precioReparto = 0.0;
    private double precioMostrador = 0.0;
    private double precioMasa = 0.0;
    
    public void cargarPreciosBase() {
        IVentasDAO dao = DAOFactory.getVentasDAO();
        precioReparto = dao.obtenerPrecioProducto(1);
        precioMostrador = dao.obtenerPrecioProducto(2);
        precioMasa = dao.obtenerPrecioProducto(3);
        
        // Respaldo de seguridad por si la base de datos falla al leer
        if (precioReparto == 0) precioReparto = 19.0;
        if (precioMostrador == 0) precioMostrador = 22.0;
        if (precioMasa == 0) precioMasa = 20.0;
    }
    // 1. LLENADO DE COMBOBOXES (Sin tocar la vista)
    public void inicializarCombos(JComboBox<Object> boxRepartidor, JComboBox<Object> cbProductos, JComboBox<Object> cbGastos) {
        // --- Empleados ---
        // Asumiendo que su EmpleadosDAO original o SQLiteDAO ya está en la fábrica
        IEmpleadosDAO empDao = DAOFactory.getEmpleadosDAO();
        
        // 🛠️ ¡REPARADO! Ahora llamamos al método exclusivo de vendedores
        DefaultTableModel modeloEmp = empDao.consultarVendedores(); 
        
        boxRepartidor.addItem(new EmpleadoItem(0, "Seleccione..."));
        for (int i = 0; i < modeloEmp.getRowCount(); i++) {
            int id = Integer.parseInt(modeloEmp.getValueAt(i, 0).toString());
            String nombre = modeloEmp.getValueAt(i, 1).toString();
            boxRepartidor.addItem(new EmpleadoItem(id, nombre));
        }

        // --- Productos ---
        IVentasDAO ventasDao = DAOFactory.getVentasDAO();
        List<ProductoItem> prods = ventasDao.obtenerProductosParaVenta();
        cbProductos.addItem(new ProductoItem(0, "Seleccionar...", 0, false));
        for(ProductoItem p : prods) cbProductos.addItem(p);

        // --- Gastos ---
        List<GastoItem> gastos = ventasDao.obtenerGastosParaVenta();
        cbGastos.addItem(new GastoItem(0, "Seleccionar...", false));
        for(GastoItem g : gastos) cbGastos.addItem(g);
    }

    // 2. MATEMÁTICAS EN TIEMPO REAL
    public void calcularTotalAPagar(JTextField tReparto, JTextField tVenta, JTextField tMasa, JTable tProd, JTable tGastos, JLabel cEntregar) {
        double totalIngresos = 0.0;
        double totalGastos = 0.0;
        
        try {
            if (!tReparto.getText().isEmpty()) totalIngresos += Double.parseDouble(tReparto.getText()) * precioReparto;
            if (!tVenta.getText().isEmpty()) totalIngresos += Double.parseDouble(tVenta.getText()) * precioMostrador;
            if (!tMasa.getText().isEmpty()) totalIngresos += Double.parseDouble(tMasa.getText()) * precioMasa;
            
            DefaultTableModel modeloProd = (DefaultTableModel) tProd.getModel();
            for (int i = 0; i < modeloProd.getRowCount(); i++) {
                Object valor = modeloProd.getValueAt(i, 4); 
                if (valor != null) totalIngresos += Double.parseDouble(valor.toString());
            }

            DefaultTableModel modeloGastos = (DefaultTableModel) tGastos.getModel();
            for (int i = 0; i < modeloGastos.getRowCount(); i++) {
                Object valor = modeloGastos.getValueAt(i, 1);
                if (valor != null) totalGastos += Double.parseDouble(valor.toString());
            }
        } catch (NumberFormatException e) { /* Ignorar errores mientras teclean */ }

        double totalFinal = totalIngresos - totalGastos;
        cEntregar.setText("Total a Entregar: $" + String.format("%.2f", totalFinal));
        cEntregar.setForeground(totalFinal < 0 ? Color.RED : Color.BLACK);
    }

    // 3. LÓGICA DE REPARTIDOR VS MOSTRADOR
    public void procesarSeleccionRepartidor(JFrame parent, JComboBox<Object> boxRepartidor, 
                                            JTextField tReparto, JTextField tVenta, JTextField tMasa, 
                                            JRadioButton sMasa, JCheckBox Pro, JCheckBox Gas,
                                            JTable tProd, JTable tGastos, JLabel cEntregar, 
                                            JComboBox cb_gas, JComboBox cb_pro) { // 🚀 NUEVOS PARÁMETROS

        if (boxRepartidor.getSelectedItem() == null) return;
        
        Object item = boxRepartidor.getSelectedItem();
        int idEmpleado = 0;
        if (item instanceof EmpleadoItem) {
            idEmpleado = ((EmpleadoItem) item).getId();
        }
        
        String seleccionado = item.toString();
        DefaultTableModel modeloProd = (DefaultTableModel) tProd.getModel();
        DefaultTableModel modeloGastos = (DefaultTableModel) tGastos.getModel();
        
        // Limpieza inicial estándar
        tReparto.setText(""); tVenta.setText(""); tMasa.setText("");
        tReparto.setBackground(Color.WHITE); tVenta.setBackground(Color.WHITE); tMasa.setBackground(Color.WHITE);
        tVenta.setForeground(Color.BLACK);
        modeloProd.setRowCount(0);
        modeloGastos.setRowCount(0);
        
        if (seleccionado.equalsIgnoreCase("Seleccione...")) {
            tReparto.setEnabled(false); tVenta.setEnabled(false); tMasa.setEnabled(false);
            calcularTotalAPagar(tReparto, tVenta, tMasa, tProd, tGastos, cEntregar);
            return;
        }
        
        IVentasDAO ventasDao = DAOFactory.getVentasDAO();
        double[] corteExistente = ventasDao.obtenerCorteEmpleadoHoy(idEmpleado);
        
        if (corteExistente != null) {
            // 🛑 ¡SÍ YA EXISTE REGISTRO! Cargamos textos principales
            tReparto.setText(String.format("%.2f", corteExistente[0]).replace(",", "."));
            tVenta.setText(String.format("%.2f", corteExistente[1]).replace(",", "."));
            tMasa.setText(String.format("%.2f", corteExistente[2]).replace(",", "."));
            
            Color amarilloAlerta = new Color(255, 255, 200);
            tReparto.setBackground(amarilloAlerta);
            tVenta.setBackground(amarilloAlerta); 
            tMasa.setBackground(amarilloAlerta);
            // 1. REGLA DE REPARTO/VENTA: Solo el Repartidor puede editar. El Mostrador se queda bloqueado.
            if (!seleccionado.equalsIgnoreCase("Mostrador")) {
                tReparto.setEnabled(true);
                tReparto.setEditable(true);
                tVenta.setEnabled(true);   
                tVenta.setEditable(true);
                tReparto.setDisabledTextColor(Color.BLACK);
            } else {
                tReparto.setEnabled(false);
                tVenta.setEnabled(false);
                tReparto.setDisabledTextColor(Color.BLACK);
            }
            
            // 2. REGLA DE LA MASA: Leemos el texto de la caja (no el objeto). 
            // Si tiene más de 0 kilos, la habilitamos.
            String textoMasa = tMasa.getText();
            if (!textoMasa.equals("0.00") && !textoMasa.equals("0.0") && !textoMasa.isEmpty()) {
                tMasa.setEnabled(true);   
                tMasa.setEditable(true);
                sMasa.setSelected(true);
            } else {
                tMasa.setEnabled(false);
            }
            // 🚀 🛠️ ¡REPARADO! Llenamos las tablas visuales de productos adicionales y gastos
            List<Object[]> productosHoy = ventasDao.obtenerProductosCorteHoy(idEmpleado);
            for (Object[] fila : productosHoy) {
                modeloProd.addRow(fila);
            }
            
            List<Object[]> gastosHoy = ventasDao.obtenerGastosCorteHoy(idEmpleado);
            for (Object[] fila : gastosHoy) {
                modeloGastos.addRow(fila);
            }
            if(modeloGastos.getRowCount()>0){
                Gas.setSelected(true);
                cb_gas.setEnabled(true);
                tGastos.setEnabled(true);
            }
            if(modeloProd.getRowCount()>0){
                Pro.setSelected(true);
                cb_pro.setEnabled(true);
                tProd.setEnabled(true);
            }
            
            // Forzamos el recalculo inmediato del dinero para que coincida con las tablas cargadas
            calcularTotalAPagar(tReparto, tVenta, tMasa, tProd, tGastos, cEntregar);
            JOptionPane.showMessageDialog(parent, 
                "ℹ️ REGISTRO LOCALIZADO:\nEl empleado " + seleccionado + " ya cuenta con un corte hoy.\nSe han restaurado todos sus datos, productos y gastos en las tablas.", 
                "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        

        // 🟢 ESCENARIO B: NO HAY REGISTRO PREVIO (Su lógica normal)
        if (seleccionado.equalsIgnoreCase("Mostrador")) {
            IMolinoDAO molinoDao = DAOFactory.getMolinoDAO();
            double produccionTotal = molinoDao.obtenerTotalTortillaHoy();
            
            if (produccionTotal <= 0) {
                JOptionPane.showMessageDialog(parent, "⚠️ ACCIÓN BLOQUEADA:\nNo se puede calcular el Mostrador porque el registro de Molino está vacío.\nPor favor, registre primero la producción del día.");
                boxRepartidor.setSelectedIndex(0); 
                return; 
            }

            tReparto.setEditable(false); tReparto.setBackground(new Color(220, 220, 220));
            tVenta.setEditable(false);   tVenta.setBackground(new Color(220, 220, 220)); 
            tMasa.setEnabled(true);     tMasa.setEditable(true);
            
            try {
                double vendidoReparto = ventasDao.obtenerTotalRepartoHoy();
                double sobranteMostrador = produccionTotal - vendidoReparto;
                if (sobranteMostrador < 0) {
                     tVenta.setForeground(Color.RED);
                     JOptionPane.showMessageDialog(parent, "❌ ERROR DE BALANCE:\nSe ha vendido más en reparto (" + vendidoReparto + ") de lo que se produjo (" + produccionTotal + ").");
                }
                tVenta.setText(String.format("%.2f", sobranteMostrador).replace(",", "."));
            } catch (Exception e) { JOptionPane.showMessageDialog(parent, "Error al calcular mostrador: " + e.getMessage()); }
        } else {
            tReparto.setEnabled(true); tReparto.setEditable(true);
            tVenta.setEnabled(true);   tVenta.setEditable(true);
            tMasa.setEnabled(true);   tMasa.setEditable(true);
        }
        
        // Forzamos el recalculo inmediato del dinero para que coincida con las tablas cargadas
        calcularTotalAPagar(tReparto, tVenta, tMasa, tProd, tGastos, cEntregar);
    }

    // 4. GUARDADO FINAL
    public void guardarCorte(JFrame parent, JComboBox<Object> boxRepartidor, JTextField tReparto, JTextField tVenta, 
                             JTextField tMasa, JTable tProd, JTable tGastos, JRadioButton sMasaNo, 
                             JCheckBox sPAdicionales, JCheckBox cGastos) { // 🚀 NUEVO PARÁMETRO: cGastos
        
        Object itemSeleccionado = boxRepartidor.getSelectedItem();
        int idEmpleado = 0;
        if (itemSeleccionado instanceof EmpleadoItem) {
            idEmpleado = ((EmpleadoItem) itemSeleccionado).getId();
        }
        
        if (idEmpleado == 0) {
            JOptionPane.showMessageDialog(parent, "⚠️ Por favor selecciona un Repartidor o Mostrador.");
            return;
        }

        double kReparto = 0, kVenta = 0, kMasa = 0;
        try {
            if (!tReparto.getText().isEmpty()) kReparto = Double.parseDouble(tReparto.getText());
            if (!tVenta.getText().isEmpty()) kVenta = Double.parseDouble(tVenta.getText());
            if (!tMasa.getText().isEmpty()) kMasa = Double.parseDouble(tMasa.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(parent, "❌ Verifica que los kilos sean números válidos.");
            return;
        }

        // ==========================================
        // 🛡️ LÓGICA DE ADVERTENCIAS INTELIGENTES
        // ==========================================
        DefaultTableModel modeloProductos = (DefaultTableModel) tProd.getModel();
        DefaultTableModel modeloGastos = (DefaultTableModel) tGastos.getModel();

        boolean ignoraProductos = !sPAdicionales.isSelected() && modeloProductos.getRowCount() > 0;
        boolean ignoraGastos = !cGastos.isSelected() && modeloGastos.getRowCount() > 0;

        String mensajeConfirmacion = "¿Estás seguro de guardar el corte para: " + itemSeleccionado.toString() + "?";

        if (ignoraProductos) {
            mensajeConfirmacion += "\n\n⚠️ AVISO: Hay productos en la tabla, pero la casilla 'Productos Adicionales' está desactivada. NO SE GUARDARÁN.";
        }
        if (ignoraGastos) {
            mensajeConfirmacion += "\n\n⚠️ AVISO: Hay gastos en la tabla, pero la casilla 'Gastos' está desactivada. NO SE GUARDARÁN.";
        }

        // Mostramos el cuadro de diálogo (usamos WARNING_MESSAGE si hay avisos, QUESTION_MESSAGE si todo está normal)
        int tipoMensaje = (ignoraProductos || ignoraGastos) ? JOptionPane.WARNING_MESSAGE : JOptionPane.QUESTION_MESSAGE;
        int confirm = JOptionPane.showConfirmDialog(parent, mensajeConfirmacion, "Confirmar Guardado", JOptionPane.YES_NO_OPTION, tipoMensaje);
        
        if (confirm != JOptionPane.YES_OPTION) return;

        // ==========================================
        // 🚀 FILTRO DE GUARDADO
        // Si el usuario aceptó ignorarlos, mandamos modelos vacíos al DAO para que no guarde basura
        // ==========================================
        DefaultTableModel prodParaGuardar = ignoraProductos ? new DefaultTableModel() : modeloProductos;
        DefaultTableModel gastosParaGuardar = ignoraGastos ? new DefaultTableModel() : modeloGastos;

        IVentasDAO dao = DAOFactory.getVentasDAO();
        boolean exito = dao.guardarCorteCompleto(idEmpleado, kReparto, kVenta, kMasa, prodParaGuardar, gastosParaGuardar);
        
        if (exito) {
            JOptionPane.showMessageDialog(parent, "✅ ¡Corte guardado exitosamente!");
            
            // Limpieza general
            boxRepartidor.setSelectedIndex(0);
            tReparto.setText("");
            tVenta.setText("");
            tMasa.setText("");
            modeloProductos.setRowCount(0);
            modeloGastos.setRowCount(0);
            sMasaNo.setSelected(true);
            
            // Forzamos el apagado de las casillas y sus eventos visuales
            if (sPAdicionales.isSelected()) {
                sPAdicionales.setSelected(false);
                sPAdicionales.getActionListeners()[0].actionPerformed(null);
            }
            if (cGastos.isSelected()) {
                cGastos.setSelected(false);
                cGastos.getActionListeners()[0].actionPerformed(null);
            }
            
        } else {
            JOptionPane.showMessageDialog(parent, "❌ Error al guardar en la base de datos.");
        }
    }
}