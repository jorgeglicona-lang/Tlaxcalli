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
    public void procesarSeleccionRepartidor(JFrame parent, JComboBox<Object> boxRepartidor, JTextField tReparto, JTextField tVenta) {
        if (boxRepartidor.getSelectedItem() == null) return;
        String seleccionado = boxRepartidor.getSelectedItem().toString();
        
        tReparto.setText("");
        
        if(seleccionado.equalsIgnoreCase("Seleccione...")){
            tReparto.setEnabled(false);
            tVenta.setEnabled(false);
            return;
        }
        
        if (seleccionado.equalsIgnoreCase("Mostrador")) {
            IMolinoDAO molinoDao = DAOFactory.getMolinoDAO();
            IVentasDAO ventasDao = DAOFactory.getVentasDAO();
            
            double produccionTotal = molinoDao.obtenerTotalTortillaHoy();
            
            if (produccionTotal <= 0) {
                JOptionPane.showMessageDialog(parent, "⚠️ ACCIÓN BLOQUEADA:\nNo se puede calcular el Mostrador porque el registro de Molino está vacío.\nPor favor, registre primero la producción del día.");
                boxRepartidor.setSelectedIndex(0); 
                return; 
            }

            tReparto.setEditable(false);
            tReparto.setBackground(new Color(220, 220, 220));
            tVenta.setEditable(false);
            tVenta.setBackground(new Color(220, 220, 220)); 
            
            try {
                double vendidoReparto = ventasDao.obtenerTotalRepartoHoy();
                double sobranteMostrador = produccionTotal - vendidoReparto;
                
                if (sobranteMostrador < 0) {
                     tVenta.setForeground(Color.RED);
                     JOptionPane.showMessageDialog(parent, "❌ ERROR DE BALANCE:\nSe ha vendido más en reparto (" + vendidoReparto + ") de lo que se produjo (" + produccionTotal + ").");
                } else {
                     tVenta.setForeground(Color.BLACK);
                }
                // Usamos replace para evitar bugs con la coma de los decimales
                tVenta.setText(String.format("%.2f", sobranteMostrador).replace(",", "."));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Error al calcular mostrador: " + e.getMessage());
            }
        } else {
            tReparto.setEnabled(true);
            tVenta.setEnabled(true);
            tReparto.setEditable(true);
            tVenta.setEditable(true);
            tVenta.setText("");
            tVenta.setBackground(Color.WHITE);
            tVenta.setForeground(Color.BLACK);
        }
    }

    // 4. GUARDADO FINAL
    public void guardarCorte(JFrame parent, JComboBox<Object> boxRepartidor, JTextField tReparto, JTextField tVenta, 
                             JTextField tMasa, JTable tProd, JTable tGastos, JRadioButton sMasaNo, JCheckBox sPAdicionales) {
        
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

        int confirm = JOptionPane.showConfirmDialog(parent, "¿Estás seguro de guardar el corte para: " + itemSeleccionado.toString() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        DefaultTableModel modeloProductos = (DefaultTableModel) tProd.getModel();
        DefaultTableModel modeloGastos = (DefaultTableModel) tGastos.getModel();

        IVentasDAO dao = DAOFactory.getVentasDAO();
        boolean exito = dao.guardarCorteCompleto(idEmpleado, kReparto, kVenta, kMasa, modeloProductos, modeloGastos);
        
        if (exito) {
            JOptionPane.showMessageDialog(parent, "✅ ¡Corte guardado exitosamente!");
            // Limpieza
            boxRepartidor.setSelectedIndex(0);
            tReparto.setText("");
            tVenta.setText("");
            tMasa.setText("");
            modeloProductos.setRowCount(0);
            modeloGastos.setRowCount(0);
            sMasaNo.setSelected(true);
            sPAdicionales.setSelected(false);
            // Al desmarcar los adicionales, forzamos la actualización visual
            sPAdicionales.getActionListeners()[0].actionPerformed(null);
            
        } else {
            JOptionPane.showMessageDialog(parent, "❌ Error al guardar en la base de datos.");
        }
    }
}