
package com.mycompany.tlaxcalli.Igu;

import com.mycompany.tlaxcalli.Persistencia.Cconection;
import java.awt.Color;

public class Inicio extends javax.swing.JFrame {

    Cconection call=new Cconection();
    int xMause, yMause;
    public Inicio() {
        initComponents();
       pintarImagen(Logolb, "/imagen/transparencia.png");
        call.establecerConexion();
        
    }
    
    public void settext(String nombre){
        this.nombre.setText(nombre);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Logolb = new javax.swing.JLabel();
        Name = new javax.swing.JLabel();
        jPBoton1 = new javax.swing.JPanel();
        b_registros = new javax.swing.JLabel();
        jPBoton2 = new javax.swing.JPanel();
        b_reportes = new javax.swing.JLabel();
        jPBoton3 = new javax.swing.JPanel();
        b_empleados = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jBext = new javax.swing.JPanel();
        ext = new javax.swing.JLabel();
        nombre = new javax.swing.JLabel();
        negocio = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(240, 178, 70));
        jPanel2.setPreferredSize(new java.awt.Dimension(250, 550));

        Name.setFont(new java.awt.Font("Roboto Black", 0, 26)); // NOI18N
        Name.setForeground(new java.awt.Color(255, 255, 255));
        Name.setText("Tlaxcalli");

        jPBoton1.setBackground(new java.awt.Color(240, 178, 70));
        jPBoton1.setPreferredSize(new java.awt.Dimension(250, 70));

        b_registros.setBackground(new java.awt.Color(40, 195, 20));
        b_registros.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        b_registros.setForeground(new java.awt.Color(255, 255, 255));
        b_registros.setText("Nuevo Registro");
        b_registros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b_registrosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b_registrosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b_registrosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPBoton1Layout = new javax.swing.GroupLayout(jPBoton1);
        jPBoton1.setLayout(jPBoton1Layout);
        jPBoton1Layout.setHorizontalGroup(
            jPBoton1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPBoton1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(b_registros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPBoton1Layout.setVerticalGroup(
            jPBoton1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(b_registros, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        jPBoton2.setBackground(new java.awt.Color(240, 178, 70));
        jPBoton2.setPreferredSize(new java.awt.Dimension(250, 70));

        b_reportes.setBackground(new java.awt.Color(40, 195, 20));
        b_reportes.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        b_reportes.setForeground(new java.awt.Color(255, 255, 255));
        b_reportes.setText("Reportes");
        b_reportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b_reportesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b_reportesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b_reportesMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPBoton2Layout = new javax.swing.GroupLayout(jPBoton2);
        jPBoton2.setLayout(jPBoton2Layout);
        jPBoton2Layout.setHorizontalGroup(
            jPBoton2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPBoton2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(b_reportes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPBoton2Layout.setVerticalGroup(
            jPBoton2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(b_reportes, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        jPBoton3.setBackground(new java.awt.Color(240, 178, 70));
        jPBoton3.setPreferredSize(new java.awt.Dimension(250, 70));

        b_empleados.setBackground(new java.awt.Color(40, 195, 20));
        b_empleados.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        b_empleados.setForeground(new java.awt.Color(255, 255, 255));
        b_empleados.setText("Empleados");
        b_empleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b_empleadosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b_empleadosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b_empleadosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPBoton3Layout = new javax.swing.GroupLayout(jPBoton3);
        jPBoton3.setLayout(jPBoton3Layout);
        jPBoton3Layout.setHorizontalGroup(
            jPBoton3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPBoton3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(b_empleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPBoton3Layout.setVerticalGroup(
            jPBoton3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(b_empleados, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(Logolb, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
            .addComponent(jPBoton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPBoton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPBoton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(Logolb, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPBoton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPBoton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPBoton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(234, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel3MouseDragged(evt);
            }
        });
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel3MousePressed(evt);
            }
        });

        jBext.setBackground(new java.awt.Color(255, 255, 255));

        ext.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        ext.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ext.setText("X");
        ext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                extMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                extMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                extMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jBextLayout = new javax.swing.GroupLayout(jBext);
        jBext.setLayout(jBextLayout);
        jBextLayout.setHorizontalGroup(
            jBextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ext, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );
        jBextLayout.setVerticalGroup(
            jBextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBextLayout.createSequentialGroup()
                .addComponent(ext, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 854, Short.MAX_VALUE)
                .addComponent(jBext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jBext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 40));

        nombre.setText("Nombre");
        jPanel1.add(nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 170, -1, -1));

        negocio.setText("negocio");
        jPanel1.add(negocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 240, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void extMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_extMouseClicked
        System.exit(0);
    }//GEN-LAST:event_extMouseClicked

    private void extMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_extMouseEntered
        jBext.setBackground(Color.red);
        ext.setForeground(Color.white);
    }//GEN-LAST:event_extMouseEntered

    private void extMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_extMouseExited
        jBext.setBackground(Color.white);
        ext.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_extMouseExited

    private void b_registrosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_registrosMouseEntered
        jPBoton1.setBackground(new Color(35,140,35));
    }//GEN-LAST:event_b_registrosMouseEntered

    private void b_registrosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_registrosMouseExited
        jPBoton1.setBackground(new Color(240,178,70));
    }//GEN-LAST:event_b_registrosMouseExited

    private void b_reportesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_reportesMouseEntered
        jPBoton2.setBackground(new Color(35,140,35));
    }//GEN-LAST:event_b_reportesMouseEntered

    private void b_reportesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_reportesMouseExited
        jPBoton2.setBackground(new Color(240,178,70));
    }//GEN-LAST:event_b_reportesMouseExited

    private void b_empleadosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_empleadosMouseEntered
        jPBoton3.setBackground(new Color(35,140,35));
    }//GEN-LAST:event_b_empleadosMouseEntered

    private void b_empleadosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_empleadosMouseExited
        jPBoton3.setBackground(new Color(240,178,70));
    }//GEN-LAST:event_b_empleadosMouseExited

    private void b_registrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_registrosMouseClicked
        NR nuevo=new NR();
        nuevo.setVisible(true);
        nuevo.setLocationRelativeTo(null);
        
        Molino mol=new Molino();
        mol.setVisible(true);
        mol.setLocationRelativeTo(null);
    }//GEN-LAST:event_b_registrosMouseClicked

    private void b_empleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_empleadosMouseClicked
        Emplados reg=new Emplados();
        reg.setVisible(true);
        reg.setLocationRelativeTo(null);
    }//GEN-LAST:event_b_empleadosMouseClicked

