package DatabaseRuntimeProcessor;/*
 * DatabaseRuntimeProcessor.DropDatabase
 * Elimina un esquema de urSQL
 * con todo lo que tenga asociado como tablas y columnas
 *
 */

import Shared.Structures.Field;
import Shared.Structures.Row;
import Shared.Structures.Table;
import java.util.ArrayList;
import SystemCatalog.FetchMetadata;
import SystemCatalog.WriteMetadata;

/**
 *
 * @author Nicolas Jimenez
 */
public class DropDatabase {

    public void dropDatabase(String dataBase) {

        FetchMetadata schemas = new FetchMetadata();
        Table dataBaseSchemas = schemas.fetchSchemas();

        if (verifyExist(dataBaseSchemas, dataBase)) {
            System.out.println("No se puede eliminar la base de datos ya que no existe la base de datos");
            return;
        }
        deleteMetadata(dataBase);
        //   addSchema(dataBase);
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

        return filas.stream().map((fila) -> fila.getColumns()).noneMatch((campos) -> (!campos.stream().noneMatch((campo)
                -> (campo.getContent().equals(dataBase)))));
    }

    /**
     * Elimina todo lo que este asociado al esquema en la metadata, incluyendo
     * tablas y columnas.
     *
     * @param databaseName
     */
    private void deleteMetadata(String databaseName) {

        WriteMetadata deleter = new WriteMetadata();
        deleter.deleteEsquema(databaseName);

        FetchMetadata fetcher = new FetchMetadata();
        Table tablas = fetcher.fetchTables();
        Table columnas = fetcher.fetchColumns();
        Table foreignkeys = fetcher.fetchForeignKey();

        ArrayList<Row> filas = tablas.getRows();

        for (int i = 0; i < filas.size(); i++) {

            Row fila = filas.get(i);
            ArrayList<Field> campos = fila.getColumns();
            Field campo = campos.get(0);

            if (campo.getContent().equals(databaseName)) {

                String nombreTabla = campos.get(1).getContent();
                deleter.deleteTabla(databaseName, nombreTabla);
            }
        }

        ArrayList<Row> filasColumnas = columnas.getRows();
        // Elimina las columnas asociadas
        for (int i = 0; i < filasColumnas.size(); i++) {

            Row fila = filasColumnas.get(i);
            ArrayList<Field> campos = fila.getColumns();
            Field campo = campos.get(0);

            if ( campo.getContent().equals ( databaseName )  )  {

                String nombreTabla = campos.get(1).getContent();
                String nombreColumna = campos.get(2).getContent();
                deleter.deleteColumna( nombreTabla, nombreTabla, databaseName ) ;
            }
        }
        ArrayList<Row> filasForeanea = foreignkeys.getRows();
        // Elimina las llaves foraneas asociadas
        for (int i = 0; i < filasForeanea.size(); i++) {

            Row fila = filasForeanea.get(i);
            ArrayList<Field> campos = fila.getColumns();
            Field campo = campos.get(4);

            if ( campo.getContent().equals ( databaseName )  )  {

                deleter.deleteForeignKey(databaseName);
            }
        }        
    }
    
    private void deleteSchema(){
        
    }

}
