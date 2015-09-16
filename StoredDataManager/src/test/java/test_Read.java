/**
 * Created by manzumbado on 9/15/15.
 */
import java.io.*;
import DataBaseFile.*;
import org.junit.*;
import static org.junit.Assert.*;

public class test_Read {

    InputStream inputStream;
    DBFReader reader;

    @Before
    public void init() throws IOException{
        // crea objeto DBF
        //
         inputStream  = new FileInputStream("test.db");
         reader = new DBFReader( inputStream);
    }

    @Test
    public void testFieldCount() throws IOException {
        // obtiene el numero de campos
        //
        int numberOfFields = reader.getFieldCount();
        assertEquals(numberOfFields,3);
        // bucle para iterar sobre todos los campos
        //
        for( int i=0; i<numberOfFields; i++) {

            DBFField field = reader.getField( i);

            // Realizar las operaciones requeridas
            //
            System.out.println(field.getName());

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







}

