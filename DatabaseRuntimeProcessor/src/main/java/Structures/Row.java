package Structures;

import java.util.ArrayList;

/**
 * Clase encargada de crear un objeto tipo Row. La fila contiene un conjunto de campos (Field) que describen la información
 * contenida en cada columna de la tabla.
 * @author Kevin
 */
public class Row {
    private ArrayList<Field> columns;
    private final int length;
    
    public Row(){
        this.columns = null;
        this.length = 0;
    }
    public Row(ArrayList<Field> columns){
     this.columns = columns;
     this.length = columns.size();
    }

    /**
     * @return the columns
     */
    public ArrayList<Field> getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(ArrayList<Field> columns) {
        this.columns = columns;
    }

    /**
     * @return the length
     */
    public int getLength() {
        
        return this.columns.size();
    }
    
    
    
}
