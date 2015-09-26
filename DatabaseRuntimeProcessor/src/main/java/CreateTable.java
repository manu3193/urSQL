/*
 * Create Table
 * Esta clase crea una nueva tabla y con ello crea nuevas columnas, llave primaria y llave foranea.
 * Al hacer esto se debe guardar tanto en disco como en la metadata de urSQL.
 */

import Structures.Field;
import Structures.Row;
import java.util.ArrayList;
import ursql.systemcatalog.WriteMetadata;

/**
 *
 * @author Nicolas Jimenez
 */
public class CreateTable {

    public void createTable(String database, String nombreTabla, Row columns) {

        addMetadata(database, nombreTabla, columns);
    }

    /**
     * Metodo para anadir la metadata de la recien creada tabla al System
     * Catalog.
     *
     * @param database
     * @param nombreTabla
     * @param columns
     */
    public void addMetadata(String database, String nombreTabla, Row columns) {

        WriteMetadata metadataProcessor = new WriteMetadata();
        metadataProcessor.writeTabla(database, nombreTabla);

        ArrayList<Field> columnas = columns.getColumns();         //Revisar might cause isssues and bugs.
        for (Field columna : columnas) {

            String nullability = null;
            if (columna.getIsNull() == true) {

                nullability = "null";
            } else {

                nullability = "notnull";
            }
            String primary = null;
            if ( columna.isPrimaryKey()) {
                
                primary = "true";
            }
            else{
                primary = "false";
            }
            
            metadataProcessor.writeColumna(database, nombreTabla, columna.getContent(), columna.getType(),
                    nullability, primary, database);

        }
    }

}
