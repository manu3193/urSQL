
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Nicolas Jimenez
 */
public class LexicalAnalysis {
    
    private final String instruction;
    private final ArrayList<String> outputInstruction;
    
    public LexicalAnalysis( String inputInstruction){
        
        this.instruction = inputInstruction;
        this.outputInstruction = new ArrayList<String>();
    } 
    
    public ArrayList<String> tokenize () {
        
        StringTokenizer tokens = new StringTokenizer( instruction );
       
        while ( tokens.hasMoreElements() ) {
            
            outputInstruction.add( tokens.nextToken() );
        } 
        return outputInstruction;
    } 
    
}
