/**
 * Created by manzumbado on 9/15/15.
 */
import java.io.*;
import DataBaseFile.*;

public class test_Read {

    public static void main( String args[]) {

        try {

            // crea objeto DBF
            //
            InputStream inputStream  = new FileInputStream("StoredDataManager/target/test.db");
            DBFReader reader = new DBFReader( inputStream);

            // obtiene el numero de campos
            //
            int numberOfFields = reader.getFieldCount();

            // bucle para iterar sobre todos los campos
            //
            for( int i=0; i<numberOfFields; i++) {

                DBFField field = reader.getField( i);

                // Realizar las operaciones requeridas
                //
                System.out.println( field.getName());
            }

            // empieza a leer las columnas
            //
            Object []rowObjects;

            while( (rowObjects = reader.nextRecord()) != null) {

                for( int i=0; i<rowObjects.length; i++) {

                    System.out.println( rowObjects[i]);
                }
            }

            // se han recorrido todas las filas

            inputStream.close();
        }
        catch( DBFException e) {

            System.out.println( e.getMessage());
        }
        catch( IOException e) {

            System.out.println( e.getMessage());
        }
    }
}

