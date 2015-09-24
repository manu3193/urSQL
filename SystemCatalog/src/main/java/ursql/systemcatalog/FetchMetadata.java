/*
 * Clase utilizada para obtener datos de las tablas de la metadata, la metadata contiene 4 tablas, esas son:
 *  Tabla de los esquemas de urSQL.
 * Tabla de las tablas de todas los esquemnas de urSQL
 * Tabla de las columnas de todas las tablas  de todos los esquemas de urSQL.
 *
 */
package ursql.systemcatalog;

/**
 *
 * @author Nicolas Jimenez
 */
public class FetchMetadata {

    public void fetchSchema() {

    }

    /**
     *  Regresa la tabla de tablas de urSQL.
     * @return 
     */
    public Table fetchTables() {

        Table temp = select("System", "Table", "all");
        return temp;
    }

    /**
     * Regresa la tabla de columnas.
     * @return 
     */
    public Table  fetchColumns(  ) {
        
        Table temp = select ("System", "Column", "all");
        return temp;
    }

    /**
     *  Regresa la tabla de bases de datos
     * @return 
     */
    public Table fetchSchemas() {

        Table temp = select("System", "Schema", "all");

        return temp;
    }
}
