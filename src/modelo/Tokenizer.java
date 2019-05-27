package modelo;

import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import vista.Vista;
import java.util.Arrays;

/**
 *
 * @author Isass
 */
public class Tokenizer {

    public String[] arrayIdentificador;
    public int[] arrayNumero;
    public double[] arrayNumeroFlotante;
    public String[] arraySimbolo;
    public String[] arrayPalabraReservada;

    public String[] lexemas;
    public String[] tiposLexemas;

    public String[] tipoIdentificador;
    public String[] nombreIdentificador;
    public String[] valorIdentificador;
    public String[] atributosDePalabra;
    
//    public ArrayList<String> arrayAtributosDePalabras;
//    private String [] recuerdaLexema;

    String recuerdo = "";
    String atributosDePalabras = "";

    public enum Lexemas {
        RESERVADA("Palabra Reservada"),
        IDENTIFICADOR("Identificador"),
        NUMERO("Numero"),
        NUMERO_DECIMAL("Numero Decimal"),
        SIMBOLO("Caracter");

        private final String tipo;

        private Lexemas(String tipo) {
            this.tipo = tipo;
        }

    }

    Vista vista;
    public static final String[] palabrasReservadas = new String[]{"return", "final",
        "static", "void", "int", "long", "public", "private", "protected", "new",
        "boolean", "true", "false", "while", "for", "do", "switch", "case", "if",
        "else", "break", "class", "package", "import", "throws", "null", "float",
        "byte", "short", "double", "String", "FileReader", "BufferedReader"};

    public static final String[] tiposDeDatos = new String[]{"int", "double",
        "String", "long", "short", "class", "boolean", "char", "byte"};
    
