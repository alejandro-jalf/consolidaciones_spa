/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;

/**
 *
 * @author Jose Alejandro Lopez
 */
public class LoaderInstancias {
    
    private JFrame ventana = null;
    private JTable tableConsolidaciones = null;
    private JLabel labelHost = null;
    private JLabel labelDatabse = null;
    
    public void setVentana(JFrame ventana) { this.ventana = ventana; }
    public void setLabelHost(JLabel labelHost) { this.labelHost = labelHost; }
    public void setLabelDatabase(JLabel labelDatabase) { this.labelDatabse = labelDatabase; }
    public void setTableConsolidaciones(JTable tableConsolidaciones) { this.tableConsolidaciones = tableConsolidaciones; }
    
    public JFrame getVentana() { return this.ventana; }
    public JTable getTableConsolidaciones() { return this.tableConsolidaciones; }
    public JLabel getLabelHost() { return this.labelHost; }
    public JLabel getLabelDatabase() { return this.labelDatabse; }
}
