package Analysis;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nicolasjimenez
 */
public class Parser {

    private final ArrayList<String> instruction;
    private final ArrayList<String> comparators;
    private final ArrayList<String> aggregate;

    public Parser(ArrayList<String> instruction) {

        this.instruction = instruction;
        comparators = new ArrayList();
        comparators.add("<");
        comparators.add(">");
        comparators.add("=");
        comparators.add("like");
        comparators.add("not");

        this.aggregate = new ArrayList();
        aggregate.add("count");
        aggregate.add("average");
        aggregate.add("min");
        aggregate.add("max");
    }

    public boolean parse() {

        return whatOperation();
    }

    private boolean whatOperation() {

        int instructionSize = instruction.size();

        if (instructionSize == 0) {
            return false;
        }

        String token0 = instruction.get(0);

        if (instruction.size() == 1) {
            return token0.equalsIgnoreCase("start") || token0.equalsIgnoreCase("stop");
        }

        String token1 = instruction.get(1);

        if (token0.equalsIgnoreCase("create")) {

            if (token1.equalsIgnoreCase("database")) {

                if (!(instructionSize == 3)) {
                    return false;
                }

                return !isNumeric(instruction.get(2));

            } else if (token1.equalsIgnoreCase("table")) {

                return createTable(instructionSize);

            } else if (token1.equalsIgnoreCase("index")) {

                return createIndex(instructionSize);
            } else {
                return false;
            }
        }
        if (token0.equalsIgnoreCase("drop")) {

            return drop(instructionSize);
        }
        if (token0.equalsIgnoreCase("list")) {

            return token1.equalsIgnoreCase("databases") && instructionSize == 2;
        }
        if (token0.equalsIgnoreCase("get")) {

            return token1.equalsIgnoreCase("status") && instructionSize == 2;
        }
        if (token0.equalsIgnoreCase("display")) {

            if (token1.equalsIgnoreCase("database") && instructionSize == 3) {

                if (!isNumeric(instruction.get(2))) {
                    return true;
                }
            }
            return false;
        }
        if (token0.equalsIgnoreCase("set")) {

            if (token1.equalsIgnoreCase("database") && instructionSize == 3) {

                if (!isNumeric(instruction.get(2))) {
                    return true;
                }
            }
            return false;
        }
        if (token0.equalsIgnoreCase("alter")) {

            return alter(instructionSize);
        }
        if (token0.equalsIgnoreCase("select")) {

            return select(instructionSize);
        }
        if (token0.equalsIgnoreCase("update")) {

            return update(instructionSize);
        }
        if (token0.equalsIgnoreCase("delete")) {

            return delete(instructionSize);
        }
        if (token0.equalsIgnoreCase("insert")) {

            return insert(instructionSize);
        } else {
            return false;
        }
    }

    private boolean drop(int size) {

        if (size != 3) {
            return false;
        }
        String token1 = instruction.get(1);
        if (token1.equalsIgnoreCase("table") || token1.equalsIgnoreCase("database")) {

            return !isNumeric(instruction.get(2));

        } else {
            return false;
        }

    }

    private boolean createTable(int size) {

        if (size < 15) {
            return false;
        }
        if (!instruction.get(1).equalsIgnoreCase("table") || isNumeric(instruction.get(2))
                || !instruction.get(3).equalsIgnoreCase("as") || !instruction.get(4).equals("(")) {
            return false;
        }
        int continuar = columnDefinition(5, size, "primary");
        if (continuar == -1) {
            return false;
        }
        if (continuar + 8 > size) {
            return false;
        }

        if (!instruction.get(continuar + 1).equalsIgnoreCase("primary")
                || !instruction.get(continuar + 2).equalsIgnoreCase("key") || !instruction.get(continuar + 3).equals("(")
                || isNumeric(instruction.get(continuar + 4)) || !instruction.get(continuar + 5).equals(")")) {
            return false;
        }
        return instruction.get(continuar + 6).equals(")") || instruction.get(continuar + 7).equals(";");
    }

    private boolean createIndex(int size) {

        if (instruction.size() != 8) {
            return false;
        }
        String tableName = instruction.get(4);
        String columnName = instruction.get(6);
        return instruction.get(3).equalsIgnoreCase("on") && instruction.get(5).equals("(")
                && instruction.get(7).equals(")") && !isNumeric(tableName) && !isNumeric(columnName);
    }