    private void jPanel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MousePressed
        xMause = evt.getX();
        yMause = evt.getY();
    }//GEN-LAST:event_jPanel3MousePressed

    private void jPanel3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        
        this.setLocation(x-xMause, y-yMause);
    }//GEN-LAST:event_jPanel3MouseDragged

    private void b_reportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_reportesMouseClicked
        Reportes reg=new Reportes();
        reg.setVisible(true);
        reg.setLocationRelativeTo(null);
    }//GEN-LAST:event_b_reportesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Logolb;
    private javax.swing.JLabel Name;
    private javax.swing.JLabel b_empleados;
    private javax.swing.JLabel b_registros;
    private javax.swing.JLabel b_reportes;
    private javax.swing.JLabel ext;
    private javax.swing.JPanel jBext;
    private javax.swing.JPanel jPBoton1;
    private javax.swing.JPanel jPBoton2;
    private javax.swing.JPanel jPBoton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel negocio;
    private javax.swing.JLabel nombre;
    // End of variables declaration//GEN-END:variables
    
    // MÉTODO NATIVO PARA AJUSTAR IMAGEN A JLABEL
    private void pintarImagen(javax.swing.JLabel lbl, String ruta) {
        try {
            // 1. Cargar la imagen desde los recursos del proyecto (funciona dentro del JAR)
            java.net.URL url = getClass().getResource(ruta);
            
            if (url != null) {
                javax.swing.ImageIcon imagen = new javax.swing.ImageIcon(url);
                
                // 2. Obtener dimensiones. Si el layout aún no carga, usamos el tamaño preferido
                int w = lbl.getWidth();
                int h = lbl.getHeight();
                if (w == 0 || h == 0) {
                    w = lbl.getPreferredSize().width;
                    h = lbl.getPreferredSize().height;
                }
                
                // 3. Escalar la imagen (SCALE_SMOOTH da mejor calidad que la librería externa)
                javax.swing.Icon icono = new javax.swing.ImageIcon(
                    imagen.getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH)
                );
                
                // 4. Asignar al label
                lbl.setIcon(icono);
                lbl.repaint();
            } else {
                System.err.println("No se encontró la imagen en: " + ruta);
            }
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + e.getMessage());
        }
    }

}
