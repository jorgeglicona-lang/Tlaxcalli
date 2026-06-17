
package com.ometeotl.tlaxcalli.IGU;

import com.ometeotl.tlaxcalli.HerramientasVisuales;
import static com.ometeotl.tlaxcalli.HerramientasVisuales.ocultarColumna;
import com.ometeotl.tlaxcalli.LOGICA.C_Inicio;
import com.ometeotl.tlaxcalli.LOGICA.C_Sesion_login;
import com.ometeotl.tlaxcalli.Tlaxcalli;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.I_InicioDAO;
import java.awt.Color;

public class Inicio extends javax.swing.JFrame {

    private final Tlaxcalli nom = new Tlaxcalli();
    private final C_Inicio controlador = new C_Inicio();
    
    public Inicio() {
        initComponents();
        
        HerramientasVisuales.pintarImagen(Logolb, "/imagen/transparencia.png");
        nom.inicializarNombreNegocio(this, negocio);
        this.nomU.setText("¡Bienvenido "+C_Sesion_login.nombre+"!");
        
        // 1. Le pedimos a la fábrica el "enchufe" de la sala
        I_InicioDAO inicioDAO = DAOFactory.getInicioDAO();
    
        // 2. Usamos el enchufe para pedir las tablas y las metemos a los JTable visuales
        tablaProductos.setModel(inicioDAO.obtenerProductosTabla());
        tablaCatGastos.setModel(inicioDAO.obtenerCatGastosTabla());
        
        // Ocultamos "Es Comodín" en la tabla de Productos (Es la Columna Índice 3)
        ocultarColumna(tablaProductos,0);
        ocultarColumna(tablaProductos,3);
        
        // Ocultamos "Requiere Descripción" en la tabla de Gastos (Es la Columna Índice 2)
        ocultarColumna(tablaCatGastos,0);
        ocultarColumna(tablaCatGastos,2);
        
        HerramientasVisuales.configurarBarraArrastre(this, jPanel3);
        HerramientasVisuales.configurarBotonCerrar(this, jBext, ext, true);
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
        jPBoton4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jBext = new javax.swing.JPanel();
        ext = new javax.swing.JLabel();
        nomU = new javax.swing.JLabel();
        negocio = new javax.swing.JLabel();
        panelPestanias = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaCatGastos = new javax.swing.JTable();
        b_agregar = new javax.swing.JButton();
        b_modificar = new javax.swing.JButton();
        b_eliminar = new javax.swing.JButton();

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

        jPBoton4.setBackground(new java.awt.Color(240, 178, 70));
        jPBoton4.setPreferredSize(new java.awt.Dimension(250, 70));

        jLabel1.setBackground(new java.awt.Color(40, 195, 20));
        jLabel1.setFont(new java.awt.Font("Roboto Bk", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Gastos Generales");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPBoton4Layout = new javax.swing.GroupLayout(jPBoton4);
        jPBoton4.setLayout(jPBoton4Layout);
        jPBoton4Layout.setHorizontalGroup(
            jPBoton4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPBoton4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPBoton4Layout.setVerticalGroup(
            jPBoton4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
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
            .addComponent(jPBoton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPBoton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(158, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jBext.setBackground(new java.awt.Color(255, 255, 255));

        ext.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        ext.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ext.setText("X");

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

        nomU.setText("Nombre");
        jPanel1.add(nomU, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, -1, -1));

        negocio.setText("negocio");
        jPanel1.add(negocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 140, -1, -1));

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaProductos);

        panelPestanias.addTab("Catalogo Productos", jScrollPane1);

        tablaCatGastos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tablaCatGastos);

        panelPestanias.addTab("Catalogo Gastos", jScrollPane2);

        jPanel1.add(panelPestanias, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 240, 580, 280));

        b_agregar.setText("Agregar");
        b_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_agregarActionPerformed(evt);
            }
        });
        jPanel1.add(b_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 200, 80, 30));

        b_modificar.setText("Modificar");
        b_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_modificarActionPerformed(evt);
            }
        });
        jPanel1.add(b_modificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 200, -1, 30));

        b_eliminar.setText("Eliminar");
        b_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_eliminarActionPerformed(evt);
            }
        });
        jPanel1.add(b_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 200, -1, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        NR n=new NR();
        HerramientasVisuales.GenV(n);
        Molino mol=new Molino();
        HerramientasVisuales.GenV(mol);
    }//GEN-LAST:event_b_registrosMouseClicked

    private void b_empleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_empleadosMouseClicked
        Emplados reg=new Emplados();
        HerramientasVisuales.GenV(reg);
    }//GEN-LAST:event_b_empleadosMouseClicked

    private void b_reportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_reportesMouseClicked
        Reportes reg=new Reportes();
        HerramientasVisuales.GenV(reg);
    }//GEN-LAST:event_b_reportesMouseClicked

    private void b_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_agregarActionPerformed
        controlador.agregarInteligente(this, panelPestanias, tablaProductos, tablaCatGastos);
        ocultarColumna(tablaProductos,0);
        ocultarColumna(tablaProductos,3);
        ocultarColumna(tablaCatGastos,0);
        ocultarColumna(tablaCatGastos,2);
    }//GEN-LAST:event_b_agregarActionPerformed

    private void b_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_eliminarActionPerformed
        controlador.eliminarInteligente(this, panelPestanias, tablaProductos, tablaCatGastos);
        ocultarColumna(tablaProductos,0);
        ocultarColumna(tablaProductos,3);
        ocultarColumna(tablaCatGastos,0);
        ocultarColumna(tablaCatGastos,2);
    }//GEN-LAST:event_b_eliminarActionPerformed

    private void b_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_modificarActionPerformed
        controlador.modificarInteligente(this, panelPestanias, tablaProductos, tablaCatGastos);
        ocultarColumna(tablaProductos,0);
        ocultarColumna(tablaProductos,3);
        ocultarColumna(tablaCatGastos,0);
        ocultarColumna(tablaCatGastos,2);
    }//GEN-LAST:event_b_modificarActionPerformed

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        jPBoton4.setBackground(new Color(35,140,35));
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        jPBoton4.setBackground(new Color(240,178,70));
    }//GEN-LAST:event_jLabel1MouseExited

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        Gastos_Generales GasG=new Gastos_Generales();
        HerramientasVisuales.GenV(GasG);
    }//GEN-LAST:event_jLabel1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Logolb;
    private javax.swing.JLabel Name;
    private javax.swing.JButton b_agregar;
    private javax.swing.JButton b_eliminar;
    private javax.swing.JLabel b_empleados;
    private javax.swing.JButton b_modificar;
    private javax.swing.JLabel b_registros;
    private javax.swing.JLabel b_reportes;
    private javax.swing.JLabel ext;
    private javax.swing.JPanel jBext;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPBoton1;
    private javax.swing.JPanel jPBoton2;
    private javax.swing.JPanel jPBoton3;
    private javax.swing.JPanel jPBoton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel negocio;
    private javax.swing.JLabel nomU;
    private javax.swing.JTabbedPane panelPestanias;
    private javax.swing.JTable tablaCatGastos;
    private javax.swing.JTable tablaProductos;
    // End of variables declaration//GEN-END:variables
}
