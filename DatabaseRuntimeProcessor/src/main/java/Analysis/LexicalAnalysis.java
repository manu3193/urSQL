
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
    

    
    
    public ArrayList<String> tokenize (String instruction) {
        
        StringTokenizer tokens = new StringTokenizer( instruction );
        ArrayList<String> outputInstruction = new ArrayList();
        
        while ( tokens.hasMoreElements() ) {

            outputInstruction.add( tokens.nextToken() );
        } 
        return outputInstruction;
    } 
    

    
}
