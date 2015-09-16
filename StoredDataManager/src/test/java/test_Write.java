/**
 * Created by manzumbado on 9/15/15.
 */
import DataBaseFile.*;
import org.junit.*;
import java.io.*;


public class test_Write {

    DBFWriter writer;
    FileOutputStream fos;

    @Before
    public void test_Write() throws FileNotFoundException{
        writer = new DBFWriter();
         fos = new FileOutputStream( "test.db");
    }

    @Test
    public void populate() throws DBFException, IOException{
        // Creamos un arreglo de 3 campos
        //
        DBFField fields[] = new DBFField[ 3];

        fields[0] = new DBFField();
        fields[0].setName( "emp_code");
        fields[0].setDataType( DBFField.FIELD_TYPE_C);
        fields[0].setFieldLength(10);

        fields[1] = new DBFField();
        fields[1].setName("emp_name");
        fields[1].setDataType( DBFField.FIELD_TYPE_C);
        fields[1].setFieldLength(20);

        fields[2] = new DBFField();
        fields[2].setName("salary");
        fields[2].setDataType( DBFField.FIELD_TYPE_N);
        fields[2].setFieldLength(12);
        fields[2].setDecimalCount(2);
        writer.setFields(fields);

        Object rowData[] = new Object[3];
        rowData[0] = "1000";
        rowData[1] = "John";
        rowData[2] = new Double( 5000.00);

        writer.addRecord( rowData);

        rowData = new Object[3];
        rowData[0] = "1001";
        rowData[1] = "Lalit";
        rowData[2] = new Double( 3400.00);

        writer.addRecord( rowData);

        rowData = new Object[3];
        rowData[0] = "1002";
        rowData[1] = "Rohit";
        rowData[2] = new Double( 7350.00);

        writer.addRecord( rowData);
        writer.write( fos);
        fos.close();
    }
}
