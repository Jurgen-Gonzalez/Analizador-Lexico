package modelo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import vista.Vista;

/**
 *
 * @author Isass
 */
public class Tokenizer {

    public ArrayList<String> arrayIdentificador;
    public ArrayList<Integer> arrayNumero;
    public ArrayList<String> arrayNumeroFlotante;
    public ArrayList<String> arraySimbolo;
    public ArrayList<String> arrayPalabraReservada;

    Vista vista;

    String[] palabrasReservadas = new String[]{"return", "final", "static", "void", "int", "long", "public", "private", "protected", "new", "String",
        "boolean", "true", "false", "while", "for", "do", "switch", "case", "if", "else", "break", "class", "package", "import", "throws", "null", "float"};

    /**
     * Constructor
     *
     * @param vista
     */
    public Tokenizer(Vista vista) {
        this.vista = vista;
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
                    if (esReservada(lexema)) {
                        estado = 6;
                    } else {
                        arrayIdentificador.add(lexema);

                        lexema = "";
                        estado = 0;
                    }
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
                        if (zote.charAt(i + 1) == '.') {
                            estado = 7;
                        }
                        lexema += zote.charAt(i);
                        i++;

                    } else {
                        if (lexema.contains(".")) {
                            estado = 9;
                        } else {
                            estado = 5;
                        }
                    }
                    break;

                case 5:
                    arrayNumero.add(Integer.valueOf(lexema));
                    DefaultTableModel modelo = new DefaultTableModel();
                    vista.tabla.setModel(modelo);
                    
                    rellenarTabla(arrayNumero.get(0).getClass(), arrayIdentificador.get(0) , String.valueOf(arrayNumero.get(0)));
                    
                    lexema = "";
                    estado = 0;
                    break;
                case 6: // es palabra reservada
                    if (esReservada(lexema)) {
                        arrayPalabraReservada.add(lexema);
                        lexema = "";
                        estado = 0;
                        // i++;
                    }

                    break;
                case 7: // es flotante
                    if (zote.charAt(i) == '.') {

                        estado = 8;
                    } else {
                        estado = 0;
                    }
                    break;
                case 8:

                    if (zote.charAt(i) == '.') {
                        lexema += zote.charAt(i);
                    } else {
                        lexema += zote.charAt(i);
                    }

                    estado = 0;
                    break;
                case 9:
                    arrayNumeroFlotante.add(lexema);
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

    boolean esFlotante(String n) {

        return n.contains(".");
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
    public void rellenarTextFieldNumerico(ArrayList<Integer> a, JTextArea txt) {
        String aux = "";
        for (int i = 0; i < a.size(); i++) {
            aux += a.get(i) + "    ";
        }
        txt.setText(aux);
    }
    public void rellenarTabla(Class a, String b, String c){
        vista.tabla.setModel(new javax.swing.table.DefaultTableModel(
                            new Object[][]{
                                {a,b,c}
                            },
                            new String[]{
                                "Tipo", "Nombre", "Valor"
                            }
                    ) {
                        Class[] types = new Class[]{
                            java.lang.String.class, java.lang.String.class, java.lang.String.class
                        };
                        boolean[] canEdit = new boolean[]{
                            false, false, false
                        };

                        @Override
                        public Class getColumnClass(int columnIndex) {
                            return types[columnIndex];
                        }

                        @Override
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return canEdit[columnIndex];
                        }
                    });
    }
}
