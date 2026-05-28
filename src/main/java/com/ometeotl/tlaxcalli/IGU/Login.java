package com.ometeotl.tlaxcalli.IGU;

import com.ometeotl.tlaxcalli.HerramientasVisuales;
import com.ometeotl.tlaxcalli.LOGICA.C_Login;
import java.awt.Color;
import com.ometeotl.tlaxcalli.LOGICA.C_Inicio;

public class Login extends javax.swing.JFrame {

    private final C_Inicio ima = new C_Inicio();
    private final C_Login l = new C_Login();
    String valorPass;
    
    public Login() {
        initComponents();
        ima.pintarImagen(Logolb, "/imagen/logazul.png");
        ima.pintarImagen(Information, "/imagen/informacion.png");
        
        Information.setToolTipText("<html>Primer ingreso del sistema:"
                + "<br>Usuario: <b>Admin</b><br>Contraseña: <b>Admin123</b>"
                + "<br><br> Recuerde cambiar la contraseña</html>");
        
        exbg.setBackground(Color.white);
        jLabel9.setForeground(new Color(204,204,204));
        HerramientasVisuales.configurarBarraArrastre(this, jPanel4);
        HerramientasVisuales.configurarBotonCerrar(this, exbg, jLabel9, true);
        HerramientasVisuales.configurarPlaceholderTexto(usertxt, "Ingrese su usuario");
        HerramientasVisuales.configurarPlaceholderClave(passtst, "**********");
        HerramientasVisuales.configurarSaltoEnter(usertxt, passtst);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        usertxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        passtst = new javax.swing.JPasswordField();
        button = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        Logolb = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        exbg = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        Information = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/transparencia.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 260, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Ometeotl");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, -1, -1));

        jLabel4.setFont(new java.awt.Font("Roboto Black", 0, 30)); // NOI18N
        jLabel4.setText("Tlaxcalli");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 190, -1, -1));

        jLabel8.setForeground(new java.awt.Color(181, 181, 181));
        jLabel8.setText("Info - Iconos creados por Roundicons - Flaticon");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 380, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/loginclaro.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 260, 400));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel5.setText("Contraseña");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 240, -1, -1));

        usertxt.setFont(new java.awt.Font("Rockwell", 0, 11)); // NOI18N
        usertxt.setForeground(new java.awt.Color(153, 153, 153));
        usertxt.setText("Ingrese su usuario");
        usertxt.setBorder(null);
        jPanel1.add(usertxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 280, 30));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel6.setText("Usuario");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, -1, -1));

        passtst.setForeground(new java.awt.Color(153, 153, 153));
        passtst.setText("**********");
        passtst.setBorder(null);
        passtst.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pasststKeyPressed(evt);
            }
        });
        jPanel1.add(passtst, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, 280, 30));

        button.setBackground(new java.awt.Color(49, 191, 71));

        jLabel7.setFont(new java.awt.Font("Roboto Black", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(226, 251, 244));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("ENTRAR");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });

        javax.swing.GroupLayout buttonLayout = new javax.swing.GroupLayout(button);
        button.setLayout(buttonLayout);
        buttonLayout.setHorizontalGroup(
            buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        buttonLayout.setVerticalGroup(
            buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel1.add(button, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 330, 110, 50));

        Logolb.setBackground(new java.awt.Color(0, 102, 102));
        Logolb.setForeground(new java.awt.Color(0, 102, 102));
        jPanel1.add(Logolb, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 50, 40));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 280, 10));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, 280, 10));

        exbg.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("X");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout exbgLayout = new javax.swing.GroupLayout(exbg);
        exbg.setLayout(exbgLayout);
        exbgLayout.setHorizontalGroup(
            exbgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );
        exbgLayout.setVerticalGroup(
            exbgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel1.add(exbg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 40));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 810, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 40));
        jPanel1.add(Information, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 190, 20, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        button.setBackground(new Color(26,230,63));
        jLabel7.setBackground(Color.white);
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        button.setBackground(new java.awt.Color(49,191,71));
        valorPass = new String (passtst.getPassword());
        
        if(valorPass.equals("**********") || usertxt.getText().equals("Ingrese su usuario")) {
            return; // Cortamos la acción si los campos son los falsos
        }
            
        if (l.ValUsuario(usertxt.getText(), valorPass)) {
            this.dispose(); // Cierra el login y abre el sistema
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        button.setBackground(new Color(49,191,71));
        jLabel7.setBackground(new Color(226,251,244));
    }//GEN-LAST:event_jLabel7MouseExited

    private void pasststKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pasststKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            valorPass = new String (passtst.getPassword());
            // Validamos que no intenten entrar con el texto de relleno
            if(valorPass.equals("**********") || usertxt.getText().equals("Ingrese su usuario")) {
                return; // Cortamos la acción si los campos son los falsos
            }
            
            if (l.ValUsuario(usertxt.getText(), valorPass)) {
                this.dispose(); // Cierra el login y abre el sistema
            }
        }
    }//GEN-LAST:event_pasststKeyPressed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Information;
    private javax.swing.JLabel Logolb;
    private javax.swing.JPanel button;
    private javax.swing.JPanel exbg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPasswordField passtst;
    private javax.swing.JTextField usertxt;
    // End of variables declaration//GEN-END:variables
}