    private boolean alter(int size) {

        if (size != 15) {
            return false;
        }

        if (isNumeric(instruction.get(2)) || isNumeric(instruction.get(8)) || isNumeric(instruction.get(11))
                || isNumeric(instruction.get(13))) {
            return false;
        }

        return instruction.get(1).equalsIgnoreCase("table") && instruction.get(3).equalsIgnoreCase("add")
                && instruction.get(4).equalsIgnoreCase("constraint") && instruction.get(5).equalsIgnoreCase("foreign")
                && instruction.get(6).equalsIgnoreCase("key") && instruction.get(7).equals("(")
                && instruction.get(9).equals(")") && instruction.get(10).equalsIgnoreCase("references")
                && instruction.get(12).equals("(") && instruction.get(14).equals(")");
    }

    private boolean select(int size) {

        if (size < 4) {
            return false;
        }
        int continuar = columns(1, size, "from");

        if (continuar == -1 || !(size > continuar + 1)) {
            return false;
        }
        continuar = join(continuar + 1, size);

        if (continuar == 0) {
            return true;
        }
        if (continuar == -1) {
            return false;
        }
        String instruccion = instruction.get(continuar);
        if (instruccion.equalsIgnoreCase("where")) {

            if (size <= continuar + 3) {
                return false;
            }
            if (size > continuar + 4) {

                if (instruction.get(continuar + 4).equalsIgnoreCase("null")) {

                    if (size > continuar + 5) {

                        continuar = whereStatement(continuar, continuar + 5, continuar + 4);  // si termina el where en un is not null
                        if (continuar == -1) {                                                                          // entonces se sigue
                           
                            return false;
                        }
                        instruccion = instruction.get(continuar);

                    } else if (size == continuar + 5) {                       // si termina el where con un is not null

                        return whereStatement(continuar, size, continuar + 4) != -1;
                    } else {
                        return false;
                    }

                } else if (instruction.get(continuar + 4).equalsIgnoreCase("group")                               //ehereefefefef****
                        || instruction.get(continuar + 4).equalsIgnoreCase("for")) {
   
                    continuar = whereStatement(continuar, continuar + 4, continuar + 4); // si de fijo sigue y no hay is not null
                    if (continuar == -1) {
                        return false;  
                    }
                    instruccion = instruction.get(continuar);
                } else {
                    return false;
                }
            } else if (size == continuar + 4) {

                return whereStatement(continuar, size, continuar + 4) != -1;  //si ya es el fin un where sin (not null)
            }
        }

        if (instruccion.equalsIgnoreCase("group")) {

            if (!instruction.get(continuar + 1).equalsIgnoreCase("by") || size <= continuar + 2) {
                return false;
            }
            String token;
            boolean last = false;  // false si es una ","  ;  si es una columna: true
            for (int i = continuar + 2; i < size; i++) {

                token = instruction.get(i);

                if (token.equalsIgnoreCase("for")) {
                    continuar = i;
                    instruccion = instruction.get(continuar);
                    break;
                }
                if (i + 1 == size && !token.equals(",") )   {
                    return !last;
                }
                if (last) {
                    //last es true; significa que debemos encontrar una , 
                    if (!token.equals(",")) {
                        return false;
                    }
                    last = false;
                } else {   // last es false; significa que debemos encontrar una columna 

                    if (token.equals(",") || isNumeric(token)) {
                        return false;
                    }
                    last = true;
                }
            }
        }
        if (instruccion.equalsIgnoreCase("for")) {

            return (size == continuar + 2 && ( instruction.get(continuar + 1).equalsIgnoreCase("xml")
                    || instruction.get(continuar + 1).equals("json") ));
        }
        return false;
    }

    private boolean update(int size) {

        if (size < 6) {
            return false;
        }

        if (!(instruction.get(2).equalsIgnoreCase("set")) || isNumeric(instruction.get(1))
                || isNumeric(instruction.get(3)) || !instruction.get(4).equals("=")) {
            return false;
        }
        if (size == 6) {
            return true;
        }
        if (size < 10 || size > 11) {
            return false;
        }
        return whereStatement(6, size, 10) != -1;
    }

