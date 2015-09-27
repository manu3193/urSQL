package DatabaseRuntimeProcessor;/*
 * Esta clase de DatabaseRuntimeProcessor.CreateDatabase se encarga de crear un esquema  (base de datos) al hacer esto
 * se agrega el esquema nuevo.
 * Este se agrega a la metadata del urSQL y tambien se crea una carpeta en el sistema de archivos donde se 
 * agregaran las tablas. 
 *
 */

import java.util.ArrayList;
import Shared.Structures.Table;
import SystemCatalog.FetchMetadata;
import SystemCatalog.WriteMetadata;
import Shared.Structures.Row;
import Shared.Structures.Field;

/**
 *
 * @author Nicolas Jimenez
 */
public class CreateDatabase {

    public void createDatabase(String dataBase) {

        FetchMetadata schemas = new FetchMetadata();
        Table dataBaseSchemas = schemas.fetchSchemas();

        if (!verifyExist(dataBaseSchemas, dataBase)) {
            System.out.println("No se puede crear la base de datos");
            return;
        }
        addMetadata(dataBase);
        addSchema(dataBase);
    }

    /**
     * Verifica que la base de datos no exista.
     *
     * @param dataBaseSchemas
     * @param dataBase
     * @return
     */
    private boolean verifyExist(Table dataBaseSchemas, String dataBase) {

        ArrayList<Row> filas = dataBaseSchemas.getRows();

        //return filas.stream().map((fila) -> fila.getColumns()).noneMatch((campos) -> (!campos.stream().noneMatch((campo) -> ( campo.getContent().equals(dataBase)))));
        
                for (Row fila : filas) {

            ArrayList<Field> campos = fila.getColumns();
            for ( Field campo : campos ) {

                if ( campo.getContent().equals(dataBase)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Agrega la metadata del nuevo esquema.
     *
     * @param databaseName
     */
    private void addMetadata(String databaseName) {

        WriteMetadata writer = new WriteMetadata();

        writer.writeEsquema(databaseName);

    }

    
    private void addSchema(String databaseName) {

    }
}
