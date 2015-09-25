/*
 * Esta clase de CreateDatabase se encarga de crear un esquema al hacer esto se agrega el esquema nuevo
 * Este se agrega a la metadata del urSQL y tambien se crea una carpeta en el sistema de archivos donde se 
 * agregaran las tablas. 
 *
 */

/**
 *
 * @author JoséAlberto
 */
public class CreateDatabase {
    
    
    
    public void createDatabase( String dataBase ) {

        // Process this .
    }
    
    private void verifyExist() {

        //Recorrer algo para ver si existe se pondría en prueba  
        FetchMetadata schemas = new FetchMetadata();
        
        schemas.
    }
    
    private void addMetadata( String databaseName) {
        
        WriteMetadata writer = new WriteMetadata();
        
        writer.writeEsquema( databaseName );
        
    }
    
    private void addSchema() {
        
        
    }
}   
