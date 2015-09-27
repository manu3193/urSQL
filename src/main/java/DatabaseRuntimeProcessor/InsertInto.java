package DatabaseRuntimeProcessor;
/**
 * Clase creada con el objetivo de implementar el comando DML insert into. Se
 * recibe como parámetros el nombre de la tabla, un arraylist de las columnas a
 * modificar, un arraylist con los valores a ingresar en la nueva fila y el
 * esquema al que pertenece dicha tabla.
 *
 * @author Kevin
 */
import Shared.Structures.Table;
import Shared.Structures.Row;
import Shared.Structures.Field;
import java.util.ArrayList;
import SystemCatalog.FetchMetadata;
import StoredDataManager.Main.StoredDataManager;


public class InsertInto {

    FetchMetadata metadata;
    StoredDataManager insertManager;
    public void executeInsertion(String tableName, ArrayList<String> columns, ArrayList<String> values, String schemaName) {
        boolean doesExist;
        Row rowToInsert= new Row();
        doesExist = verifyExistence(schemaName, tableName);
        boolean inOrder;
        if (doesExist){
           rowToInsert= buildRow(columns, values, tableName, schemaName);
           insertManager.insertIntoTable(rowToInsert);
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
    private Row buildRow(ArrayList<String>columns, ArrayList<String> values, String tableName, String schemaName){
        Row newTuple = new Row();
        ArrayList<Field> temp = new ArrayList<Field>();
        Field element = new Field();
        for(int i=0;i<columns.size();i++){
            element.setContent(values.get(i));
            element.setSchemaName(schemaName);
            element.setTableName(tableName);
            temp.add(element);
        }
        newTuple.setColumns(temp);
        return newTuple;
    }
}
