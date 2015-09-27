/*
 *               MightyMain 
 *     Clase inicializa y recibe las instrucciones del usuario ejecuta los analisis y retorna los 
 *     outputs. 
 */

import Analysis.LexicalAnalysis;
import Analysis.Parser;
import java.util.ArrayList;

/**
 *
 * @author nicolasjimenez
 */
public class MightyMain {

    /**
     * Ejecuta urSQL
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
