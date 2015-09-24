/*
 * WriteMetadata
 * Esta clase sirve para
 * 
 */
package ursql.systemcatalog;

import java.util.ArrayList;

/**
 *
 * @author nicolasjimenez
 */
public class WriteMetadata {

    public WriteMetadata() {

    }

    public void writeEsquema(String nombreEsquema) {

        ArrayList<String> temp = new ArrayList();
        temp.add(nombreEsquema);

        insertInto("System", "Databases", temp);
    }

    public void writeTabla(String nombreEsquema, String nombreTabla) {

        ArrayList<String> temp = new ArrayList();
        temp.add(nombreEsquema);
        temp.add(nombreTabla);
        insertInto("System", "Table", temp);
    }

    public void writeColumna(String nombreEsquema, String nombreTabla, String nombreColumna, String tipo,
            String constraints, String foreignKey, String primaryKey) {

        ArrayList<String> temp = new ArrayList();
        temp.add(nombreEsquema);
        temp.add(nombreTabla);
        temp.add(nombreColumna);
        temp.add(tipo);
        temp.add(constraints);
        temp.add(foreignKey);
        temp.add(primaryKey);
        insertInto("System", "Columns", temp);
    }

    public void queryLog(String nombreConsulta, String nombreTabla, String nombreEsquema) {

        ArrayList<String> temp = new ArrayList();
        temp.add(nombreEsquema);
        temp.add(nombreTabla);
        temp.add(nombreConsulta);
        insertInto("System", "QueryLog", temp);
    }
}
