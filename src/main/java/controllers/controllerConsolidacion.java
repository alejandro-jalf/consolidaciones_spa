/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.modelConsolidaciones;
import configs.config;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.LoaderInstancias;
/**
 *
 * @author Jose Alejandro Lopez
 */
public class controllerConsolidacion {
    
    private modelConsolidaciones models = null;
    private config conf = null;
    private String host = "";
    private String port = "";
    private String database = "";
    private String user = "";
    private String password = "";
    private JSONArray data = null;
    private JTable tablaConsolidacion = null;
    private String rowExample[] = new String[7];
    private String dateArray[] = null;
    private String hourArray[] = null;
    private String resultado = "";
    private String document = "";
    private JSONObject listDocumensByReferencia = null;
    private JSONArray listTranferenciasZR = null, listTranferenciasVC = null, listTranferenciasOU = null, listTranferenciasJL = null, listTranferenciasBO = null;
    private JSONArray listEntradasZR = null, listEntradasVC = null, listEntradasOU = null, listEntradasJL = null, listEntradasBO = null;
    private String listDocumentsSucursales = "";
    private Pattern patron = null;
    private Matcher mat = null;
    private JFrame ventana = null;
    private LoaderInstancias loaderInstancias = null;
    
    public controllerConsolidacion(LoaderInstancias loaderInstancias) {
        this.loaderInstancias = loaderInstancias;
        this.ventana = loaderInstancias.getVentana();
        this.tablaConsolidacion = loaderInstancias.getTableConsolidaciones();
        this.conf = new config(loaderInstancias);
        this.host = conf.getHostLocal();
        this.port = conf.getPort();
        this.database = conf.getDatabaseLocal(); 
        this.user = conf.getUser();
        this.password = conf.getPassword();
        this.listDocumensByReferencia = new JSONObject();
        
        this.listTranferenciasZR = new JSONArray();
        this.listTranferenciasVC = new JSONArray();
        this.listTranferenciasOU = new JSONArray();
        this.listTranferenciasJL = new JSONArray();
        this.listTranferenciasBO = new JSONArray();
        
        this.listEntradasZR = new JSONArray();
        this.listEntradasVC = new JSONArray();
        this.listEntradasOU = new JSONArray();
        this.listEntradasJL = new JSONArray();
        this.listEntradasBO = new JSONArray();
        
        loaderInstancias.getLabelHost().setText(conf.getHostName());
        loaderInstancias.getLabelDatabase().setText(conf.getDatabaseName());
        
        this.models = new modelConsolidaciones(this.host, this.port, this.database, this.user, this.password, loaderInstancias);
    }
    
    public String getConsolidacionesByRango(String dateInicio, String dateFinal) {
        listDocumensByReferencia.put("listDocumentsZaragoza", "");
        listDocumensByReferencia.put("listDocumentsCentro", "");
        listDocumensByReferencia.put("listDocumentsOluta", "");
        listDocumensByReferencia.put("listDocumentsJaltipan", "");
        listDocumensByReferencia.put("listDocumentsBodega", "");
        
        listDocumensByReferencia.put("entradaZaragoza", listEntradasZR);
        listDocumensByReferencia.put("entradaCentro", listEntradasVC);
        listDocumensByReferencia.put("entradaOluta", listEntradasOU);
        listDocumensByReferencia.put("entradaJaltipan", listEntradasJL);
        listDocumensByReferencia.put("entradaBodega", listEntradasBO);
        
        listDocumensByReferencia.put("transferenciasZaragoza", listTranferenciasZR);
        listDocumensByReferencia.put("transferenciasCentro", listTranferenciasVC);
        listDocumensByReferencia.put("transferenciasOluta", listTranferenciasOU);
        listDocumensByReferencia.put("transferenciasJaltipan", listTranferenciasJL);
        listDocumensByReferencia.put("transferenciasBodega", listTranferenciasBO);
        

        data = models.getTransferenciasToday(dateInicio, dateFinal, this.host, this.database);
        document = "";
        if (data != null){
            if (data.length() == 2) {
                try {
                    if (data.getString(0).equals("error")) {
                        JOptionPane.showMessageDialog(ventana, "El servidor al que se quiere conectar esta sin conexion", "Error en la conexion con base de datos", JOptionPane.ERROR_MESSAGE);
                        return "";
                    }
                } catch (Exception e) {
                    System.out.println("Es un objecto y no un error: " + e);
                }
            }
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("Fecha");
            modelo.addColumn("Hora");
            modelo.addColumn("Transferencia");
            modelo.addColumn("Entrada");
            modelo.addColumn("Destino");
            modelo.addColumn("Observaciones");
            modelo.addColumn("Estatus");

            JSONObject item;

            for (int i = 0; i < data.length(); i++) {
                item = data.getJSONObject(i);
                document = item.getString("Entrada");
                saveTransferencias(item.getString("Referencia"), document);
            }

            getDocumentsToday(dateInicio, dateFinal);

            for (int x = 0; x < data.length(); x++) {
                item = data.getJSONObject(x);
                document = item.getString("Entrada");

                rowExample[0] = parseDate(item.getString("Fecha"));
                rowExample[1] = parseHour(item.getString("Hora"));
                rowExample[2] = item.getString("Documento");
                rowExample[3] = item.getString("Entrada");
                rowExample[4] = item.getString("Referencia");
                rowExample[5] = item.getString("Observaciones");
                rowExample[6] = getVerificacion(item.getString("Referencia"), document);

                modelo.addRow(rowExample);
            }

            tablaConsolidacion.setModel(modelo);
        }
        
        return "";
    }
    
