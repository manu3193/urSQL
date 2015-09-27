package SystemCatalog;

import DatabaseRuntimeProcessor.*;

/*
 * WriteMetadata!
 * Esta clase sirve para escribir informacion dentro de la metadata de main
 * Se escribe nombres de esquemas, nombres de tablas, nombres de consultas ejecutadas y 
 * nombres de columnas con tipo, si es llave primaria y si es llave foranea. 
 */
import java.util.ArrayList;
import DatabaseRuntimeProcessor.InsertInto;

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

        InsertInto inserter = new InsertInto();

        inserter.executeInsertion("Schema", tempCol, tempValor, "System");
    }

    /**
     * Elimina una fila(esquema) de la tabla de esquemas de la metadata
     *
     * @param nombreEsquema
     */
    public void deleteEsquema(String nombreEsquema) {

        Delete deleter = new Delete ("Schema", "SchemaName" , "=", nombreEsquema );
        
   //     delete("SchemaName", "Schema", "=", nombreEsquema, "System");
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

        InsertInto inserter = new InsertInto();
        inserter.executeInsertion("Table", tempCol, tempValor, "System");
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
     * @param primaryKey
     */
    public void writeColumna(String nombreEsquema, String nombreTabla, String nombreColumna, String tipo,
            String constraints,  String primaryKey) {

        ArrayList<String> tempCol = new ArrayList();
        tempCol.add("Schema");
        tempCol.add("Table");
        tempCol.add("Column");
        tempCol.add("Type");
        tempCol.add("Constraint");
        tempCol.add("Schema");
        tempCol.add("PrimaryKey");

        ArrayList<String> tempVal = new ArrayList();
        tempVal.add(nombreEsquema);
        tempVal.add(nombreTabla);
        tempVal.add(nombreColumna);
        tempVal.add(tipo);
        tempVal.add(constraints);
        tempVal.add(primaryKey);

        InsertInto inserter = new InsertInto();
        inserter.executeInsertion("Column", tempCol ,tempVal, "System");
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

        InsertInto inserter = new InsertInto();
        inserter.executeInsertion("QueryLog", tempCol, tempValor, "System");
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

        ArrayList<String> tempCol = new ArrayList<String>();
        tempCol.add("Column");
        tempCol.add("OriginTabla");
        tempCol.add("ColumnReferenced");
        tempCol.add("TableReferenced");
        tempCol.add("Schema");

        ArrayList<String> tempVal = new ArrayList<String>();
        tempVal.add(column);
        tempVal.add(originTable);
        tempVal.add(columnReferenced);
        tempVal.add(tableReferenced);
        tempVal.add(schema);

        InsertInto inserter = new InsertInto();
        inserter.executeInsertion("ForeignKey", tempCol, tempVal,"System");
    }

    /**
     * Elimina una llave foranea por esquema
     *
     * @param schema
     */
    public void deleteForeignKey(String schema) {

        delete("System", "Schema", "", "=", schema);

    }

}
