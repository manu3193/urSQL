package DatabaseRuntimeProcessor;

import Shared.Structures.Field;
import Shared.Structures.Row;
import Shared.Structures.Table;
import java.util.ArrayList;
import SystemCatalog.FetchMetadata;

/**
 * Clase encargada de hacer el comando DatabaseRuntimeProcessor.DisplayDatabase.
 *
 * @author Kevin Umaña
 */
public class DisplayDatabase {

    FetchMetadata metadata = new FetchMetadata();

    /**
     *
     * @param schemaName
     * @return String that represents the elements contained in the specified
     * schema.
     */
    public String displayDatabase(String schemaName) {
        boolean doesExist;
        ArrayList<String> tablesName = new ArrayList<String>();
        doesExist = verifyExistence(schemaName);
        String resultToPrint1 = "";
        String resultToPrint2 = "";
        if (doesExist) {
            tablesName = gatherMatchingTables(schemaName);
            resultToPrint1 = printTables(tablesName, schemaName);
            resultToPrint2 = describeTables(schemaName, tablesName);
        } else {
            System.out.println("Error. There is no schema with such name. Please try again.");
        }
        resultToPrint1 = resultToPrint1.concat(resultToPrint2);
        return resultToPrint1;
    }

    private boolean verifyExistence(String schemaName) {
        Table schemaTable = metadata.fetchSchemas();
        int length = schemaTable.getLength();
        Field element;
        boolean result = false;
        for (int i = 1; i < length; i++) {
            element = schemaTable.getRows().get(i).getColumns().get(0);
            if (element.getContent().equals(schemaName)) {
                result = true;
                break;
            } else {
                result = false;
            }
        }
        return result;
    }

    @SuppressWarnings("empty-statement")
    private ArrayList<String> gatherMatchingTables(String schemaName) {
        Table tableSet;
        tableSet = metadata.fetchTables();
        int i = 1;
        ArrayList<String> tables = new ArrayList<String>();
        while (i < tableSet.getLength()) {
            for (int j = 0; j < tableSet.getRows().get(i).getColumns().size(); j++) {
                if (tableSet.getRows().get(i).getColumns().get(0).getContent().equals(schemaName)) {
                    tables.add(tableSet.getRows().get(i).getColumns().get(1).getContent());
                } else {
                    ;
                }
            }
            i++;
        }
        return tables;
    }

    private String printTables(ArrayList<String> tablesToDisplay, String schemaName) {
        //StringBuilder en caso de que no se imprima en consola.
        StringBuilder tempResult = new StringBuilder();
        tempResult.append("+------------------------------------------------------------------------------+\n");
        tempResult.append("| Lista de tablas del esquema: ");
        tempResult.append(schemaName);
        tempResult.append(" |\n");
        tempResult.append("+------------------------------------------------------------------------------+\n");
        System.out.println("+------------------------------------------------------------------------------+\n");
        System.out.println("| Lista de tablas del esquema: " + schemaName + " |\n");
        System.out.println("+------------------------------------------------------------------------------+\n");
        for (int i = 0; i < tablesToDisplay.size(); i++) {
            if (i + 1 == tablesToDisplay.size()) {
                System.out.println("| " + tablesToDisplay.get(i) + " |\n");
                System.out.println("+------------------------------------------------------------------------------+\n");
                tempResult.append("| ");
                tempResult.append(tablesToDisplay.get(i));
                tempResult.append(" |\n");
                tempResult.append("+------------------------------------------------------------------------------+\n");
            } else {
                System.out.println("| " + tablesToDisplay.get(i) + " |\n");
                tempResult.append("| ");
                tempResult.append(tablesToDisplay.get(i));
                tempResult.append(" |\n");
            }
        }
        String result = tempResult.toString();
        return result;
    }

    private String describeTables(String schemaName, ArrayList<String> tablesName) {
        Table columnTable = metadata.fetchColumns();
        Row columnsNames = columnTable.getRows().get(0);
        StringBuilder result = new StringBuilder();
        result.append("Lista de columnas para la base de datos: ");
        result.append(schemaName);
        result.append("\n");
        for (int k = 0; k < tablesName.size(); k++) {
            result.append("La tabla: ");
            result.append(tablesName.get(k));
            result.append(" posee las siguientes columnas: \n");
            result.append("Nombre               Tipo               IsNull               isPK               \n");

            for (int i = 1; i < columnTable.getLength(); i++) {
                for (int j = 0; j < columnTable.getRows().get(i).getColumns().size(); j++) { //Cantidad elementos de una fila
                    Field element = columnTable.getRows().get(i).getColumns().get(j); //Elemento
                    if (element.getSchemaName().equals(schemaName)
                            && element.getTableName().equals(tablesName.get(k))) {
                        result.append(element.getContent());
                        result.append(" ");
                        result.append(element.getType());
                        result.append(" ");
                        result.append(element.getIsNull());
                        result.append(" ");
                        result.append(element.isPrimaryKey());
                        result.append(" \n");
                    }
                }

            }

        }
        return result.toString();
    }
}
