/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Jose Alejandro Lopez
 */
public class ModifyStrings {
    
    private String stringResult = "";
    private String arrayString[] = null;
    
    public String paragraphString(String cadena) {
        if (cadena.length() == 0) return cadena;
        
        arrayString = cadena.split(" ");
        stringResult = "";
        
        for (int i = 0; i < arrayString.length; i++) {
            stringResult += arrayString[i] + " ";
            if (i % 9 == 0 && i != 0) stringResult += "\n";
        }
        return stringResult;
    }
    
}
