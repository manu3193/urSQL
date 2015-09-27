package DatabaseRuntimeProcessor;/*
 *               MightyMain 
 *     Clase inicializa y recibe las instrucciones del usuario ejecuta los analisis y retorna los 
 *     outputs. 
 */

import DatabaseRuntimeProcessor.Analysis.LexicalAnalysis;
import GUI.GUI;

import java.util.ArrayList;

/**
 *
 * @author nicolasjimenez
 */
public class MightyMain {

    /**
     * Ejecuta main
     *
     * @param args
     */
    public static void main(String[] args) {

        GUI guiInstance = new GUI();
        
        
        
        LexicalAnalysis lex = new LexicalAnalysis();
        ArrayList<String> instructionSet = lex.tokenize(null);

        for (String instruccion : instructionSet) {

            
        }
    }

}
