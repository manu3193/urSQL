import java.util.ArrayList;
/**
 *
 * @author Kevin
 */

/**
 * Clase encargada de construir el System Catalog que almacena la información que controla urSQL y las bases de datos
 * existentes.
 * 
 */
public class Create {
  
    public Create(){   
    }
    
    public void buildSystemCatalog(){
     //Definición del nombre del catálogo del sistema.   
     String catalogName= "System";
     
     //Definición del nombre de las tablas que serán ingresadas en el System Catalog.
     String schemaName = "Schema";
     String tableName = "Table";
     String columnName ="Column";
     String queryLogName = "QueryLog";
     
     //Definición de las columnas a ingresar en tabla Schema.
     ArrayList<String> schemaColumns = new ArrayList<>();
     String schema = "SchemaName";
     schemaColumns.add(schema);
   
     //Definición de las columnas a ingresar en tabla Table.
     ArrayList<String> tableColumns = new ArrayList<>();
     String tableFirstCol ="SchemaName";
     String tableSecondCol= "TableName";
     tableColumns.add(tableFirstCol);
     tableColumns.add(tableSecondCol);
     
     //Definición de las columnas a ingresar en la tabla Column.
     ArrayList<String> columnColumns = new ArrayList<>();
     String columnFirstCol = "Schema";
     String columnSecondCol = "Table";
     String columnThirdCol = "Column";
     String columnFourthCol = "Type";
     String columnFifthCol = "Constraint";
     String columnSixthCol = "ForeignKey";
     String columnSeventhCol = "PrimaryKey'";
     columnColumns.add(columnFirstCol);
     columnColumns.add(columnSecondCol);
     columnColumns.add(columnThirdCol);
     columnColumns.add(columnFourthCol);
     columnColumns.add(columnFifthCol);
     columnColumns.add(columnSixthCol);
     columnColumns.add(columnSeventhCol);
     
     //Definición de las columnas a ingresar en la tabla Query Log.
     ArrayList<String> queryColumns = new ArrayList<>();
     String queryFirstCol = "Schema";
     String querySecondCol = "Table";
     String queryThirdCol = "Query";
     queryColumns.add(queryFirstCol);
     queryColumns.add(querySecondCol);
     queryColumns.add(queryThirdCol);
     
     //Creación de las tablas del System Catalog.
    //DatabaseRuntimeProcessor.createDatabase(catalogName);
    //DatabaseRuntimeProcessor.createTable(catalogName, schemaName, schemaColumns);    
    //DatabaseRuntimeProcessor.createTable(catalogName, tableName, tableColumns);
     //DatabaseRuntimeProcessor.createTable(catalogName, columnName, columnColumns);
     //DatabaseRuntimeProcessor.createTable(catalogName, queryLogName, queryColumns);      
    }
}
