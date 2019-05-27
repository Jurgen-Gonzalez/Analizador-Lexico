package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
//import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.Archivo;
import modelo.Tokenizer;
import vista.Vista;

public class Controlador implements ActionListener {

    Vista vista;
    Archivo arch;
    Tokenizer tokeni;
    
    JFileChooser chooserFile;   //  necesario para obtener el archivo a escanear
    String zote;
    /**
     * @param vista, parámetro pasado desde el main
     * @param arch, parámetro pasado desde el main
     * @param tokeni, parámetro pasado desde el main
     */
    public Controlador(Vista vista, Archivo arch, Tokenizer tokeni) {
        this.vista = vista;
        this.arch = arch;
        this.tokeni  = tokeni;
        chooserFile = new JFileChooser();

       
        //EVENTOS
        this.vista.setResizable(false);
        this.vista.btnEscanear.addActionListener(this);
        
        this.vista.btnSeleccionarArchivo.addActionListener((ae) -> {
            chooserFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

            FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Archivos txt", "txt", "text");
            chooserFile.setFileFilter(txtFilter);

            int result = chooserFile.showOpenDialog(vista);
            if (result != JFileChooser.CANCEL_OPTION) {

                File fileName = chooserFile.getSelectedFile();

                if ((fileName == null) || (fileName.getName().equals(""))) {
                } else {
                    String rutaTxt = chooserFile.getSelectedFile().getAbsoluteFile().getAbsolutePath();
                    try {
                        zote = this.arch.muestraContenido(rutaTxt)+ " ";
                    } catch (IOException ex) {
                    }
                    this.vista.btnEscanear.setEnabled(true);
                }
            }
        });

    }

    public void iniciar() {
        this.vista.setVisible(true);
        this.vista.setDefaultCloseOperation(3);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object o = ae.getSource();
        
        if (o == this.vista.btnEscanear) {                     // BOTÓN ESCANEAR
            
            //inicio borrar varialbles
            this.tokeni.arrayIdentificador = new String[0];
            this.tokeni.arrayNumero= new int[0];
            this.tokeni.arrayNumeroFlotante= new double[0];
            this.tokeni.arrayPalabraReservada= new String[0];
            this.tokeni.arraySimbolo= new String[0];
            //fin borrar varialbles
            
            this.tokeni.tokenizer(zote);
            // ArrayList auxiliares para llenar los JTextArea
            String [] auxArrayIdentificador = this.tokeni.arrayIdentificador;
            int [] auxArrayNumero = this.tokeni.arrayNumero;
            double [] auxArrayNumeroFlotante = this.tokeni.arrayNumeroFlotante;
            String [] auxArraySimbolo = this.tokeni.arraySimbolo;
            String [] auxArrayPalabraReservada = this.tokeni.arrayPalabraReservada;
            
            // Se rellenan los JTextArea
            this.tokeni.rellenarTextField(auxArrayIdentificador, this.vista.jTextAreaIdentificador);                 
            this.tokeni.rellenarTextFieldNumerico(auxArrayNumero, this.vista.jTextAreaNumero);
            this.tokeni.rellenarTextFieldDecimal(auxArrayNumeroFlotante, this.vista.jTextAreaNumeroFlotante);
            this.tokeni.rellenarTextField(auxArraySimbolo, this.vista.jTextAreaSimbolo);
            this.tokeni.rellenarTextField(auxArrayPalabraReservada, this.vista.jTextAreaReservada);

            // Da color a el texto
            this.vista.jTextAreaIdentificador.setForeground(new Color(0, 150, 20));
            this.vista.jTextAreaSimbolo.setForeground(new Color(255, 150, 50));
            this.vista.jTextAreaNumero.setForeground(new Color(100, 100, 90));
            this.vista.jTextAreaNumeroFlotante.setForeground(new Color(100, 100, 90));
            this.vista.jTextAreaReservada.setForeground(new Color(20,20,250));
            
//            JOptionPane.showMessageDialog(vista, "Escaneo Completado");
            this.vista.btnEscanear.setEnabled(false);
            
            
            
            
        }                                                   // fin BOTÓN ESCANEAR                
        
    }

}