    private void saveTransferencias(String referencia, String transferencia) {
        referencia = referencia.toLowerCase().trim();
        listDocumentsSucursales = "";
        if (referencia.equals("zaragoza")) {
            listTranferenciasZR = listDocumensByReferencia.getJSONArray("transferenciasZaragoza");
            listTranferenciasZR.put(transferencia);
            listDocumensByReferencia.put("transferenciasZaragoza", listTranferenciasZR);
            
            listDocumentsSucursales = listDocumensByReferencia.getString("listDocumentsZaragoza");
            if (listDocumentsSucursales.length() > 0) listDocumentsSucursales += ",'";
            else listDocumentsSucursales += "'";
            listDocumentsSucursales += transferencia + "'";
            listDocumensByReferencia.put("listDocumentsZaragoza", listDocumentsSucursales);
        }
        if (referencia.equals("centro") || referencia.equals("victoria")) {
            listTranferenciasVC = listDocumensByReferencia.getJSONArray("transferenciasCentro");
            listTranferenciasVC.put(transferencia);
            listDocumensByReferencia.put("transferenciasCentro", listTranferenciasVC);
            
            listDocumentsSucursales = listDocumensByReferencia.getString("listDocumentsCentro");
            if (listDocumentsSucursales.length() > 0) listDocumentsSucursales += ",'";
            else listDocumentsSucursales += "'";
            listDocumentsSucursales += transferencia + "'";
            listDocumensByReferencia.put("listDocumentsCentro", listDocumentsSucursales);
        }
        if (referencia.equals("oluta")) {
            listTranferenciasOU = listDocumensByReferencia.getJSONArray("transferenciasOluta");
            listTranferenciasOU.put(transferencia);
            listDocumensByReferencia.put("transferenciasOluta", listTranferenciasOU);
            
            listDocumentsSucursales = listDocumensByReferencia.getString("listDocumentsOluta");
            if (listDocumentsSucursales.length() > 0) listDocumentsSucursales += ",'";
            else listDocumentsSucursales += "'";
            listDocumentsSucursales += transferencia + "'";
            listDocumensByReferencia.put("listDocumentsOluta", listDocumentsSucursales);
        }
        if (referencia.equals("jaltipan")) {
            listTranferenciasJL = listDocumensByReferencia.getJSONArray("transferenciasJaltipan");
            listTranferenciasJL.put(transferencia);
            listDocumensByReferencia.put("transferenciasJaltipan", listTranferenciasJL);
            
            listDocumentsSucursales = listDocumensByReferencia.getString("listDocumentsJaltipan");
            if (listDocumentsSucursales.length() > 0) listDocumentsSucursales += ",'";
            else listDocumentsSucursales += "'";
            listDocumentsSucursales += transferencia + "'";
            listDocumensByReferencia.put("listDocumentsJaltipan", listDocumentsSucursales);
        }
        if (
            referencia.equals("bodega") ||
            referencia.equals("bocardo") ||
            referencia.equals("bodega bocardo")
        ) {
            listTranferenciasBO = listDocumensByReferencia.getJSONArray("transferenciasBodega");
            listTranferenciasBO.put(transferencia);
            listDocumensByReferencia.put("transferenciasBodega", listTranferenciasBO);
            
            listDocumentsSucursales = listDocumensByReferencia.getString("listDocumentsBodega");
            if (listDocumentsSucursales.length() > 0) listDocumentsSucursales += ",'";
            else listDocumentsSucursales += "'";
            listDocumentsSucursales += transferencia + "'";
            listDocumensByReferencia.put("listDocumentsBodega", listDocumentsSucursales);
        }
    }
    
