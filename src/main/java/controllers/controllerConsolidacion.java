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
    private String rowExample[] = new String[8];
    private String dateArray[] = null;
    private String hourArray[] = null;
    private String resultado = "";
    private String document = "";
    private JSONObject listDocumensByReferencia = null;
    private JSONArray listTranferenciasZR = null, listTranferenciasVC = null, listTranferenciasOU = null, listTranferenciasJL = null, listTranferenciasBO = null, listTranferenciasSA = null;
    private JSONArray listEntradasZR = null, listEntradasVC = null, listEntradasOU = null, listEntradasJL = null, listEntradasBO = null, listEntradasSA = null;
    private String listDocumentsSucursales = "";
    private Pattern patron = null;
    private Matcher mat = null;
    private JFrame ventana = null;
    private LoaderInstancias loaderInstancias = null;
    private String hostConsult = "";
    
    public controllerConsolidacion(LoaderInstancias loaderInstancias, config conf) {
        this.loaderInstancias = loaderInstancias;
        this.ventana = loaderInstancias.getVentana();
        this.tablaConsolidacion = loaderInstancias.getTableConsolidaciones();
        this.conf = conf;
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
        this.listTranferenciasSA = new JSONArray();
        
        this.listEntradasZR = new JSONArray();
        this.listEntradasVC = new JSONArray();
        this.listEntradasOU = new JSONArray();
        this.listEntradasJL = new JSONArray();
        this.listEntradasBO = new JSONArray();
        this.listEntradasSA = new JSONArray();
        
        loaderInstancias.getLabelHost().setText(conf.getHostName().toUpperCase());
        loaderInstancias.getLabelDatabase().setText(conf.getDatabaseName().toUpperCase());
        
        this.models = new modelConsolidaciones(this.host, this.port, this.database, this.user, this.password, loaderInstancias);
    }
    
    public String getConsolidacionesByRango(String dateInicio, String dateFinal) {
        listDocumensByReferencia.put("listDocumentsZaragoza", "");
        listDocumensByReferencia.put("listDocumentsCentro", "");
        listDocumensByReferencia.put("listDocumentsOluta", "");
        listDocumensByReferencia.put("listDocumentsJaltipan", "");
        listDocumensByReferencia.put("listDocumentsBodega", "");
        listDocumensByReferencia.put("listDocumentsSanAndres", "");
        
        listDocumensByReferencia.put("entradaZaragoza", listEntradasZR);
        listDocumensByReferencia.put("entradaCentro", listEntradasVC);
        listDocumensByReferencia.put("entradaOluta", listEntradasOU);
        listDocumensByReferencia.put("entradaJaltipan", listEntradasJL);
        listDocumensByReferencia.put("entradaBodega", listEntradasBO);
        listDocumensByReferencia.put("entradaSanAndres", listEntradasSA);
        
        listDocumensByReferencia.put("transferenciasZaragoza", listTranferenciasZR);
        listDocumensByReferencia.put("transferenciasCentro", listTranferenciasVC);
        listDocumensByReferencia.put("transferenciasOluta", listTranferenciasOU);
        listDocumensByReferencia.put("transferenciasJaltipan", listTranferenciasJL);
        listDocumensByReferencia.put("transferenciasBodega", listTranferenciasBO);
        listDocumensByReferencia.put("transferenciasSanAndres", listTranferenciasSA);
        

        data = models.getTransferenciasToday(dateInicio, dateFinal, conf.getHostLocal(), conf.getDatabaseLocal());
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
            modelo.addColumn("Referencia");
            modelo.addColumn("Almacen Destino");
            modelo.addColumn("Observaciones");
            modelo.addColumn("Estatus");

            JSONObject item;

            for (int i = 0; i < data.length(); i++) {
                item = data.getJSONObject(i);
                document = item.getString("Entrada");
                saveTransferencias(item.getString("AlmacenDestino"), document);
            }

            getDocumentsToday(dateInicio, dateFinal);

            for (int x = 0; x < data.length(); x++) {
                item = data.getJSONObject(x);
                document = item.getString("Entrada");

                rowExample[0] = parseDate(item.getString("Fecha"));
                rowExample[1] = parseHour(item.getString("Hora"));
                rowExample[2] = item.getString("Documento");
                rowExample[3] = item.getString("Entrada");
                rowExample[4] = item.getString("Referencia").toUpperCase();
                rowExample[5] = item.getString("AlmacenDestino");
                rowExample[6] = item.getString("Observaciones");
                rowExample[7] = getVerificacion(item.getString("AlmacenDestino"), document);

                modelo.addRow(rowExample);
            }

            tablaConsolidacion.setModel(modelo);
            
            tablaConsolidacion.getColumnModel().getColumn(0).setPreferredWidth(40);
            tablaConsolidacion.getColumnModel().getColumn(1).setPreferredWidth(40);
            tablaConsolidacion.getColumnModel().getColumn(2).setPreferredWidth(40);
            tablaConsolidacion.getColumnModel().getColumn(3).setPreferredWidth(40);
            tablaConsolidacion.getColumnModel().getColumn(4).setPreferredWidth(40);
            tablaConsolidacion.getColumnModel().getColumn(5).setPreferredWidth(120);
            tablaConsolidacion.getColumnModel().getColumn(7).setPreferredWidth(40);
        }
        
        return "";
    }
    
    private void saveTransferencias(String referencia, String transferencia) {
        referencia = referencia.toLowerCase().trim();
        listDocumentsSucursales = "";
        if (referencia.equals("spa-super1-punto de venta")) {
            listTranferenciasZR = listDocumensByReferencia.getJSONArray("transferenciasZaragoza");
            listTranferenciasZR.put(transferencia);
            listDocumensByReferencia.put("transferenciasZaragoza", listTranferenciasZR);
            
            listDocumentsSucursales = listDocumensByReferencia.getString("listDocumentsZaragoza");
            if (listDocumentsSucursales.length() > 0) listDocumentsSucursales += ",'";
            else listDocumentsSucursales += "'";
            listDocumentsSucursales += transferencia + "'";
            listDocumensByReferencia.put("listDocumentsZaragoza", listDocumentsSucursales);
        }
        if (
            referencia.equals("spa-centro-punto de venta") ||
            referencia.equals("spa-centro-confiteria")
        ) {
            listTranferenciasVC = listDocumensByReferencia.getJSONArray("transferenciasCentro");
            listTranferenciasVC.put(transferencia);
            listDocumensByReferencia.put("transferenciasCentro", listTranferenciasVC);
            
            listDocumentsSucursales = listDocumensByReferencia.getString("listDocumentsCentro");
            if (listDocumentsSucursales.length() > 0) listDocumentsSucursales += ",'";
            else listDocumentsSucursales += "'";
            listDocumentsSucursales += transferencia + "'";
            listDocumensByReferencia.put("listDocumentsCentro", listDocumentsSucursales);
        }
        if (referencia.equals("spa-oluta-punto de venta")) {
            listTranferenciasOU = listDocumensByReferencia.getJSONArray("transferenciasOluta");
            listTranferenciasOU.put(transferencia);
            listDocumensByReferencia.put("transferenciasOluta", listTranferenciasOU);
            
            listDocumentsSucursales = listDocumensByReferencia.getString("listDocumentsOluta");
            if (listDocumentsSucursales.length() > 0) listDocumentsSucursales += ",'";
            else listDocumentsSucursales += "'";
            listDocumentsSucursales += transferencia + "'";
            listDocumensByReferencia.put("listDocumentsOluta", listDocumentsSucursales);
        }
        if (
            referencia.equals("spa-jaltipan-punto de venta") ||
            referencia.equals("spa-jaltipan-confiteria")
        ) {
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
            referencia.equals("bodega bocardo") ||
            referencia.equals("almacen bocardo") ||
            referencia.equals("mercancias en transito") ||
            referencia.equals("spa-almacen")
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
        if (referencia.equals("spa-san andres-punto de venta")) {
            listTranferenciasSA = listDocumensByReferencia.getJSONArray("transferenciasSanAndres");
            listTranferenciasSA.put(transferencia);
            listDocumensByReferencia.put("transferenciasSanAndres", listTranferenciasBO);
            
            listDocumentsSucursales = listDocumensByReferencia.getString("listDocumentsSanAndres");
            if (listDocumentsSucursales.length() > 0) listDocumentsSucursales += ",'";
            else listDocumentsSucursales += "'";
            listDocumentsSucursales += transferencia + "'";
            listDocumensByReferencia.put("listDocumentsSanAndres", listDocumentsSucursales);
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
            hostConsult = (
                conf.getHostName().equals("LocalHost") &&
                conf.getDatabaseName().equals("SPACENTRO")
            ) ? conf.getHostLocal() : conf.getHostVictoria();
            listEntradasVC = models.getEntradasToday(
                dateInicio,
                dateFinal,
                hostConsult,
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
            hostConsult = (
                conf.getHostName().equals("LocalHost") &&
                conf.getDatabaseName().equals("SPAJALTIPAN")
            ) ? conf.getHostLocal() : conf.getHostJaltipan();
            listEntradasJL = models.getEntradasToday(
                dateInicio,
                dateFinal,
                hostConsult,
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
            hostConsult = (
                conf.getHostName().equals("LocalHost") &&
                conf.getDatabaseName().equals("SPABODEGA")
            ) ? conf.getHostLocal() : conf.getHostBodega();
            listEntradasBO = models.getEntradasToday(
                dateInicio,
                dateFinal,
                hostConsult,
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
        if (listDocumensByReferencia.getString("listDocumentsSanAndres").length() > 0) {
            listEntradasSA = models.getEntradasToday(
                dateInicio,
                dateFinal,
                conf.getHostBodega(),
                conf.getDatabaseBodega(),
                listDocumensByReferencia.getString("listDocumentsSanAndres")
            );
            if (listEntradasSA == null) {
                listEntradasSA = new JSONArray();
                listEntradasSA.put("error");
                listEntradasSA.put("Fallo en la conexion");
            }
            listDocumensByReferencia.put("entradaSanAndres", listEntradasSA);
        }
    }
    
    private String getVerificacion(String referencia, String document) {
        referencia = referencia.toLowerCase().trim();
        resultado = "Fallo";
        JSONArray dataSucursalesThi = new JSONArray();
        if (referencia.equals("spa-super1-punto de venta")) dataSucursalesThi = listDocumensByReferencia.getJSONArray("entradaZaragoza");
        if (
            referencia.equals("spa-centro-punto de venta") ||
            referencia.equals("spa-centro-confiteria")
        ) dataSucursalesThi = listDocumensByReferencia.getJSONArray("entradaCentro");
        if (referencia.equals("spa-oluta-punto de venta")) dataSucursalesThi = listDocumensByReferencia.getJSONArray("entradaOluta");
        if (
            referencia.equals("spa-jaltipan-punto de venta") ||
            referencia.equals("spa-jaltipan-confiteria")
        ) dataSucursalesThi = listDocumensByReferencia.getJSONArray("entradaJaltipan");
        if (
            referencia.equals("bodega bocardo") ||
            referencia.equals("almacen bocardo") ||
            referencia.equals("mercancias en transito") ||
            referencia.equals("spa-almacen")
        ) dataSucursalesThi = listDocumensByReferencia.getJSONArray("entradaBodega");
        if (referencia.equals("spa-san andres-punto de venta")) dataSucursalesThi = listDocumensByReferencia.getJSONArray("entradaSanAndres");
        
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
}
