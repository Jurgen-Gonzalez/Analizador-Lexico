package modelo;

import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author Isass
 */
public class Tokenizer {

    public ArrayList<String> arrayIdentificador;
    public ArrayList<String> arrayNumero;
    public ArrayList<String> arraySimbolo;
    public ArrayList<String> arrayPalabraReservada;
    
    /**
     * Constructor
     */
    public Tokenizer() {

        arrayIdentificador = new ArrayList<>();
        arrayNumero = new ArrayList<>();
        arraySimbolo = new ArrayList<>();
        arrayPalabraReservada = new ArrayList<>();

    }
    /**
     * @param zote, variable que contiene el contenido del archivo.
     * El switch representa un autómata, en el cuál sus estados se representan como casos.
     * La variable i será quien recorrerá todo el String, siempre incrementando de uno en uno.
     * La variable estado nos servirá para cambiarnos de estado dentro del autómata.
     * La variable lexema nos servirá para concatenar los caracteres que están seguidos, excepto por espacios.
     * Algunos case: nos sirven como transiciones, y otros como estados.
     * Dentro del switch hay arraylist que usaremos para rellenar los JTextArea de la pantalla principal.
     */
    public void tokenizer(String zote) {
        String lexema;
        int i = 0, estado = 0;
        lexema = "";
        
        while (i < zote.length()) {
            switch (estado) {

                case 0:
                    if (esAlfa(zote.charAt(i))) {
                        estado = 1;
                    } else {
                        estado = 3;
                    }
                    break;

                case 1:
                    if (esAlfa(zote.charAt(i)) || esDígito(zote.charAt(i))) {

                        lexema += zote.charAt(i);
                        i++;
                        
                    } else {
                        estado = 2;
                    }
                    break;

                case 2:
                    arrayIdentificador.add(lexema);
                    lexema = "";
                    estado = 0;
                    break;

                case 3:
                    if (esDígito(zote.charAt(i))) {
                        estado = 4;
                    } else {
                        estado = 100;
                    }
                    break;
                case 4:
                    if (esDígito(zote.charAt(i))) {
                        lexema = lexema + zote.charAt(i);
                        i++;
                    } else {
                        estado = 5;
                    }
                    break;

                case 5:
                    arrayNumero.add(lexema);
                    lexema = "";
                    estado = 0;
                    break;

                default:
                    if (esEspacio(zote.charAt(i))) {
                        i++;
                    } else {
                        arraySimbolo.add(String.valueOf(zote.charAt(i++)));
                    }
                    estado = 0;
            }
        }

    }
    /**
     * @param c, si el caracter c se encuentra entre a-z y A-Z, significa que es letra, devuelve true
     * @return 
     */
    boolean esAlfa(char c) {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }
    /**
     * @param c, si el caracter c es un espacio devuelve true
     * @return 
     */
    boolean esEspacio(char c) {
        return c == ' ' || c == '\t' || c == '\n';
    }
    /**
     * @param c, si el caracter c se encuentra entre 0-9, significa que es digito, devuelve true
     * @return 
     */
    boolean esDígito(char c) {
        return c >= '0' && c <= '9';
    }
    /**
     * @param a, arraylist que se fue llenando mientras se analizaba el StringZote
     * @param txt, JTextArea que se llenará y que mostrará en pantalla
     */
    public void rellenarTextField(ArrayList<String> a, JTextArea txt) {
        String aux = "";
        for (int i = 0; i < a.size(); i++) {
            aux += a.get(i) + "    ";
        }
        txt.setText(aux);
    }
}
