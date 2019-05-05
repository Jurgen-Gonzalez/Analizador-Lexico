
package Pruebas;


/**
 *
 * @author Isass
 */

public class random {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        String zote = "int:num:45";
        System.out.println(ObtenerUltimoNumero(zote));
        
    }
    public static String ObtenerUltimoNumero(String s) {
        
        String aux = "";
        
        for (int k = s.length()-2; k > 0; k--) {
            if (esDígito(s.charAt(k)) || s.charAt(k) == '.') {
                aux += s.charAt(k);
            }else{
                break;
            }
        }
        String auxFinal = "";
        for (int i = aux.length()-1; i >= 0; i--) {
            auxFinal += aux.charAt(i);
        }

        return auxFinal;
    }
    
    public static String ObtenerUltimoValorString(String s) {
        
        String aux = "";
        
        for (int k = s.length()-3; k > 0; k--) {
            if (s.charAt(k) != '"') {
                aux += s.charAt(k);
            }else{
                break;
            }
        }
        String auxFinal = "";
        for (int i = aux.length()-1; i >= 0; i--) {
            auxFinal += aux.charAt(i);
        }

        return auxFinal;
    }
    static boolean esDígito(char c) {
        return c >= '0' && c <= '9';
    }
    static boolean esAlfa(char c) {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }

    /**
     * @param c, si el caracter c es un espacio devuelve true
     * @return
     */
    static boolean esEspacio(char c) {
        return c == ' ' || c == '\t' || c == '\n';
    }
}
