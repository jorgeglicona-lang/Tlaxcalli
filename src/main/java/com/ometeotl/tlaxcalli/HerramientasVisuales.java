package com.ometeotl.tlaxcalli;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class HerramientasVisuales {

    // 1. EL MOTOR DE ARRASTRE PARA LA BARRA SUPERIOR
    public static void configurarBarraArrastre(JFrame ventana, JPanel barraArriba) {
        
        MouseAdapter vigilanteArrastre = new MouseAdapter() {
            int xMouse, yMouse; // Variables atrapadas aquí, ya no ensucian su ventana

            @Override
            public void mousePressed(MouseEvent evt) {
                xMouse = evt.getX();
                yMouse = evt.getY();
            }

            @Override
            public void mouseDragged(MouseEvent evt) {
                int x = evt.getXOnScreen();
                int y = evt.getYOnScreen();
                ventana.setLocation(x - xMouse, y - yMouse);
            }
        };
        
        // Se le pegan los vigilantes a la barra
        barraArriba.addMouseListener(vigilanteArrastre);
        barraArriba.addMouseMotionListener(vigilanteArrastre); // Vital para detectar el movimiento
    }

    // 2. EL BOTÓN "X" INTELIGENTE
    public static void configurarBotonCerrar(JFrame ventana, JPanel jBext, JLabel ext, boolean esPrincipal) {
        jBext.setBackground(Color.WHITE);
        ext.setForeground(new Color(204, 204, 204));
        
        jBext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                jBext.setBackground(Color.RED);
                ext.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                jBext.setBackground(Color.WHITE);
                ext.setForeground(new Color(204, 204, 204));
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                if (esPrincipal) {
                    System.exit(0); // Si es el Menú Principal o el Login, apagamos todo.
                } else {
                    ventana.dispose(); // Si es una sub-ventana, solo cerramos esa ventana.
                }
            }
        });
    }
    //fabrica de ventanas
    public static void GenV(JFrame ven){
        ven.setLocationRelativeTo(null);
        ven.setVisible(true);
    }
    
    // 1. EL VIGILANTE DE TEXTO FALSO (PLACEHOLDER)
    public static void configurarPlaceholderTexto(JTextField campo, String textoFalso) {
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                // Cuando el cursor entra, si tiene el texto falso, lo borra
                if (campo.getText().equals(textoFalso)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent evt) {
                // Cuando el cursor sale, si lo dejaron vacío, regresa el texto falso
                if (campo.getText().isEmpty()) {
                    campo.setText(textoFalso);
                    campo.setForeground(Color.GRAY);
                }
            }
        });
    }

    // 2. EL VIGILANTE DE CONTRASEÑAS FALSAS
    public static void configurarPlaceholderClave(JPasswordField campoClave, String claveFalsa) {
        campoClave.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                String passActual = new String(campoClave.getPassword());
                if (passActual.equals(claveFalsa)) {
                    campoClave.setText("");
                    campoClave.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent evt) {
                String passActual = new String(campoClave.getPassword());
                if (passActual.isEmpty()) {
                    campoClave.setText(claveFalsa);
                    campoClave.setForeground(Color.GRAY);
                }
            }
        });
    }

    // 3. EL SALTO CON ENTER (Para brincar de un campo a otro)
    public static void configurarSaltoEnter(JTextField campoOrigen, JComponent campoDestino) {
        campoOrigen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    campoDestino.requestFocus();
                }
            }
        });
    }
    
    // MÉTODO NATIVO PARA AJUSTAR IMAGEN A JLABEL
    public static void pintarImagen(javax.swing.JLabel lbl, String ruta) {
        try {
            // 1. Cargamos el recurso usando explícitamente el nombre de la clase contenedora
            java.net.URL url = HerramientasVisuales.class.getResource(ruta);
            
            if (url != null) {
                javax.swing.ImageIcon imagen = new javax.swing.ImageIcon(url);
                
                // 2. Obtener dimensiones seguras
                int w = lbl.getWidth();
                int h = lbl.getHeight();
                if (w == 0 || h == 0) {
                    w = lbl.getPreferredSize().width;
                    h = lbl.getPreferredSize().height;
                }
                
                // 3. Escalar con alta calidad
                javax.swing.Icon icono = new javax.swing.ImageIcon(
                    imagen.getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH)
                );
                
                // 4. Asignar y refrescar
                lbl.setIcon(icono);
                lbl.repaint();
            } else {
                // Como no hay consola en el JAR de producción, un JOptionPane nos salvará de buscar a ciegas
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "⚠️ No se encontró el archivo de imagen en la ruta:\n" + ruta, 
                    "Error de Recursos", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + e.getMessage());
        }
    }
    
    public static void ocultarColumna(JTable tabla, int numCol){
        if (numCol >= 0 && numCol < tabla.getColumnCount()) {
            tabla.getColumnModel().getColumn(numCol).setMinWidth(0);
            tabla.getColumnModel().getColumn(numCol).setMaxWidth(0);
            tabla.getColumnModel().getColumn(numCol).setWidth(0);
            tabla.getColumnModel().getColumn(numCol).setPreferredWidth(0);
        }
    }
    
    // Este método centraliza la lógica de habilitación
    public static void vincularControl(JCheckBox checkbox, Object... componentes) {
        // Definimos qué hacer cuando el estado cambia
        ActionListener sync = e -> {
            boolean activo = checkbox.isSelected();
            for (Object c : componentes) {
                if (c instanceof JComponent comp) {
                    comp.setEnabled(activo);
                } else if (c instanceof DefaultTableModel modelo) {
                    // Opcional: si se desactiva, podríamos querer limpiar la tabla
                    if (!activo) modelo.setRowCount(0);
                }
            }
        };

        // Lo asignamos al checkbox
        checkbox.addActionListener(sync);

        // Lo ejecutamos una vez al inicio para asegurar que el estado inicial sea correcto
        sync.actionPerformed(null);
    }
}