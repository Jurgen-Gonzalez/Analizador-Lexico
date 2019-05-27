
package Pruebas;

import static modelo.Tokenizer.tablaDeSimbolos;

/**
 *
 * @author Isass
 */

public class test_doubles {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        System.out.println(contieneCaracteresValidos("ñ"));

    }
    
    private static boolean contieneCaracteresValidos(String s){
        
        boolean loContiene;
        
        for (int i = 0; i < tablaDeSimbolos.length; i++) {
                
                for (int j = 0; j < s.length(); j++) {
                    
                    if (tablaDeSimbolos[i] == s.charAt(j)) {
                        loContiene = true;
                        return true;
                    }
                    
                    
                }
            
        }
        
        return false;
        
    }
    

}
