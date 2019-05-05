
package Pruebas;

import static Pruebas.EliminarCeros.esAlfa;

/**
 *
 * @author Isass
 */

public class ObtenerPartes {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        System.out.println(ObtenerTipo("Class.Tamal.Queso"));
        System.out.println(ObtenerIdentificador("Class.Tamal.Queso"));
        System.out.println(obtenerValor("Class.Tamal.Queso"));

    }
    
    public static String ObtenerTipo(String s){
        String aux = "";
        
        for (int i = 0; i < s.length(); i++) {
            
            aux += s.charAt(i);
            if (s.charAt(i+1) == '.') {
                return aux;
            }
        }
        
        return "Error Tipo";
    }
    
    public static String ObtenerIdentificador(String s){
        String aux = "";
        
        int indicePunto = s.indexOf('.');
        
        for (int i = indicePunto +1; i < s.length(); i++) {
            aux += s.charAt(i);
            if (s.charAt(i+1) == '.') {
                return aux;
            }
        }
        
        return "Error Identificador";
    }
    
    public static String obtenerValor(String s){
        
        for (int i = s.length(); i > 0; i--) {
            if (!esAlfa(s.charAt(i-1))) {
                return s.substring(i, s.length());
            }
                
        }
        
        return "Error Valor";
    }// fin obtenerClase
    
    static boolean esDígito(char c) {
        return c >= '0' && c <= '9';
    }

}
