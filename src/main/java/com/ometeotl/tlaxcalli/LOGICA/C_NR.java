package com.ometeotl.tlaxcalli.LOGICA;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.*;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Servicios.CorteDiaService; // <-- Importamos el nuevo servicio
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import static java.awt.Color.BLACK;
import java.util.List;
import static com.ometeotl.tlaxcalli.HerramientasVisuales.LimpiarCampos;
import static com.ometeotl.tlaxcalli.HerramientasVisuales.ActivarCampos;
import static java.awt.Color.RED;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class C_NR {
    
    private double precioReparto = 0.0;
    private double precioMostrador = 0.0;
    private double precioMasa = 0.0;
    private final IVentasDAO dao = DAOFactory.getVentasDAO();
    
    // <-- Instanciamos el servicio orquestador
    private final CorteDiaService corteService = new CorteDiaService(); 
    
    private Color DatoEx;
    
    public void cargarPreciosBase() {
        precioReparto = dao.obtenerPrecioProducto(1);
        precioMostrador = dao.obtenerPrecioProducto(2);
        precioMasa = dao.obtenerPrecioProducto(3);
        
        // Respaldo de seguridad por si la base de datos falla al leer
        if (precioReparto == 0) precioReparto = 19.0;
        if (precioMostrador == 0) precioMostrador = 22.0;
        if (precioMasa == 0) precioMasa = 20.0;
    }
    
    // 1. LLENADO DE COMBOBOX
    public void inicializarCombos(JComboBox<Object> boxRepartidor, JComboBox<Object> cbProductos,
            JComboBox<Object> cbGastos) {
        
        // --- Empleados ---
        // Asumiendo que su EmpleadosDAO original o SQLiteDAO ya está en la fábrica
        IEmpleadosDAO empDao = DAOFactory.getEmpleadosDAO();
        
        // ¡REPARADO! Ahora llamamos al método exclusivo de vendedores
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

    // 2. CALCULAR EN TIEMPO AL INSTANTE
    public void calcularTotalAPagar(JTextField tReparto, JTextField tVenta, JTextField tMasa,
            JTable tProd, JTable tGastos, JLabel cEntregar) {
        double totalIngresos = 0.0;
        double totalGastos = 0.0;
        
        try {
            if (!tReparto.getText().trim().isEmpty()) totalIngresos += Double.parseDouble(tReparto.getText()) * precioReparto;
            if (!tVenta.getText().trim().isEmpty()) totalIngresos += Double.parseDouble(tVenta.getText()) * precioMostrador;
            if (!tMasa.getText().trim().isEmpty()) totalIngresos += Double.parseDouble(tMasa.getText()) * precioMasa;
            
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
        cEntregar.setForeground(totalFinal < 0 ? RED : BLACK);
    }

    // 3. LÓGICA DE REPARTIDOR Y MOSTRADOR
    public void procesarSeleccionRepartidor(JFrame parent, JComboBox<Object> boxRepartidor, JTextField tReparto,
                                            JTextField tVenta, JTextField tMasa, JRadioButton sMasaS,
                                            JRadioButton sMasaN, JCheckBox Pro, JCheckBox Gas, JTable tProd,
                                            JTable tGastos, JLabel cEntregar, JComboBox cb_gas, JComboBox cb_pro) {

        Object item = boxRepartidor.getSelectedItem();
        DefaultTableModel modeloProd = (DefaultTableModel) tProd.getModel();
        DefaultTableModel modeloGastos = (DefaultTableModel) tGastos.getModel();

        if (item == null) return;
        
        String seleccionado = item.toString();
        
        if (seleccionado.equalsIgnoreCase("Seleccione...")) {
            LimpiarCampos(tReparto,tVenta,tProd,tGastos,Pro,Gas);
            sMasaS.setEnabled(false);
            sMasaN.setSelected(true);
            calcularTotalAPagar(tReparto, tVenta, tMasa, tProd, tGastos, cEntregar);
            return;
        }
        
        if (!(item instanceof EmpleadoItem emp)) {
            showMessageDialog(parent, "⚠️ Por favor selecciona un Repartidor o Mostrador válido.");
            return;
        }
        
        int idEmpleado = emp.getId();
        double[] corteExistente = dao.obtenerCorteEmpleadoHoy(idEmpleado);
        
        if(idEmpleado>0){
            ActivarCampos(Pro,Gas,sMasaS);
            Pro.setSelected(false);
            Gas.setSelected(false);
            sMasaN.setSelected(true);
        }
        
        if (corteExistente != null) {
            // ¡SÍ YA EXISTE REGISTRO! Cargamos textos principales
            DatoEx = new Color(190, 250, 180);
            tReparto.setBackground(DatoEx);
            tVenta.setBackground(DatoEx);
            
            // 1. REGLA DE REPARTO/VENTA: Solo el Repartidor puede editar. El Mostrador se queda bloqueado.
            if (!seleccionado.equalsIgnoreCase("Mostrador")) {
                ActivarCampos(tVenta,tReparto);
            } else {
                LimpiarCampos(tVenta,tReparto);
            }
            
            // 2. REGLA DE LA MASA: Leemos el texto de la caja (no el objeto). 
            // Si tiene más de 0 kilos, la habilitamos.
            double kiloM=corteExistente[2];
            if (kiloM>0) {
                ActivarCampos(tMasa,sMasaS);
                tMasa.setBackground(DatoEx);
            } else {
                LimpiarCampos(tMasa);
            }
            tReparto.setText(String.format("%.2f", corteExistente[0]).replace(",", "."));
            tVenta.setText(String.format("%.2f", corteExistente[1]).replace(",", "."));
            tMasa.setText(String.format("%.2f", corteExistente[2]).replace(",", "."));
            
            //️ Llenamos las tablas visuales de productos adicionales y gastos
            List<Object[]> productosHoy = dao.obtenerProductosCorteHoy(idEmpleado);
            List<Object[]> gastosHoy = dao.obtenerGastosCorteHoy(idEmpleado);
            
            for (Object[] fila : productosHoy) {
                modeloProd.addRow(fila);
            }
            
            for (Object[] fila : gastosHoy) {
                modeloGastos.addRow(fila);
            }
            
            if(modeloGastos.getRowCount()>0) Gas.setSelected(true);
            if(modeloProd.getRowCount()>0) Pro.setSelected(true);
            
            calcularTotalAPagar(tReparto, tVenta, tMasa, tProd, tGastos, cEntregar);
            
            showMessageDialog(parent, "ℹ️ REGISTRO LOCALIZADO:\nEl empleado " + seleccionado
                            + " ya cuenta con un corte hoy.\nSe han restaurado todos sus datos, productos "
                            + "y gastos en las tablas.","Atencion", INFORMATION_MESSAGE);
            return;
        }
        
        LimpiarCampos(tReparto,tVenta,tMasa,tProd,tGastos);
        Pro.setSelected(false);
        Gas.setSelected(false);
        sMasaN.setSelected(true);
        
        // ESCENARIO B: NO HAY REGISTRO PREVIO (Su lógica normal)
        if (seleccionado.equalsIgnoreCase("Mostrador")) {
            IMolinoDAO molinoDao = DAOFactory.getMolinoDAO();
            double produccionTotal = molinoDao.obtenerTotalTortillaHoy();
            
            if (produccionTotal <= 0) {
                JOptionPane.showMessageDialog(parent, "⚠️ ACCIÓN BLOQUEADA:\nNo se puede calcular el Mostrador "
                        + "porque el registro de Molino está vacío.\nPor favor, registre primero la producción "
                        + "del día.");
                boxRepartidor.setSelectedIndex(0); 
                return;
            }
            
            DatoEx = new Color(190, 250, 180);
            tReparto.setBackground(DatoEx);
            tVenta.setBackground(DatoEx);
            
            try {
                
                double vendidoReparto = dao.obtenerTotalRepartoHoy();
                
                // 2. Traemos la masa cruda vendida por los repartidores
                double masaReparto = dao.obtenerTotalMasaHoy();
                
                // 3. Convertimos esa masa cruda a su equivalente de tortilla perdida (Factor 16/18)
                double tortillaPerdidaPorMasa = masaReparto * (16.0 / 18.0);
                
                // 4. LA NUEVA FÓRMULA MAESTRA DE BALANCE
                double sobranteMostrador = produccionTotal - vendidoReparto - tortillaPerdidaPorMasa;
                
                if (sobranteMostrador < 0) {
                    tVenta.setForeground(RED);
                    showMessageDialog(parent, "❌ ERROR DE BALANCE:\nSe ha vendido más en reparto y masa (" 
                             + (vendidoReparto + tortillaPerdidaPorMasa) + " kg equiv.) de lo que se produjo (" + produccionTotal + " kg).");
                    boxRepartidor.setSelectedIndex(0);
                    return;
                }
                
                tReparto.setText("0.0");
                tVenta.setText(String.format("%.2f", sobranteMostrador).replace(",", "."));
            } catch (Exception e){
                showMessageDialog(parent, "Error al calcular mostrador: " + e.getMessage());
            }
            
        } else {
            ActivarCampos(tReparto,tVenta);
        }
        calcularTotalAPagar(tReparto, tVenta, tMasa, tProd, tGastos, cEntregar);
    }

    // 4. GUARDADO FINAL
    public void guardarCorte(JFrame parent, JComboBox<Object> boxRepartidor, JTextField tReparto, JTextField tVenta,
                             JTextField tMasa, JTable tProd, JTable tGastos, JRadioButton sMasa,
                             JCheckBox sPAdicionales, JCheckBox cGastos) {
        
        Object itemSeleccionado = boxRepartidor.getSelectedItem();
        
        if (itemSeleccionado == null) {
            showMessageDialog(parent, "⚠️ No hay ninguna selección válida en el mostrador.");
            return;
        }

        int idEmpleado = 0;
        if (itemSeleccionado instanceof EmpleadoItem emp) {
            idEmpleado = emp.getId();
        }
        
        if (idEmpleado == 0) {
            showMessageDialog(parent, "⚠️ Por favor selecciona un Repartidor o Mostrador válido.");
            return;
        }

        double kReparto = 0, kVenta = 0, kMasa = 0;
        
        try {
            if (!tReparto.getText().trim().isEmpty()) kReparto = Double.parseDouble(tReparto.getText());
            if (!tVenta.getText().trim().isEmpty()) kVenta = Double.parseDouble(tVenta.getText());
            if (!tMasa.getText().trim().isEmpty()) kMasa = Double.parseDouble(tMasa.getText());
        } catch (NumberFormatException e) {
            showMessageDialog(parent, "❌ Verifica que los kilos sean números válidos.");
            return;
        }

        DefaultTableModel modeloProductos = (DefaultTableModel) tProd.getModel();
        DefaultTableModel modeloGastos = (DefaultTableModel) tGastos.getModel();

        boolean ignoraProductos = !sPAdicionales.isSelected() && modeloProductos.getRowCount() > 0;
        boolean ignoraGastos = !cGastos.isSelected() && modeloGastos.getRowCount() > 0;

        String mensajeConfirmacion = "¿Estás seguro de guardar el corte para: " + itemSeleccionado.toString() + "?";

        if (ignoraProductos) {
            mensajeConfirmacion += "\n\n⚠️ AVISO: Hay productos en la tabla, pero la casilla 'Productos Adicionales'"
                    + "está desactivada. NO SE GUARDARÁN.";
        }
        if (ignoraGastos) {
            mensajeConfirmacion += "\n\n⚠️ AVISO: Hay gastos en la tabla, pero la casilla 'Gastos' está desactivada."
                    + "NO SE GUARDARÁN.";
        }

        // Mostramos el cuadro de diálogo (usamos WARNING_MESSAGE si hay avisos, QUESTION_MESSAGE si todo está normal)
        int tipoMensaje = (ignoraProductos || ignoraGastos) ? WARNING_MESSAGE : QUESTION_MESSAGE;
        int confirm = showConfirmDialog(parent, mensajeConfirmacion, "Confirmar Guardado", YES_NO_OPTION,
                tipoMensaje);
        
        if (confirm != YES_OPTION) return;

        DefaultTableModel prodParaGuardar = ignoraProductos ? new DefaultTableModel() : modeloProductos;
        DefaultTableModel gastosParaGuardar = ignoraGastos ? new DefaultTableModel() : modeloGastos;

        // <-- CAMBIO APLICADO AQUÍ: Llamamos al servicio orquestador en lugar del DAO directo
        boolean exito = corteService.procesarVentaYCascada(idEmpleado, kReparto, kVenta, kMasa, prodParaGuardar, gastosParaGuardar);
        
        if (!exito) {
            showMessageDialog(parent, "❌ Error al guardar en la base de datos.");
            return;   
        }
        
        showMessageDialog(parent, "✅ ¡Corte guardado exitosamente!");
        // Limpieza general
        sMasa.setSelected(true);
        LimpiarCampos(tReparto,tVenta,tMasa,boxRepartidor);
        boxRepartidor.setEnabled(true);

        if (sPAdicionales.isSelected()) {
            sPAdicionales.setSelected(false);
            sPAdicionales.getActionListeners()[0].actionPerformed(null);
        }
        
        if (cGastos.isSelected()) {
            cGastos.setSelected(false);
            cGastos.getActionListeners()[0].actionPerformed(null);
        }
    }
}