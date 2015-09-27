package DatabaseRuntimeProcessor;

import Shared.Structures.Field;
import Shared.Structures.Table;
import SystemCatalog.FetchMetadata;

/**
 *
 * @author Kevin
 */
public class ListDatabases {
     FetchMetadata metadata = new FetchMetadata();

    /**
     *
     *
     * @return String specifying the databases stored in the System Catalog.
     * 
     */

    public String listDatabases(){
       Table schemaTable = metadata.fetchSchemas();
       Field elemento;
       StringBuilder buildResult = new StringBuilder();
       buildResult = buildResult.append("Las bases de datos almacenadas en el sistema son: \n");
       buildResult = buildResult.append("Nombre del esquema \n");
       for(int i=1; i< schemaTable.getLength();i++){
           elemento = schemaTable.getRows().get(i).getColumns().get(0);
           buildResult = buildResult.append(elemento.getContent());
           buildResult = buildResult.append("\n");
       }
       return buildResult.toString();
    }
    
    
}
