
/**
 * Clase creada con el objetivo de implementar el comando DML insert into. Se
 * recibe como parámetros el nombre de la tabla, un arraylist de las columnas a
 * modificar, un arraylist con los valores a ingresar en la nueva fila y el
 * esquema al que pertenece dicha tabla.
 *
 * @author Kevin
 */
import Structures.Table;
import Structures.Row;
import Structures.Field;
import java.util.ArrayList;
import ursql.systemcatalog.FetchMetadata;

public class InsertInto {

    FetchMetadata metadata;
    //StoredDataManager insertManager;
    public void executeInsertion(String tableName, ArrayList<String> columns, ArrayList<String> values, String schemaName) {
        boolean doesExist;
        doesExist = verifyExistence(schemaName, tableName);
        boolean inOrder;
        if (doesExist){
            
            
        }else{
            System.out.println("Operation can not be completed. Table does not exist.");
        }
        
    }

    private boolean verifyExistence(String schemaName, String tableName) {
        Table metadataTables = metadata.fetchTables();
        int length = metadataTables.getLength();
        Field schemaCol;
        Field tableCol;
        boolean result = false;
        for (int i = 1; i < length; i++) {
            schemaCol = metadataTables.getRows().get(i).getColumns().get(0);
            tableCol = metadataTables.getRows().get(i).getColumns().get(1);
            if (schemaCol.getContent().equals(schemaName) && tableCol.getContent().equals(tableName)) {
                result = true;
                break;
            } else {
                result = false;
            }
        }
        return result;
    }
    //private boolean elementsInOrder
    private Row buildRow(ArrayList<String>columns, ArrayList<String> values){
        Row newTuple = new Row();
       
        
    }
}