    private void getDocumentsToday(String dateInicio, String dateFinal) {
        if (listDocumensByReferencia.getString("listDocumentsZaragoza").length() > 0) {
            listEntradasZR = models.getEntradasToday(
                dateInicio,
                dateFinal,
                conf.getHostSuper1(),
                conf.getDatabaseSuper1(),
                listDocumensByReferencia.getString("listDocumentsZaragoza")
            );
            if (listEntradasZR == null) {
                listEntradasZR = new JSONArray();
                listEntradasZR.put("error");
                listEntradasZR.put("Fallo en la conexion");
            }
            listDocumensByReferencia.put("entradaZaragoza", listEntradasZR);
        }
        if (listDocumensByReferencia.getString("listDocumentsCentro").length() > 0) {
            listEntradasVC = models.getEntradasToday(
                dateInicio,
                dateFinal,
                conf.getHostVictoria(),
                conf.getDatabaseVictoria(),
                listDocumensByReferencia.getString("listDocumentsCentro")
            );
            if (listEntradasVC == null) {
                listEntradasVC = new JSONArray();
                listEntradasVC.put("error");
                listEntradasVC.put("Fallo en la conexion");
            }
            listDocumensByReferencia.put("entradaCentro", listEntradasVC);
        }
        if (listDocumensByReferencia.getString("listDocumentsOluta").length() > 0) {
            listEntradasOU = models.getEntradasToday(
                dateInicio,
                dateFinal,
                conf.getHostOluta(),
                conf.getDatabaseOluta(),
                listDocumensByReferencia.getString("listDocumentsOluta")
            );
            if (listEntradasOU == null) {
                listEntradasOU = new JSONArray();
                listEntradasOU.put("error");
                listEntradasOU.put("Fallo en la conexion");
            }
            listDocumensByReferencia.put("entradaOluta", listEntradasOU);
        }
        if (listDocumensByReferencia.getString("listDocumentsJaltipan").length() > 0) {
            listEntradasJL = models.getEntradasToday(
                dateInicio,
                dateFinal,
                conf.getHostJaltipan(),
                conf.getDatabaseJaltipan(),
                listDocumensByReferencia.getString("listDocumentsJaltipan")
            );
            if (listEntradasJL == null) {
                listEntradasJL = new JSONArray();
                listEntradasJL.put("error");
                listEntradasJL.put("Fallo en la conexion");
            }
            listDocumensByReferencia.put("entradaJaltipan", listEntradasJL);
        }
        if (listDocumensByReferencia.getString("listDocumentsBodega").length() > 0) {
            listEntradasBO = models.getEntradasToday(
                dateInicio,
                dateFinal,
                conf.getHostBodega(),
                conf.getDatabaseBodega(),
                listDocumensByReferencia.getString("listDocumentsBodega")
            );
            if (listEntradasBO == null) {
                listEntradasBO = new JSONArray();
                listEntradasBO.put("error");
                listEntradasBO.put("Fallo en la conexion");
            }
            listDocumensByReferencia.put("entradaBodega", listEntradasBO);
        }
    }
    
    private String getVerificacion(String referencia, String document) {
        referencia = referencia.toLowerCase().trim();
        resultado = "Fallo";
        JSONArray dataSucursalesThi = new JSONArray();
        if (referencia.equals("zaragoza")) dataSucursalesThi = listDocumensByReferencia.getJSONArray("entradaZaragoza");
        if (referencia.equals("centro") || referencia.equals("victoria")) dataSucursalesThi = listDocumensByReferencia.getJSONArray("entradaCentro");
        if (referencia.equals("oluta")) dataSucursalesThi = listDocumensByReferencia.getJSONArray("entradaOluta");
        if (referencia.equals("jaltipan")) dataSucursalesThi = listDocumensByReferencia.getJSONArray("entradaJaltipan");
        if (
            referencia.equals("bodega") ||
            referencia.equals("bocardo") ||
            referencia.equals("bodega bocardo")
        ) dataSucursalesThi = listDocumensByReferencia.getJSONArray("entradaBodega");
        
        if (dataSucursalesThi.length() == 2) {
            try {
                if (dataSucursalesThi.getString(0).equals("error"))
                return "Sin conexion";
            } catch (Exception e) {
                System.out.println("No es un error" + e);
            }
        }
        
        for (int x = 0; x < dataSucursalesThi.length(); x++) {
            if (dataSucursalesThi.getString(x).equals(document)) {
                resultado = "Exito";
                break;
            }
        }
        return resultado;
    }
    
    private String parseDate(String date) {
        dateArray = date.split(" ");
        dateArray = dateArray[0].split("-");
        return dateArray[2] + "/" + dateArray[1] + "/" + dateArray[0];
    }
    
    private String parseHour(String hour) {
        hourArray = hour.split(" ");
        return hourArray[1];
    }
    
    private Boolean changeHostAndDatabase(String host) {
        host = host.toLowerCase().trim();
        if (host.equals("zr")) {
            this.host = conf.getHostSuper1();
            this.database = conf.getDatabaseSuper1();
            return true;
        }
        if (host.equals("vc")) {
            this.host = conf.getHostVictoria();
            this.database = conf.getDatabaseVictoria();
            return true;
        }
        if (host.equals("ou")) {
            this.host = conf.getHostSuper1();
            this.database = conf.getDatabaseSuper1();
            return true;
        }
        if (host.equals("jl")) {
            this.host = conf.getHostJaltipan();
            this.database = conf.getDatabaseJaltipan();
            return true;
        }
        if (host.equals("bo")) {
            this.host = conf.getHostBodega();
            this.database = conf.getDatabaseBodega();
            return true;
        }
   
        return false;
    }
}