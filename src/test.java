
import controlador.Controlador;
import modelo.Archivo;
import modelo.Tokenizer;
import vista.Vista;

public class test {

    /**
     * @param MeEstoyQuemando
     */
    public static void main(String[] MeEstoyQuemando) {
        //Instancia de pantalla principal
        Vista vista = new Vista();
        
        //Intancia de Archivo2
        Archivo arch = new Archivo();
        
        //Instancia de Tokenizer
        Tokenizer tokenizer = new Tokenizer(vista);
        
        /**
         * Se mandan los objetos a la clase controlador para poder trabajar con estos.
         * @param vista, 
         * @param arch, 
         * @param tokenizer, 
         */
        Controlador con = new Controlador(vista, arch, tokenizer);
        con.iniciar();
        
    }

}
