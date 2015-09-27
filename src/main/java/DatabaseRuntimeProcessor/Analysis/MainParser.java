/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseRuntimeProcessor.Analysis;

import java.util.ArrayList;

/**
 *
 * @author nicolasjimenez
 */
public class MainParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        LexicalAnalysis lex = new LexicalAnalysis();

//        ArrayList<ArrayList<String>> instrucciones = new ArrayList<ArrayList<String>>();
//
//        ArrayList<String> instruction1 = new ArrayList<String>();
//        ArrayList<String> instruction2 = new ArrayList<String>();
//        ArrayList<String> instruction3 = new ArrayList<String>();
//
//        instrucciones.add(instruction1);
//        instrucciones.add(instruction2);
//
//        instruction1.add("get");
//        instruction1.add("status");
//        instruction1.add(";");
//        
//        instruction2.add("drop");
//        instruction2.add("trffr");
//        instruction2.add(";");

        String temp = "get status ; "
                + "drop database frfr ;"
                + " create index on ; ";
        
        
        lex.tokenize(temp);

    }

}
