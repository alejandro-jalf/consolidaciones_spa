/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import utils.LoaderInstancias;
import utils.ModifyStrings;

/**
 *
 * @author Jose Alejandro Lopez
 */
public class Conexion {
    
    private String host = "", port = "", database = "", user = "", password = "";
    private Connection con = null;
    private LoaderInstancias loaderInstancias = null;
    private ModifyStrings modify = null;
    
    public Conexion(String host, String port, String database, String user, String password, LoaderInstancias loaderInstancias) {
        this.loaderInstancias = loaderInstancias;
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        this.modify = new ModifyStrings();
    }
    
    public void setHostAndDatabase(String host, String database) {
        this.host = host;
        this.database = database;
    }
    
    public Connection getConexion() {
        try {
            String connectionUrl = "jdbc:sqlserver://" + host + ":" + port + ";" +
                    "databaseName=" + database + ";user=" + user + ";password=" + password;
//                    ";integratedSecurity=true;";
//                    ";EncryptionMethod=SSL;cryptoProtocolVersion=TLSv1.2" +
//                    ";encrypt=true;trustServerCertificate=true";
            
            if (con == null) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection(connectionUrl);   
            }
            
            System.out.println("Conexion establecida");
            return con;
        } catch (Exception e) {
            System.out.println("Error en la conexion");
            e.printStackTrace();
            JOptionPane.showMessageDialog(loaderInstancias.getVentana(), modify.paragraphString(e.getMessage()), "Error en la conexion", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public void closeConexion() {
        if (con != null) {
            try {
                con.close();
                con = null;
                System.out.println("Conexion cerrada");
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }
//    jdbc:Informatica:sqlserver://host_name:port_no;SelectMethod=cursor;DatabaseName=database_name;EncryptionMethod=SSL;HostNameInCertificate=DB_host_name;ValidateServerCertificate=true_or_false

    
//    El controlador no puede establecer una conexion segura con sql server con el cifrado de capa de sockets seguros (ssl). Error: "The server selected protocol version TLS10 is not accepted by client preferences [TLS12]". ClientConnectinId:76fd8191-501a-4127-4893-b4159adfc803
}
