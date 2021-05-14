/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import controllers.controllerConsolidacion;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import panels.Origin;
import utils.PaintRowTable;
import utils.LoaderInstancias;
import configs.config;

/**
 *
 * @author Jose Alejandro Lopez
 */
public class index extends javax.swing.JFrame {

    private DefaultTableModel modeloTable = new DefaultTableModel();
    private String rowExample[] = {"","","","","", "", "", ""};
    private controllerConsolidacion controller = null;
    private index objIdex = null;
    private SimpleDateFormat Formato;
    private String fechaInicio = "", fechaFinal = "";
    private String fechaIni[] = null, fechaFin[] = null;
    private LoaderInstancias loaderInstancias = null;
    private Calendar calendar = null;
    private Origin origin = null;
    private config conf = null;
    
    public index() {
        try {
            this.Formato = new SimpleDateFormat("dd-MM-yyyy");
            this.loaderInstancias = new LoaderInstancias();
            
            initComponents();
            setTable();
            setButtons();
            
            
            this.conf = new config(loaderInstancias);
            origin = new Origin(dialogOrigin, loaderInstancias, conf);
            origin.setBackground(Color.WHITE);
            this.objIdex = this;
        
            tableConsolidaciones.setDefaultRenderer(Object.class, new PaintRowTable());
            tableConsolidaciones.setBackground(Color.WHITE);
            loaderInstancias.setTableConsolidaciones(tableConsolidaciones);
            loaderInstancias.setVentana(this);
            loaderInstancias.setLabelHost(textHost);
            loaderInstancias.setLabelDatabase(textDatabase);
            controller = new controllerConsolidacion(loaderInstancias, conf);
            calendar = new GregorianCalendar();
            dateFinal.setCalendar(calendar);
            dateInicio.setCalendar(calendar);
            
            footer.setBackground(new Color(60,63,65));
            footer.setForeground(Color.WHITE);
            
            this.setTitle("Consolidaciones SPA");
            this.setLocationRelativeTo(null);
            tableConsolidaciones.requestFocus();
            this.setIconImage(new ImageIcon(getClass().getResource("/contents/r.png")).getImage());
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono" + e);
            JOptionPane.showMessageDialog(this, "Sucedio algun error: " + e, "Error en la ejecucion", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogOrigin = new javax.swing.JDialog();
        btnSearch = new javax.swing.JButton();
        dateInicio = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dateFinal = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableConsolidaciones = new javax.swing.JTable();
        footer = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        textHost = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textDatabase = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        javax.swing.GroupLayout dialogOriginLayout = new javax.swing.GroupLayout(dialogOrigin.getContentPane());
        dialogOrigin.getContentPane().setLayout(dialogOriginLayout);
        dialogOriginLayout.setHorizontalGroup(
            dialogOriginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        dialogOriginLayout.setVerticalGroup(
            dialogOriginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        btnSearch.setText("Buscar");

        dateInicio.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Fecha inicio:");

        jLabel2.setText("Fecha Fin:");

        dateFinal.setBackground(new java.awt.Color(255, 255, 255));

        tableConsolidaciones.setBackground(new java.awt.Color(255, 255, 255));
        tableConsolidaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableConsolidaciones.setGridColor(new java.awt.Color(153, 153, 153));
        jScrollPane1.setViewportView(tableConsolidaciones);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Host:");

        textHost.setForeground(new java.awt.Color(255, 255, 255));
        textHost.setText("localhost");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Database:");

        textDatabase.setForeground(new java.awt.Color(255, 255, 255));
        textDatabase.setText("SUPER1");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Copyright © 2021 Super Promociones de Acayucan");

        javax.swing.GroupLayout footerLayout = new javax.swing.GroupLayout(footer);
        footer.setLayout(footerLayout);
        footerLayout.setHorizontalGroup(
            footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textHost, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textDatabase, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 400, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap())
        );
        footerLayout.setVerticalGroup(
            footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, footerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(textHost)
                    .addComponent(jLabel4)
                    .addComponent(textDatabase)
                    .addComponent(jLabel5))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1009, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(footer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSearch)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateInicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateFinal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(footer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void setTable() {
        modeloTable.addColumn("Fecha");
        modeloTable.addColumn("Hora");
        modeloTable.addColumn("Documento");
        modeloTable.addColumn("Entrada");
        modeloTable.addColumn("Referencia");
        modeloTable.addColumn("Almacen Destino");
        modeloTable.addColumn("Observaciones");
        modeloTable.addColumn("Estatus");
        
        for (int i = 0; i < 20; i++) { modeloTable.addRow(rowExample); }
        
        tableConsolidaciones.setModel(modeloTable);
        
        tableConsolidaciones.getColumnModel().getColumn(0).setPreferredWidth(40);
        tableConsolidaciones.getColumnModel().getColumn(1).setPreferredWidth(40);
        tableConsolidaciones.getColumnModel().getColumn(2).setPreferredWidth(40);
        tableConsolidaciones.getColumnModel().getColumn(3).setPreferredWidth(40);
        tableConsolidaciones.getColumnModel().getColumn(4).setPreferredWidth(40);
        tableConsolidaciones.getColumnModel().getColumn(5).setPreferredWidth(120);
        tableConsolidaciones.getColumnModel().getColumn(7).setPreferredWidth(40);
        
        tableConsolidaciones.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.isAltDown() && e.getKeyCode() == 67) {
                    showDialog();
                }
            }
        });
    }
    
    private void setButtons() {
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableComponents(false);
                if (dateInicio.getDate() != null && dateFinal.getDate() != null) {
                    fechaInicio = (dateInicio.getDate() != null) ? Formato.format(dateInicio.getDate()) : "00-00-0000";
                    fechaIni = fechaInicio.split("-");
                    fechaFinal = (dateFinal.getDate() != null) ? Formato.format(dateFinal.getDate()) : "00-00-0000";
                    fechaFin = fechaFinal.split("-");
                    
                    if (Integer.parseInt(fechaIni[0]) <= 0 || Integer.parseInt(fechaIni[0]) >= 32)
                        JOptionPane.showMessageDialog(objIdex, "Dia de la fecha inicio no puede ser menor de 1 o mayor que 32", "Error en la fecha de inicio", JOptionPane.ERROR_MESSAGE);
                    else if (Integer.parseInt(fechaIni[1]) <= 0 || Integer.parseInt(fechaIni[1]) >= 13)
                        JOptionPane.showMessageDialog(objIdex, "Mes de la fecha inicio no puede ser menor de 1 o mayor que 12", "Error en la fecha de inicio", JOptionPane.ERROR_MESSAGE);
                    else if (Integer.parseInt(fechaIni[2]) <= 2015)
                        JOptionPane.showMessageDialog(objIdex, "Año de la fecha termino no puede ser menor de 2015", "Error en la fecha de termino", JOptionPane.ERROR_MESSAGE);
                    else if (Integer.parseInt(fechaFin[0]) <= 0 || Integer.parseInt(fechaFin[0]) >= 32)
                        JOptionPane.showMessageDialog(objIdex, "Dia de la fecha inicio no puede ser menor de 1 o mayor que 32", "Error en la fecha de termino", JOptionPane.ERROR_MESSAGE);
                    else if (Integer.parseInt(fechaFin[1]) <= 0 || Integer.parseInt(fechaFin[1]) >= 13)
                        JOptionPane.showMessageDialog(objIdex, "Mes de la fecha termino no puede ser menor de 1 o mayor que 12", "Error en la fecha de termino", JOptionPane.ERROR_MESSAGE);
                    else if (Integer.parseInt(fechaFin[2]) <= 2015)
                        JOptionPane.showMessageDialog(objIdex, "Año de la fecha termino no puede ser menor de 2015", "Error en la fecha de inicio", JOptionPane.ERROR_MESSAGE);
                    else if(dateInicio.getDate().after(dateFinal.getDate()))
                        JOptionPane.showMessageDialog(objIdex, "La fecha de inicio no puede ser mayor que la fecha de termino", "Error en el orden de las fechas", JOptionPane.ERROR_MESSAGE);
                    else{
                        controller.getConsolidacionesByRango(fechaIni[2] + fechaIni[1] + fechaIni[0], fechaFin[2] + fechaFin[1] + fechaFin[0]);
                        System.out.println("FechaInicio: " + fechaInicio + " FechaFinal: " + fechaFinal);
                    }
                } else {
                    if (dateInicio.getDate() == null)
                        JOptionPane.showMessageDialog(objIdex, "Fecha de inicio incorrecta o vacia", "Error en la fecha de inicio", JOptionPane.ERROR_MESSAGE);
                    else if (dateFinal.getDate() == null)
                        JOptionPane.showMessageDialog(objIdex, "Fecha de termino incorrecta o vacia", "Error en la fecha de termino", JOptionPane.ERROR_MESSAGE);
                }
                enableComponents(true);
            }
        });
        
        btnSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnSearch.doClick();
                }
                if (e.isControlDown() && e.isAltDown() && e.getKeyCode() == 67) {
                    showDialog();
                }
            }
        });
    }
    
    private void enableComponents(Boolean enabled) {
        if (enabled) {
            objIdex.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            objIdex.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        }
        btnSearch.setEnabled(enabled);
        dateFinal.setEnabled(enabled);
        dateInicio.setEnabled(enabled);
    }
    
    private void showDialog() {
        dialogOrigin.setContentPane(origin);
        dialogOrigin.setVisible(true);
        dialogOrigin.setSize(460, 250);
        dialogOrigin.setLocationRelativeTo(null);
        dialogOrigin.setTitle("Datos de la conexion");
        try {
            dialogOrigin.setIconImage(new ImageIcon(getClass().getResource("/contents/r.png")).getImage());
        } catch (Exception err) {
            System.out.println("Error: " + err);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                index obj = new index();
                obj.getContentPane().setBackground(Color.WHITE);
                obj.setExtendedState(MAXIMIZED_BOTH);
                obj.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private com.toedter.calendar.JDateChooser dateFinal;
    private com.toedter.calendar.JDateChooser dateInicio;
    private javax.swing.JDialog dialogOrigin;
    private javax.swing.JPanel footer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableConsolidaciones;
    private javax.swing.JLabel textDatabase;
    private javax.swing.JLabel textHost;
    // End of variables declaration//GEN-END:variables
}
