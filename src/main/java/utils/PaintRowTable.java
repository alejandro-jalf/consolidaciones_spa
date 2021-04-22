/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Jose Alejandro Lopez
 */
public class PaintRowTable extends DefaultTableCellRenderer {
    
//    private Component component;
    private String resultado = "Fallo";
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
 
//        component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.

        resultado = (String) table.getValueAt(row, 6);
//        System.out.println( row + (String) table.getValueAt(row, 4) + " " + (String) table.getValueAt(row, 5) + "  " + resultado);
        
        if (resultado.equals("Fallo")) {
            setBackground(Color.YELLOW);
            setForeground(Color.BLACK);
        }
        else if (resultado.equals("Sin conexion")) {
            setBackground(Color.RED);
            setForeground(Color.WHITE);
        }
        else {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        }
        
        setBorder(new LineBorder(Color.GRAY));
//        setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(255, 255, 255)));
        setOpaque(true);
        
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
