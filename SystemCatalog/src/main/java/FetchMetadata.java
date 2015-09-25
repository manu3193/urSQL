/*
 * Clase utilizada para obtener datos de las tablas de la metadata, la metadata contiene 4 tablas, las cuales son:
 * Tabla de los esquemas de urSQL.
 * Tabla de las tablas de todas los esquemnas de urSQL
 * Tabla de las columnas de todas las tablas  de todos los esquemas de urSQL.
 * Tabla del historico de consultas.
 */


/**
 *
 * @author Nicolas Jimenez
 */
public class FetchMetadata {

    /**
     *  Retorna la tabla del historico de consultas.
     * @return 
     */
    public Table fetchQueryLog() {

        Table temp = select("System", "QueryLog", "all");
        return temp;        
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
     *  Regresa la tabla de las bases de datos existentes.
     * @return 
     */
    public Table fetchSchemas() {

        Table temp = select("System", "Schema", "all");

        return temp;
    }
}
