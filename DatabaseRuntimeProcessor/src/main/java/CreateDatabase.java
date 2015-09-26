/*
 * Esta clase de CreateDatabase se encarga de crear un esquema  (base de datos) al hacer esto 
 * se agrega el esquema nuevo.
 * Este se agrega a la metadata del urSQL y tambien se crea una carpeta en el sistema de archivos donde se 
 * agregaran las tablas. 
 *
 */

import java.util.ArrayList;
import Structures.Table;
import ursql.systemcatalog.FetchMetadata;
import ursql.systemcatalog.WriteMetadata;
import Structures.Row;
import Structures.Field;

/**
 *
 * @author JoséAlberto
 */
public class CreateDatabase {

    public void createDatabase(String dataBase) {

        FetchMetadata schemas = new FetchMetadata();
        Table dataBaseSchemas = schemas.fetchSchemas();

        if ( ! verifyExist(dataBaseSchemas, dataBase) ) {
            System.out.println("No se puede crear la base de datos");
            return;
        }
        addMetadata(dataBase);
        addSchema(dataBase);
    }

    /**
     * Verifica que la base de datos no exista.
     * @param dataBaseSchemas
     * @param dataBase
     * @return 
     */
    private boolean verifyExist(Table dataBaseSchemas, String dataBase) {

        ArrayList<Row> filas = dataBaseSchemas.getRows();

        return filas.stream().map((fila) -> fila.getColumns()).noneMatch((campos) -> (!campos.stream().noneMatch((campo) -> (campo.getContent().equals(dataBase)))));
    }

    /**
     * Agrega la metadata del nuevo esquema.
     * @param databaseName 
     */
    private void addMetadata(String databaseName) {

        WriteMetadata writer = new WriteMetadata();

        writer.writeEsquema(databaseName);

    }

    private void addSchema( String databaseName) {

        
    }
}
