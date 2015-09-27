
package ursql.systemcatalog;

/*
 * WriteMetadata!
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
     * Anade un nuevo esquema (fila) a la tabla de esquemas de la metadata.
     *
     * @param nombreEsquema
     */
    public void writeEsquema(String nombreEsquema) {

        ArrayList<String> tempValor = new ArrayList();
        ArrayList<String> tempCol = new ArrayList();

        tempValor.add(nombreEsquema);
        tempCol.add("SchemaName");
        DropTable temp = new DropTable();
        
        insertInto( "Schema",  tempCol, tempValor , "System" );
    }

    /**
     * Elimina una fila(esquema) de la tabla de esquemas de la metadata
     *
     * @param nombreEsquema
     */
    public void deleteEsquema(String nombreEsquema) {

        delete( "SchemaName","Schema", "=", nombreEsquema,  "System");
    }

    /**
     * Escribe una nueva fila(tabla) a la tabla de tablas de la metadata
     *
     * @param nombreEsquema
     * @param nombreTabla
     */
    public void writeTabla(String nombreEsquema, String nombreTabla) {

        ArrayList<String> tempValor = new ArrayList();
        ArrayList<String> tempCol = new ArrayList();

        tempValor.add(nombreEsquema);
        tempValor.add(nombreTabla);
        tempCol.add("SchemaName");
        tempCol.add("TableName");

        insertInto("System", "Table", tempCol, tempValor);
    }

    /**
     * Elimina una fila (tabla) de la tabla de tablas de la metadata.
     *
     * @param nombreEsquema
     * @param nombreTabla
     */
    public void deleteTabla(String nombreEsquema, String nombreTabla) {

        delete("Sytem", "Table", "TableName", "=", nombreTabla);
    }

    /**
     * anade una columna (fila) a la tabla de columnas de la metadata
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
     * Elimina una columna (fila) de la tabla de columnas de la metadata.
     *
     * @param nombreColumna
     * @param nombreTabla
     * @param nombreEsquema
     */
    public void deleteColumna(String nombreColumna, String nombreTabla, String nombreEsquema) {

        delete("System", "Column", "ColumnName", "=", nombreColumna);
    }

    /**
     * Escribe a la tabla de consultas queryLog, una consulta
     *
     * @param nombreConsulta
     * @param nombreTabla
     * @param nombreEsquema
     */
    public void queryLog(String nombreConsulta, String nombreTabla, String nombreEsquema) {

        ArrayList<String> tempValor = new ArrayList();
        tempValor.add(nombreEsquema);
        tempValor.add(nombreTabla);
        tempValor.add(nombreConsulta);

        ArrayList<String> tempCol = new ArrayList();
        tempCol.add("Schema");
        tempCol.add("Table");
        tempCol.add("Query");

        insertInto("System", tempCol, tempValor);
    }

    /**
     *
     * @param column
     * @param originTable
     * @param columnReferenced
     * @param tableReferenced
     * @param schema
     */
    public void writerForeignKey(String column, String originTable, String columnReferenced, String tableReferenced,
            String schema) {

        ArrayList<String> tempCol = new ArrayList<>();
        tempCol.add("Column");
        tempCol.add("OriginTabla");
        tempCol.add("ColumnReferenced");
        tempCol.add("TableReferenced");
        tempCol.add("Schema");

        ArrayList<String> tempVal = new ArrayList<>();
        tempVal.add(column);
        tempVal.add(originTable);
        tempVal.add(columnReferenced);
        tempVal.add(tableReferenced);
        tempVal.add(schema);

        insertInto("System", tempCol, tempVal);
    }

    /**
     * Elimina una llave foranea por esquema
     * @param schema 
     */
    public void deleteForeignKey( String schema) {

        delete("System", "Schema", "", "=", schema);

    }

}
