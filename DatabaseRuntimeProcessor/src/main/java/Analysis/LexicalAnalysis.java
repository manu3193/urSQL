/*
 * Lexical Analysis
 * Analiza todo un conjunto de instruciones  
 * 
 */
package Analysis;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Nicolas Jimenez
 */
public class LexicalAnalysis {

    private final ArrayList<ArrayList<String>> instructions;

    public LexicalAnalysis() {

        this.instructions = new ArrayList<>();
    }

    /**
     *  Instruccion que tokeniza las instrucciones por espacios y separa instruccion por instruccion por ";" 
     * @param instruction 
     * @return  
     */
    public ArrayList<ArrayList<String>> tokenize(String instruction) {

        StringTokenizer tokens = new StringTokenizer(instruction);
        ArrayList<String> outputInstruction = new ArrayList();

        while (tokens.hasMoreElements()) {

            String elementoActual = tokens.nextToken();

            if (elementoActual.equals(";")) {

                instructions.add(outputInstruction);
                outputInstruction.clear();

            } else {
                outputInstruction.add(tokens.nextToken());
            }
        }
        return instructions;
    }


    
}