    private boolean delete(int size) {

        if (size < 3 || !(instruction.get(1).equalsIgnoreCase("from")) || isNumeric(instruction.get(2))) {
            return false;
        }
        if (size == 3) {
            return true;
        }
        if (size < 7 || size > 8) {
            return false;
        }
        return whereStatement(3, size, 7) != -1;
    }

    private boolean insert(int size) {

        if (size < 10) {
            return false;
        }
        if (!(instruction.get(1).equalsIgnoreCase("into")) || isNumeric(instruction.get(2)) || !instruction.get(3).equals("(")) {
            return false;
        }
        int startValues = 0;
        boolean foundRBracket = false;
        int numberColumns = 0;
        boolean lastElement = false;    // si es , entonces es false;  si es columna entonces true
        for (int i = 4; i < size; i++) {

            String token = instruction.get(i);

            if (token.equals(")")) {
                foundRBracket = true;

                if (lastElement == false) {
                    return false;
                }
                startValues = i + 1;
                break;
            }
            if (!token.equals(",")) {  //es columna

                if (isNumeric(token) || lastElement == true) {
                    return false;
                }
                lastElement = true;
                numberColumns++;
            } else if (token.equals(",")) {

                if (lastElement == false) {
                    return false;
                }

                lastElement = false;
            } else {
                return false;
            }
        }
        if (!foundRBracket) {
            return false;
        }

        if (startValues + numberColumns > size || startValues == 0) {
            return false;
        }
        if (!instruction.get(startValues).equalsIgnoreCase("values") || !instruction.get(startValues + 1).equals("(")) {
            return false;
        }
        int numberValues = 0;
        lastElement = false;
        for (int i = startValues + 2; i < size; i++) {

            String token = instruction.get(i);

            if (token.equals(")")) {

                if (lastElement == false) {
                    return false;
                }
                if (numberValues == numberColumns && size == i + 1) {
                    return true;
                } else {
                    break;
                }
            }

            if (!token.equals(",")) {  //es valor

                if (lastElement == true) {
                    return false;
                }
                lastElement = true;
                numberValues++;

            } else if (token.equals(",")) {  //es ,

                if (lastElement == false) {
                    return false;
                }

                lastElement = false;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean isNumeric(String str) {

        if (str.isEmpty() || str.contains("+") || str.contains("-") || str.contains("*") || str.contains("?") || str.contains("!")
                || str.contains("(") || str.contains(")") || str.contains("%") || str.contains("&") || str.contains("@") || str.contains("#")
                || str.contains("$") || str.contains("^") || str.contains("|")) {
            return true;
        }

        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private int join(int startPoint, int size) {
        if (startPoint + 1 > size) {
            return -1;
        }
        if (startPoint + 1 == size && !isNumeric(instruction.get(startPoint))) {
            return 0;
        }
        if (startPoint + 2 == size ){
            return -1;
        }
        if (startPoint + 2 < size) {
            String next = instruction.get(startPoint + 1);
            if (next.equalsIgnoreCase("where") || next.equalsIgnoreCase("group") || next.equalsIgnoreCase("for")) {
                return startPoint + 1;
            }
        }
        String currentToken;
        boolean lastElement = false;  // false si el ultimo fue un join, true si el ultimo fue una tabla
        
        for (int i = startPoint; i < size; i++) {
            
            currentToken = instruction.get(i);
            
            if (i+1 == size ){
                
                if (!lastElement && !currentToken.equalsIgnoreCase("join")){
                    return 0;
                }
                else{
                    return -1;
                }
            }
            if (i+1 < size) {
                
                if (instruction.get(i + 1).equalsIgnoreCase("where") || instruction.get(i + 1).equalsIgnoreCase("group")
                        || instruction.get(i + 1).equalsIgnoreCase("for")) {

                    return i + 1;
                }
            }
            if (currentToken.equalsIgnoreCase("join")) {
                if (!lastElement) {
                    return -1;
                }
                lastElement = false;
            } else {
                if (isNumeric(currentToken) || lastElement == true )  {
                    return -1;
                }
                lastElement= true;
            }
        }
        return -1;
    }

    private int columns(int startPoint, int size, String endToken) {

        String startToken = instruction.get(startPoint);

        if (startToken.equals("*") && instruction.get(startPoint + 1).equalsIgnoreCase(endToken)) {
            return startPoint + 1;
        }
        String currentToken;
        boolean last = false;  // false si el ultimo fue una , 

        for (int i = startPoint; i < size; i++) {
            
            currentToken = instruction.get(i);
            if (!isNumeric(currentToken) && !currentToken.equals(",") && !currentToken.equals(endToken)) {
                if (last) {
                    return -1;
                }
                last = true;
                continue;
            }
            if (currentToken.equals(",")) {

                if (!last) {
                    return -1;
                }
                last = false;

                if (i + 3 < size) {
                    if (aggregate.contains(instruction.get(i + 1))) { 
                        if (instruction.get(i + 2).equals("(") && !isNumeric(instruction.get(i + 3)) && instruction.get(i + 4).equals(")")
                                && instruction.get(i + 5).equalsIgnoreCase(endToken)) {
                            return i + 5;
                        }
                        return -1;
                    }
                } else {
                    System.out.println("aqui");
                    return -1;
                }
                
            } else if (currentToken.equals(endToken)) { //llega a from, sin pasar por los aggregate functions.

                if (!last) {
                    System.out.println("hjh");
                    return -1;
                }
                return i;
            } else {
                return -1;
            } 
        }
        System.out.println("aer");
        return -1;
    }

    private int columnDefinition(int startPoint, int size, String finishToken) {

        int phase = 0;

        for (int i = startPoint; i < size; i++) {
            String token = instruction.get(i);
            if (phase == 0) {

                if (isNumeric(token) || token.equalsIgnoreCase("not") || token.equalsIgnoreCase("null")
                        || token.equalsIgnoreCase("integer") || token.equalsIgnoreCase("varchar") || token.equalsIgnoreCase("datetime")
                        || token.equalsIgnoreCase("decimal")) {
                    return -1;
                }
                phase++;
                continue;
            }
            if (phase == 1) {

                if (token.equalsIgnoreCase("integer") || token.equalsIgnoreCase("datetime")
                        || token.equalsIgnoreCase("varchar")) {
                    phase = 2;
                    continue;
                }
                if (token.equalsIgnoreCase("decimal") || token.equalsIgnoreCase("char")) {
                    continue;
                }
                String anterior = instruction.get(i - 1);
                if (token.equalsIgnoreCase("(") && (anterior.equalsIgnoreCase("decimal") || anterior.equalsIgnoreCase("char"))) {
                    continue;
                }
                if ((anterior.equals("(") || anterior.equals(",")) && isNumeric(token)) {
                    continue;
                }
                if (token.equals(")") && isNumeric(anterior)) {
                    phase = 2;
                    continue;
                }
                if (token.equals(",") && isNumeric(anterior)) {
                    continue;
                }
                return -1;
            }
            if (phase == 2) {

                if (token.equalsIgnoreCase("null")) {
                    phase = 3;
                    continue;
                }
                if (token.equalsIgnoreCase("not") && !instruction.get(i - 1).equalsIgnoreCase("not")) {
                } else {
                    return -1;
                }
            } else if (phase == 3) {

                if (token.equals(",") && instruction.get(i + 1).equals(finishToken)) {
                    return i;
                } else if (token.equals(",") && !instruction.get(i + 1).equals(finishToken)) {
                    phase = 0;
                } else {
                    return -1;
                }
            }
        }

        return -1;
    }

    private int whereStatement(int start, int size, int requirement) {

        if (!(instruction.get(start).equalsIgnoreCase("where")) || isNumeric(instruction.get(start + 1))) {
            return -1;
        }

        String position8 = instruction.get(start + 2);
        String position9 = instruction.get(start + 3);
        if ((comparators.contains(position8) || (position8.equals("is") && position9.equals("null"))) && size == requirement) {
            return requirement;
        }
        if (size != requirement + 1) {
            return -1;
        }

        String position10 = instruction.get(start + 4);
        if (position8.equals("is") && position9.equals("not") && position10.equals("null")) {
            return requirement + 1;
        } else {
            return -1;
        }
    }
}
