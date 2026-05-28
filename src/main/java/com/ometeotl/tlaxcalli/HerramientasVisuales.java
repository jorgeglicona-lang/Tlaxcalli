package com.ometeotl.tlaxcalli;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
        
        jBext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                jBext.setBackground(Color.RED);
                ext.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
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
    public static void configurarPlaceholderTexto(javax.swing.JTextField campo, String textoFalso) {
        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                // Cuando el cursor entra, si tiene el texto falso, lo borra
                if (campo.getText().equals(textoFalso)) {
                    campo.setText("");
                    campo.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                // Cuando el cursor sale, si lo dejaron vacío, regresa el texto falso
                if (campo.getText().isEmpty()) {
                    campo.setText(textoFalso);
                    campo.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    }

    // 2. EL VIGILANTE DE CONTRASEÑAS FALSAS
    public static void configurarPlaceholderClave(javax.swing.JPasswordField campoClave, String claveFalsa) {
        campoClave.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                String passActual = new String(campoClave.getPassword());
                if (passActual.equals(claveFalsa)) {
                    campoClave.setText("");
                    campoClave.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                String passActual = new String(campoClave.getPassword());
                if (passActual.isEmpty()) {
                    campoClave.setText(claveFalsa);
                    campoClave.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    }

    // 3. EL SALTO CON ENTER (Para brincar de un campo a otro)
    public static void configurarSaltoEnter(javax.swing.JTextField campoOrigen, javax.swing.JComponent campoDestino) {
        campoOrigen.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    campoDestino.requestFocus();
                }
            }
        });
    }
}