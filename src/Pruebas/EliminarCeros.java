package Pruebas;

/**
 *
 * @author Isass
 */
public class EliminarCeros {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Float n = 3.3f;
        
        String m = "";
        System.out.println(obtenerClase(n.getClass().toString()));
        
    }
    
    public static String obtenerClase(String s){
        
        for (int i = s.length(); i > 0; i--) {
            if (!esAlfa(s.charAt(i-1))) {
                return s.substring(i, s.length());
            }
                
        }
        
        return "";
    }// fin obtenerClase
    
    static boolean esAlfa(char c) {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }
    
    public static String eliminarCerosIzquierda(String s) {

        if (s.equals("0")) {
            return "0";
        }
        for (int i = 0; i < s.length(); i++) {

            if (!(s.charAt(i) == '0')) {
                return s.substring(i, s.length());
            }

        }

        return "no hay algo";
    }// fin eliminar ceros

    public static String eliminarCerosDerecha(String s) {
        s += " ";
        int n = s.indexOf(".");
        for (int i = n + 1; i < s.length(); i++) {

            if (s.charAt(i) == '.') {
                throw new IllegalArgumentException("Error: el número \"" + s + "\" contiene más de un punto");
            }
        }

        if (esDígito(s.charAt(s.indexOf(".") + 1))) {

            for (int i = s.length() - 2; i > 0; i--) {

                if ((s.charAt(i) == '0')) {

                } else {
                    return s.substring(0, i + 1);
                }

            }

        } else { // si lo siguiente de punto no es un número regresa excepción

            throw new IllegalArgumentException("Error: no hay algún dígito después del punto de \"" + s + "\"");
        }

        return "no hay algo";
    }// fin eliminarCerosDerecha

    static boolean esDígito(char c) {
//        .class.toString();
        return c >= '0' && c <= '9';
    }
    
    
    
}
