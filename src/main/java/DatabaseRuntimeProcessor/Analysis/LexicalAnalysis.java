/*
 * Lexical DatabaseRuntimeProcessor.Analysis
 * Analiza todo un conjunto de instruciones  
 * 
 */
package DatabaseRuntimeProcessor.Analysis;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Nicolas Jimenez
 */
public class LexicalAnalysis {

    private final ArrayList<String> instructions;

    public LexicalAnalysis() {

        this.instructions = new ArrayList<String> ();
    }

    /**
     *  Instruccion que tokeniza las instrucciones por espacios y separa instruccion por instruccion por ";" 
     * @param instruction 
     * @return  
     */
    public ArrayList<String> tokenize(String instruction) {

        StringTokenizer tokens = new StringTokenizer(instruction);

        while (tokens.hasMoreElements()) {

            String elementoActual = tokens.nextToken();

                instructions.add( elementoActual);
        }
        return instructions;
    }


    
}
