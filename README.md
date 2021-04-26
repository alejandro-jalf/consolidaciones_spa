# Consolidaciones SPA

Aplicacion para verificar las consolidaciones realizadas en Super Promociones de Acayucan

La aplicacion es desarrollada en el lenguaje de java, utilizando la version 8 de java, e implementando las siguientes tecnologias y recursos.

- Maven (Como administrador de librerias)
- JDK v1.8.0_191-b12
- JRE v1.8.0_191-b12
- sqljdbc4 v4.2.6225.100
- json v20201115
- jcalendar v1.4

## Consideraciones

Para poder correr la aplicacion se requiere de un archivo de configuracion el cual por motivos de seguridad de las claves privadas no fue subido a este repositorio, sin embargo debe tener la siguiente estructura.

````java
package configs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;
import utils.LoaderInstancias;
import utils.ReadFileJSon;

public class config {
    
    private ReadFileJSon readJSon = null;
    private JSONObject dataConfig = null;
    private String hostLocal = "";
    private String databaseLocal = "";
    private JFrame ventana = null;
    private int response = 0;
    private LoaderInstancias loaderInstancias = null;
    private String hostName = "Name_host";
    private String databaseName = "Name_database";
    
    private final String hostSuper1 = "";
    private final String hostVictoria = "";
    private final String hostOluta = "";
    private final String hostJaltipan = "";
    private final String hostBodega = "";
    
    private final String databaseSuper1 = "";
    private final String databaseVictoria = "";
    private final String databaseOluta = "";
    private final String databaseJaltipan = "";
    private final String databaseBodega = "";
    
    private final String port = "";
    private final String user = "";
    private final String password = "";
    private final String configInitial = "{\n" +
                "\"Version\": \"1.0 SNAP-SHOP\",\n" +
                "\"Author\": \"Jose Alejandro Lopez Flores\",\n" +
                "\"Config\": {\n" +
                "    \"host\": \"ip_addres\",\n" +
                "    \"database\": \"database\"  \n" +
                "  }\n" +
                "}";
    
    public config(LoaderInstancias loaderInstancias) {
        this.loaderInstancias = loaderInstancias;
        this.ventana = loaderInstancias.getVentana();
        this.readJSon = new ReadFileJSon("config.json", this);
        
        configureHostAnDatabase();
    }
    
    private void configureHostAnDatabase() {
        try {
            dataConfig = readJSon.getHostAndDatabase();
            if (dataConfig.has("error")) {
                hostLocal = "host_local";
                databaseLocal = "database_local";
                response = JOptionPane.showConfirmDialog(ventana, "¿Quiere restaurar el archivo de configuracion?", dataConfig.getString("error"), JOptionPane.ERROR_MESSAGE);
                if (response == JOptionPane.OK_OPTION) {
                    readJSon.createFile();
                    JOptionPane.showMessageDialog(ventana, "Se necesita cerrar la aplicacion y volver a ejecutarse para completar el proceso", "Cierrre necesario", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                } else {
                    System.exit(0);
                }
            } else {
                hostLocal = dataConfig.getJSONObject("Config").getString("host");
                databaseLocal = dataConfig.getJSONObject("Config").getString("database");
                
                Pattern pat = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");
                Matcher mat = pat.matcher(hostLocal);
                if (mat.matches()) {
                    hostName = "LocalHost";
                } else {
                    String arrayHost[] = hostLocal.split("\\.");
                    hostName = arrayHost[0];
                }
                databaseName = databaseLocal;
            }
        }
        catch (JSONException e) {
            hostLocal = "host_local";
            databaseLocal = "database_local";
            System.out.println("Error al parsear json: " + e);
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("Error en la configuracion: "+ e);
            e.printStackTrace();
        }
    }
    
    public String getHostLocal() { return hostLocal; }
    public String getHostSuper1() { return hostSuper1; }
    public String getHostVictoria() { return hostVictoria; }
    public String getHostOluta() { return hostOluta; }
    public String getHostJaltipan() { return hostJaltipan; }
    public String getHostBodega() { return hostBodega; }
    public String getHostName() { return hostName; }
    
    public String getDatabaseLocal() { return databaseLocal; }
    public String getDatabaseSuper1() { return databaseSuper1; }
    public String getDatabaseVictoria() { return databaseVictoria; }
    public String getDatabaseOluta() { return databaseOluta; }
    public String getDatabaseJaltipan() { return databaseJaltipan; }
    public String getDatabaseBodega() { return databaseBodega; }
    public String getDatabaseName() { return databaseName; }
    
    public String getPort() { return port; }
    public String getUser() { return user; }
    public String getPassword() { return password; }
    public String getConfigInitial() { return configInitial; }
    
}

````