    public static final char[] tablaDeSimbolos = new char[]{'+','-','*','/','.','(',')','[',']','{','}',';','\'','=', 
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'U', 'V', 'X', 'Y', 'Z'
        , '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * Constructor
     *
     * @param vista
     */
    public Tokenizer(Vista vista) {

        this.vista = vista;
        arrayIdentificador = new String[0];
        arrayNumero = new int[0];
        arrayNumeroFlotante = new double[0];
        arraySimbolo = new String[0];
        arrayPalabraReservada = new String[0];

        //Tabla Lexemas
        lexemas = new String[0];
        tiposLexemas = new String[0];

        //tabla Identificador
        tipoIdentificador = new String[0];
        nombreIdentificador = new String[0];
        valorIdentificador = new String[0];

        atributosDePalabra = new String[0];
//        recuerdaLexema = new String[4];
//        arrayAtributosDePalabras = new ArrayList<>();

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

        outer: while (i < zote.length()) {
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

                case 2: // - Decidir si es reservada o un identificador

                    if (esReservada(lexema)) {
                        agregarALista(lexema, Lexemas.RESERVADA);

                    } else {
                        if(isInArray(lexema, arrayIdentificador))
                            agregarALista(lexema, Lexemas.IDENTIFICADOR);
                    }
                    boolean contieneElIdentificador = false;
                    String aux1;
                    switch (recuerdo) {
                        case "class":
                            atributosDePalabras = recuerdo + ":" + lexema + ":" + zote.substring(i);
                            System.out.println(atributosDePalabras);
                            break;
                        case "int":
                        case "short":
                        case "byte":
                        case "long":
                        case "double":
                        case "String":
                            String subS;
                            subS = zote.substring(zote.indexOf(recuerdo), zote.indexOf(";") + 1);

                            if (!subS.contains("=")) { // si no está inicializada se iniciliza en su valor respectivo
                                switch (recuerdo) {

                                    case "double":
                                        atributosDePalabras = recuerdo + ":" + lexema + ":" + "0.0";
                                        break;
                                    case "String":
                                        atributosDePalabras = recuerdo + ":" + lexema + ":" + "\"\"";
                                        break;
                                    default:
                                        atributosDePalabras = recuerdo + ":" + lexema + ":" + "0";
                                        break;
                                }

                            } else {
                                if (recuerdo.equals("String")) {
                                    atributosDePalabras = recuerdo + ":" + lexema + ":" + ObtenerUltimoValorString(subS);
                                }else{
                                    atributosDePalabras = recuerdo + ":" + lexema + ":" + ObtenerUltimoNumero(subS);
                                }

                            }
                            i = zote.indexOf(";");
                            zote = zote.substring(0, zote.indexOf(";")) + " " + zote.substring(i + 1);
                            System.out.println(atributosDePalabras);

                            break; // fin tipos de Datos

                        default:
                            break;
                    }// fin switch

                    recuerdo = lexema;
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
                        lexema += zote.charAt(i);
                        i++;

                    } else if (zote.charAt(i) == '.') {
                        lexema += zote.charAt(i);

                        i++;
                        estado = 6;
                    } else {
                        estado = 5;
                    }
                    break;

                case 5:
                    agregarALista(lexema, Lexemas.NUMERO);

                    recuerdo = lexema;
                    lexema = "";
                    estado = 0;
                    break;
                case 6: // Solo admite numeros (esto se aplica despues del punto decimal)
                    if (esDígito(zote.charAt(i))) {
                        lexema += zote.charAt(i);
                        i++;
                    } else {
                        estado = 9;
                    }
                    break;
                case 9:
                    agregarALista(lexema, Lexemas.NUMERO_DECIMAL);

                    recuerdo = lexema;
                    lexema = "";
                    estado = 0;
                    break;
                case 50: // - Caso para el manejo de errores
                    break;
                default:
                    if (esEspacio(zote.charAt(i))) {

                    } else {
                        if(!isInArray(zote.charAt(i), tablaDeSimbolos)){
                            System.out.println("Error en el caracter "+zote.charAt(i));
                            break outer;
                        }
                        agregarALista(String.valueOf(zote.charAt(i)), Lexemas.SIMBOLO);
                    }

                    i++;
                    estado = 0;

            }
        }
//        System.out.println("Datos del ArrayList");
//        for (int j = 0; j < arrayAtributosDePalabras.size(); j++) {
//            System.out.println(arrayAtributosDePalabras.get(j));
//        }
        vista.tablaLexemas.setModel(new DefaultTableModel(tablaLexemas(), new String[]{"Tipo", "Lexema"}));
        vista.tabla.setModel(new DefaultTableModel(tablaIdentificadores(), new String[]{"Tipo", "Nombre", "Valor"}));

    }

    private void agregarALista(String lexema, Lexemas tipo) {
        switch (tipo) {
            case IDENTIFICADOR:
                arrayIdentificador = Arrays.copyOf(arrayIdentificador, arrayIdentificador.length + 1);
                arrayIdentificador[arrayIdentificador.length - 1] = lexema;
                break;
            case NUMERO:
                arrayNumero = Arrays.copyOf(arrayNumero, arrayNumero.length + 1);
                arrayNumero[arrayNumero.length - 1] = Integer.parseInt(lexema);
                break;
            case NUMERO_DECIMAL:
                arrayNumeroFlotante = Arrays.copyOf(arrayNumeroFlotante, arrayNumeroFlotante.length + 1);
                arrayNumeroFlotante[arrayNumeroFlotante.length - 1] = Double.parseDouble(lexema);
                break;
            case RESERVADA:
                arrayPalabraReservada = Arrays.copyOf(arrayPalabraReservada, arrayPalabraReservada.length + 1);
                arrayPalabraReservada[arrayPalabraReservada.length - 1] = lexema;
                break;
            case SIMBOLO:
                arraySimbolo = Arrays.copyOf(arraySimbolo, arraySimbolo.length + 1);
                arraySimbolo[arraySimbolo.length - 1] = lexema;
        }

        lexemas = Arrays.copyOf(lexemas, lexemas.length + 1);
        lexemas[lexemas.length - 1] = lexema;
        tiposLexemas = Arrays.copyOf(tiposLexemas, tiposLexemas.length + 1);
        tiposLexemas[tiposLexemas.length - 1] = tipo.tipo;
    }

    private void agregarALista(String a, String b, String[] array1, String[] array2) { // Para tabla de Identificadores
        String[] arr1 = Arrays.copyOf(array1, array1.length + 1);
        arr1[arr1.length - 1] = a;

        String[] arr2 = Arrays.copyOf(array2, array2.length + 1);
        arr2[arr2.length - 1] = b;

        array1 = arr1;
        array2 = arr2;
    }

    private String[][] tablaLexemas() {
        String[][] tablaLexemas = new String[lexemas.length][2];
        for (int i = 0; i < lexemas.length; i++) {
            tablaLexemas[i][0] = tiposLexemas[i];
            tablaLexemas[i][1] = lexemas[i];
        }
        return tablaLexemas;
    }

    private String[][] tablaIdentificadores() {
        String[][] tablaIdentificadores = new String[nombreIdentificador.length][3];
        for (int i = 0; i < nombreIdentificador.length; i++) {
            tablaIdentificadores[i][0] = tipoIdentificador[i];
            tablaIdentificadores[i][1] = nombreIdentificador[i];
            tablaIdentificadores[i][2] = valorIdentificador[i];
        }
        return tablaIdentificadores;
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

    /**
     * @param a, arraylist que se fue llenando mientras se analizaba el
     * StringZote
     * @param txt, JTextArea que se llenará y que mostrará en pantalla
     */
    public void rellenarTextField(String[] a, JTextArea txt) {
        String aux = "";
        for (String a1 : a) {
            aux += a1 + "    ";
        }
        txt.setText(aux);
    }

    public void rellenarTextFieldNumerico(int[] a, JTextArea txt) {
        String aux = "";
        for (int i = 0; i < a.length; i++) {
            aux += a[i] + "    ";
        }
        txt.setText(aux);
    }

    public void rellenarTextFieldDecimal(double[] a, JTextArea txt) {
        String aux = "";
        for (int i = 0; i < a.length; i++) {
            aux += a[i] + "    ";
        }
        txt.setText(aux);
    }

    public String ObtenerResultado(String s) {
        int i = s.indexOf("=") + 1;
        int j = s.indexOf(";");
        String aux = "";
        for (int k = i; k < j; k++) {
            if (!(s.charAt(k) == ' ')) {
                aux += s.charAt(k);
            }

        }

        return aux;
    }

    public String ObtenerIdentificador(String s) {
        int indice = s.indexOf(":");
        String aux = "";
        for (int k = indice + 1; k < s.length(); k++) {
            if (s.charAt(k) == ':') {
                return aux;
            }
            aux += s.charAt(k);

        }

        return aux;
    }
    public String ObtenerTipo(String s) {
        String aux = "";
        for (int k = 0; k < s.length(); k++) {
            if (s.charAt(k) == ':') {
                return aux;
            }
            aux += s.charAt(k);

        }

        return aux;
    }
    public String ObtenerUltimoValorString(String s) {
        
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
    public String ObtenerUltimoNumero(String s) {
        
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
    
    private boolean isInArray(char word, char [] array){
        for (int i = 0; i < array.length; i++) {
            if(word == array[i])
                return true;
        }
        return false;
    }
    
    private boolean isInArray(String word, String [] array){
        for (int i = 0; i < array.length; i++) {
            if(word.equals(array[i]))
                return true;
        }
        return false;
    }
    
}
