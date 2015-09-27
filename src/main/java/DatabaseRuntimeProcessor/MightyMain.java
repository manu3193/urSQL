package DatabaseRuntimeProcessor;/*
 *               MightyMain 
 *     Clase inicializa y recibe las instrucciones del usuario ejecuta los analisis y retorna los 
 *     outputs. 
 */

import DatabaseRuntimeProcessor.Analysis.LexicalAnalysis;

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

        LexicalAnalysis lex = new LexicalAnalysis();
        ArrayList<ArrayList<String>> instructionSet = lex.tokenize(null);

        for (ArrayList<String> instruccion : instructionSet) {

            
        }
    }

}
