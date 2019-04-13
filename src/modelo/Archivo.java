package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Archivo {
    
    /**
     * Constructor
     */
    public Archivo(){
        
    }
    
    /** El método lee y devuelve el contenido de un txt
     * @param rutaArchivo, se pasa como parámetro la ruta del archivo, obtenido por el JFileChooser
     * @return , devuelve el contenido del archivo txt seleccionado
     * @throws java.io.FileNotFoundException, en caso de ocurrir alguna excepcion en la lectura las lanza, y donde se llama a el método se capturan
     */
    public String muestraContenido(String rutaArchivo) throws FileNotFoundException, IOException {
        
        String cadena;
        String zote = "";
        FileReader f = new FileReader(rutaArchivo);
        BufferedReader b = new BufferedReader(f);
        
        while ((cadena = b.readLine()) != null) {
            zote += cadena;
        }        
        b.close();
        return zote;
    }

}
