package modelo;

import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author Isass
 */
public class Tokenizer_1 {

    public ArrayList<String> arrayIdentificador;
    public ArrayList<String> arrayNumero;
    public ArrayList<String> arrayNumeroFlotante;
    public ArrayList<String> arraySimbolo;
    public ArrayList<String> arrayPalabraReservada;

    String[] palabrasReservadas = new String[]{"return", "final", "static", "void", "int", "long", "public", "private", "protected", "new", "String",
        "boolean", "true", "false", "while", "for", "do", "switch", "case", "if", "else", "break", "class", "package", "import", "throws", "null", "float"};

    String[] simbolos = new String[]{"=", ".", ":", ";", "(", ")", "{", "}", "[", "]", "-", "_", "+", "-", "*", "/"};

    /**
     * Constructor
     */
    public Tokenizer_1() {
        arrayIdentificador = new ArrayList<>();
        arrayNumero = new ArrayList<>();
        arrayNumeroFlotante = new ArrayList<>();
        arraySimbolo = new ArrayList<>();
        arrayPalabraReservada = new ArrayList<>();

    }

    /**
     * @param zote, variable que contiene el contenido del archivo. El switch
     * representa un autómata, en el cuál sus estados se representan como casos.
     * La variable i será quien recorrerá todo el String, siempre incrementando
     * de uno en uno. La variable estado nos servirá para cambiarnos de estado
     * dentro del autómata. La variable lexema nos servirá para concatenar los
     * caracteres que están seguidos, excepto por espacios. Algunos case: nos
     * sirven como transiciones, y otros como estados. Dentro del switch hay
     * arraylist que usaremos para rellenar los JTextArea de la pantalla
     * principal.
     */
    public void tokenizer(String zote) {
        zote += " ";
        String lexema;
        int i = 0, j = 0, estado = 0;
        lexema = "";
        boolean flotante = true;

        while (i < zote.length()) {
            switch (estado) {

                case 0:
                    if (esAlfa(zote.charAt(i))) {
                        estado = 1;
                    }else if(esDígito(zote.charAt(i))){
                        estado = 2;
                    }else if (esEspacio(zote.charAt(i))) {
                        estado = 99;
                    }else{
                        //identoficador
                    }
                    break;
                case 1://listo
                    if (esAlfa(zote.charAt(i))) {
                        lexema += zote.charAt(i);
                        estado = 0;
                        if (zote.charAt(i + 1) == ';' || zote.charAt(i +1) == ' ') {
                            if (esReservada(lexema)) {
                                arrayPalabraReservada.add(lexema);
                            } else {
                                arrayIdentificador.add(lexema);
                            }

                            lexema = "";
                            estado = 0;
                            

                        }
                        i++;
                    }else{
                        estado = 0;
                    }
                    break;
                case 2:
                    if (esDígito(zote.charAt(i))) {
                        lexema += zote.charAt(i);
                        estado = 2;
                        if (zote.charAt(i + 1) == ';' || zote.charAt(i +1) == ' ') {
                            arrayNumero.add(lexema);
                            lexema = "";
                            estado = 0;
                        }
                        i++;
                        if (zote.charAt(i) == '.') {
                            lexema += zote.charAt(i);
                            estado = 3;
                            i++;
                        }
                    }else{
                        
                    }
                    
                    
                    break;
                case 3:
                    lexema += zote.charAt(i);
                    arrayNumeroFlotante.add(lexema);
                    if (esDígito(zote.charAt(i))) {
                        lexema += zote.charAt(i);
                        estado = 2;
                        i++;
                    }else{
                        i++;
                    estado = 0;
                    }
                    
                    break;
                case 99:
                    if (esEspacio(zote.charAt(i))) {
                            i++;
                            estado = 0;
                        }
                    break;

            }
        }

    }

    /**
     * @param c, si el caracter c se encuentra entre a-z y A-Z, significa que es
     * letra, devuelve true
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
     * @param c, si el caracter c se encuentra entre 0-9, significa que es
     * digito, devuelve true
     * @return
     */
    boolean esDígito(char c) {
        return c >= '0' && c <= '9';
    }

    boolean esReservada(String c) {
        for (String palabrasReservada : palabrasReservadas) {
            if (palabrasReservada.equals(c)) {
                return true;
            }
        }
        return false;
    }

    boolean esSimbolo(String c) {
        for (String sim : simbolos) {
            if (sim.equals(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param a, arraylist que se fue llenando mientras se analizaba el
     * StringZote
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
