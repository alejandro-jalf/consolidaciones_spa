/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import models.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.LoaderInstancias;

/**
 *
 * @author Jose Alejandro Lopez
 */
public class modelConsolidaciones {
    
    private Conexion conexion = null;
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private JSONArray data = null, data2 = null;
    private JSONObject row = null;
    private LoaderInstancias loaderInstancias = null;
    
    public modelConsolidaciones(String host, String port, String database, String user, String password, LoaderInstancias loaderInstancias) {
        this.conexion = new Conexion(host, port, database, user, password, loaderInstancias);
        con = conexion.getConexion();
        this.data = new JSONArray();
        this.data2 = new JSONArray();
        this.loaderInstancias = loaderInstancias;
        
        if (con == null)
            JOptionPane.showMessageDialog(loaderInstancias.getVentana(), "Revise la direccion y conexion del host o base de datos", "Error en la conexion con base de datos", JOptionPane.ERROR_MESSAGE);
    }
    
    public JSONArray getTransferenciasToday(String dateInicio, String dateFinal, String host, String database) {
        try {
            data = new JSONArray();
            conexion.setHostAndDatabase(host, database);
            con = conexion.getConexion();
            if (con == null) {
                JOptionPane.showMessageDialog(loaderInstancias.getVentana(), "Revise la direccion y conexion del host o base de datos", "Error en la conexion con base de datos", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            String SQL = "WITH tranferencias (\n" +
                "    Fecha, Documento, Referencia, DescripcionAlmacen, Hora, Observaciones\n" +
                ")\n" +
                "    AS\n" +
                "(\n" +
                "    SELECT\n" +
                "        Fecha, Documento, Referencia, DescripcionAlmacen, Hora, Observaciones\n" +
                "    FROM QVDEMovAlmacen\n" +
                "    WHERE TipoDocumento = 'T'\n" +
                "        AND Estatus = 'E'\n" +
                "        AND Fecha BETWEEN CAST('" + dateInicio + "' AS datetime) AND CAST('" + dateFinal + "' AS datetime)\n" +
                "    GROUP BY Fecha, Documento, Referencia, DescripcionAlmacen, Hora, Observaciones\n" +
                ")\n" +
                "\n" +
                "SELECT\n" +
                "    T.Fecha, T.Documento, T.Referencia, T.DescripcionAlmacen, T.Hora, E.Documento AS Entrada, T.Observaciones\n" +
                "FROM QVDEMovAlmacen AS E\n" +
                "INNER JOIN tranferencias AS T\n" +
                "ON E.Referencia = T.Documento\n" +
                "WHERE TipoDocumento = 'A'\n" +
                "    AND E.Estatus = 'E'\n" +
                "    AND E.Fecha BETWEEN CAST('" + dateInicio + "' AS datetime) AND CAST('" + dateFinal + "' AS datetime)\n" +
                "GROUP BY T.Fecha, T.Documento, T.Referencia, T.DescripcionAlmacen, T.Hora, E.Documento, T.Observaciones\n" +
                "ORDER BY T.Fecha DESC, T.Hora DESC";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                row = new JSONObject();
                row.put("Fecha", rs.getString("Fecha"));
                row.put("Documento", rs.getString("Documento"));
                row.put("Referencia", rs.getString("Referencia"));
                row.put("DescripcionAlmacen", rs.getString("DescripcionAlmacen"));
                row.put("Hora", rs.getString("Hora"));
                row.put("Entrada", rs.getString("Entrada"));
                row.put("Observaciones", rs.getString("Observaciones"));
                data.put(row);
                
//                System.out.println(rs.getString("Fecha") + " " + rs.getString("Documento") +
//                        " " + rs.getString("Referencia") + " " + rs.getString("DescripcionAlmacen") +
//                        " " + rs.getString("Hora") + rs.getString("Entrada"));
            }
            return data;
        }
        catch(JSONException je) {
            System.out.println("Error en valores json: " + je);
            je.printStackTrace();
            return data;
        }
        catch(SQLException sqle) {
            System.out.println("Error en la base de datos en getTransferenciasToday: " + sqle);
            sqle.printStackTrace();
            data.clear();
            data.put("error");
            data.put("Fallo con la conexion en base de datos");
            return data;
        }
        catch (Exception e) {
            System.out.println("Error al obtener las consolidaciones: " + e);
            e.printStackTrace();
            e.printStackTrace();
            return data;
        }
        finally {
            if (stmt != null) try { stmt.close(); } catch(Exception e) { System.out.println(e); }
            conexion.closeConexion();
        }
    }
    
    public JSONArray getEntradasToday(String dateInicio, String dateFinal, String host, String database, String listDocuments) {
        try {
            data2 = new JSONArray();
            conexion.setHostAndDatabase(host, database);
            con = conexion.getConexion();
            if (con == null) {
                JOptionPane.showMessageDialog(loaderInstancias.getVentana(), "Revise la direccion y conexion del host o base de datos", "Error en la conexion con base de datos", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            String SQL = "" +
                "SELECT" +
                "    Documento\n" +
                "FROM QVDEMovAlmacen\n" +
                "WHERE TipoDocumento = 'A'\n" +
                "    AND Estatus = 'E'\n" +
                "    AND Fecha BETWEEN CAST('" + dateInicio + "' AS datetime) AND CAST('" + dateFinal + "' AS datetime)\n" +
                "    AND Documento IN (" + listDocuments + ")\n" +
                "GROUP BY Documento";
//            System.out.println("Query: " + SQL);
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                data2.put(rs.getString("Documento"));
            }
            return data2;
        }
        catch(JSONException je) {
            System.out.println("Error en valores json en getEntradasToday: " + je);
            return data2;
        }
        catch(SQLException sqle) {
            System.out.println("Error en la base de datos en getEntradasToday: " + sqle);
            data2.clear();
            data2.put("error");
            data2.put("Fallo con la conexion en base de datos");
            return data2;
        }
        catch (Exception e) {
            System.out.println("Error al obtener las entradas: " + e);
            e.printStackTrace();
            return data2;
        }
        finally {
            if (stmt != null) try { stmt.close(); } catch(Exception e) { System.out.println(e); }
            conexion.closeConexion();
        }
    }
}
