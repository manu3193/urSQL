package DatabaseRuntimeProcessor;

import SystemCatalog.WriteMetadata;

/*
 *  Alter Table!
 *  Altera la metadata de una tabla para anadir un constraint (llave foranea)
 * 
 * Something different!
 */

/**
 *
 * @author Nicolas Jimenez
 */
public class AlterTable {
    
    public void alterTable( String schemaName, String tableName, String columnName, String tableReferenced,
             String referencedColumn){
        
        WriteMetadata write = new WriteMetadata();
        
        write.writerForeignKey( schemaName, tableName, columnName, tableReferenced, referencedColumn);
    }
}
