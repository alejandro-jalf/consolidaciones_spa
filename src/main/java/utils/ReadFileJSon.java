/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import configs.config;

/**
 *
 * @author Jose Alejandro Lopez
 */
public class ReadFileJSon {
    private FileReader fileReader = null;
    private BufferedReader buffer = null;
    private String contenido = "";
    private String line = "";
    private String source = "";
    private JSONObject object = null;
    private config conf= null;
    
    
    public ReadFileJSon(String source, config conf) {
        this.source = source;
        this.conf = conf;
    }
    
    public JSONObject getHostAndDatabase() {
        object = null;
        try {
            contenido = "";
            fileReader = new FileReader(source);
            buffer = new BufferedReader(fileReader);
            while ((line = buffer.readLine()) != null) {
                contenido += line;
            }
            buffer.close();
            object = new JSONObject(contenido);
            if (
                !object.has("Version") ||
                !object.has("Author") ||
                !object.has("Config")
            ) {
                object = new JSONObject(
                    "{" +
                    "\"error\": \"Fichero de configuracion alterado\"" +
                    "}"
                );
            }
            else if (!object.getString("Author").equals("Jose Alejandro Lopez Flores")) {
                object = new JSONObject(
                    "{" +
                    "\"error\": \"Fichero de configuracion alterado\"" +
                    "}"
                );
            }
            else if (
                !object.getJSONObject("Config").has("host") ||
                !object.getJSONObject("Config").has("database")
            ) {
                object = new JSONObject(
                    "{" +
                    "\"error\": \"Fichero de configuracion dañado\"" +
                    "}"
                );
            }
        }
        catch (FileNotFoundException e) {
            object = new JSONObject(
                "{" +
                "\"error\": \"Fichero de configuracion no se encuentra\"" +
                "}"
            );
            System.out.println("Archivo no localizado: " + e);
            e.printStackTrace();
            return object;
        }
        catch (IOException e) {
            System.out.println("Excepcion de IO: " + e);
            e.printStackTrace();
        }
        catch (JSONException e) {
            object = new JSONObject(
                "{" +
                "\"error\": \"Fichero de configuracion dañado\"" +
                "}"
            );
            System.out.println("Error en el constructor");
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("Error en el constructor");
            e.printStackTrace();
        }
        return object;
    }
    
    public void createFile() {
        try {
            String contenido = conf.getConfigInitial();
            File file = new File(source);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
