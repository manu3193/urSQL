/*
 * WriteMetadata 
 * Esta clase sirve para escribir informacion dentro de la metadata de urSQL
 * Se escribe nombres de esquemas, nombres de tablas, nombres de consultas ejecutadas y 
 * nombres de columnas con tipo, si es llave primaria y si es llave foranea. 
 */


import java.util.ArrayList;

/**
 * 
 * @author Nicolas Jimenez
 */
public class WriteMetadata {

    /**
     * 
     * @param nombreEsquema 
     */
    public void writeEsquema(String nombreEsquema) {

        ArrayList<String> temp = new ArrayList();
        temp.add(nombreEsquema);

        insertInto("System", "Databases", temp);
    }

    /**
     * 
     * @param nombreEsquema
     * @param nombreTabla 
     */
    public void writeTabla(String nombreEsquema, String nombreTabla) {

        ArrayList<String> temp = new ArrayList();
        temp.add(nombreEsquema);
        temp.add(nombreTabla);
        insertInto("System", "Table", temp);
    }

    /**
     * 
     * @param nombreEsquema
     * @param nombreTabla
     * @param nombreColumna
     * @param tipo
     * @param constraints
     * @param foreignKey
     * @param primaryKey 
     */
    public void writeColumna(String nombreEsquema, String nombreTabla, String nombreColumna, String tipo,
            String constraints, String foreignKey, String primaryKey) {

        ArrayList<String> temp = new ArrayList();
        temp.add(nombreEsquema);
        temp.add(nombreTabla);
        temp.add(nombreColumna);
        temp.add(tipo);
        temp.add(constraints);
        temp.add(foreignKey);
        temp.add(primaryKey);
        insertInto("System", "Columns", temp);
    }

    /**
     * 
     * @param nombreConsulta
     * @param nombreTabla
     * @param nombreEsquema 
     */
    public void queryLog(String nombreConsulta, String nombreTabla, String nombreEsquema) {

        ArrayList<String> temp = new ArrayList();
        temp.add(nombreEsquema);
        temp.add(nombreTabla);
        temp.add(nombreConsulta);
        insertInto("System", "QueryLog", temp);
    }
    
    
    
}
