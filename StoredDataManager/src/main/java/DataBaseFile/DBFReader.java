package DataBaseFile;

/**
 * Created by manzumbado on 9/15/15.
 *
 * DBFReader
 * Clase que se encarga de leer los registros asumiendo que
 * el InputStream dado contiene un archivo de tipo DBF
 *
 * autor original: anil@linuxense.com
 * Modificado por manzumbado on 9/15/15.
 * license: LGPL (http://www.gnu.org/copyleft/lesser.html)
 *


 */


import java.io.*;
       import java.util.*;

/**
 La clase DBFReader puede crear objetos para representa los datos del DBF.

 Esta clase se utiliza para obtener datos del DBF. Se pueden obtener metadatos y registros.

 <p>
 DBFReader no puede escribir en el archivo DBF


 <p>
 El método nextRecord() un array de objetos y el tipo de esos objetos tal como
 se muestra a continuación:

 <table>
 <tr>
 <th>Tipo xBase</th><th>Tipo Java</th>
 </tr>

 <tr>
 <td>C</td><td>String</td>
 </tr>
 <tr>
 <td>N</td><td>Integer</td>
 </tr>
 <tr>
 <td>F</td><td>Double</td>
 </tr>
 <tr>
 <td>L</td><td>Boolean</td>
 </tr>
 <tr>
 <td>D</td><td>java.util.Date</td>
 </tr>
 </table>

 */
public class DBFReader extends DBFBase {

    DataInputStream dataInputStream;
    DBFHeader header;

    /* Class specific variables */
    boolean isClosed = true;

    /**
     Inicializa el objeto DBFReader

     Cuando este constructor retorna el objeto ha completado
     la lectura del encabezado (metadatos) y la información del
     encabezado puede ser obtenida también.


     @param in desde donde son leídos los datos.
     */
    public DBFReader( InputStream in) throws DBFException {

        try {

            this.dataInputStream = new DataInputStream( in);
            this.isClosed = false;
            this.header = new DBFHeader();
            this.header.read( this.dataInputStream);

                        /* it might be required to leap to the start of records at times */
            int t_dataStartIndex = this.header.headerLength - ( 32 + (32*this.header.fieldArray.length)) - 1;
            if( t_dataStartIndex > 0) {

                dataInputStream.skip( t_dataStartIndex);
            }
        }
        catch( IOException e) {

            throw new DBFException( e.getMessage());
        }
    }


    public String toString() {

        StringBuffer sb = new StringBuffer(  this.header.year + "/" + this.header.month + "/" + this.header.day + "\n"
                + "Total de registros: " + this.header.numberOfRecords +
                "\nTamaño de encabezado: " + this.header.headerLength +
                "");

        for( int i=0; i<this.header.fieldArray.length; i++) {

            sb.append( this.header.fieldArray[i].getName());
            sb.append( "\n");
        }

        return sb.toString();
    }

    /**
     Retorna el numero de registros en el archivo DBF.
     */
    public int getRecordCount() {

        return this.header.numberOfRecords;
    }

    /**
     Retorna el campo requerido. En caso de ser un índice inválido retorna
     un ArrayIndexOutofboundsException.

     @param index Índice del campo. El índice del primer campo en 0
     */
    public DBFField getField( int index)
            throws DBFException {

        if( isClosed) {

            throw new DBFException( "La fuente no está abierta");
        }

        return this.header.fieldArray[ index];
    }

    /**
     Retorna el número de campos en el DBF.
     */
    public int getFieldCount()
            throws DBFException {

        if( isClosed) {

            throw new DBFException( "La fuente no está abierta");
        }

        if( this.header.fieldArray != null) {

            return this.header.fieldArray.length;
        }

        return -1;
    }

    /**
     Lee y retorna la siguiente fila en el stream del DBF.
     @returns La siguiente fila como un array de objetos. Los tipos de los objetos siguen la convención DBF.
     */
    public Object[] nextRecord()
            throws DBFException {

        if( isClosed) {

            throw new DBFException( "La fuente no está abierta");
        }

        Object recordObjects[] = new Object[ this.header.fieldArray.length];

        try {

            boolean isDeleted = false;
            do {

                if( isDeleted) {

                    dataInputStream.skip( this.header.recordLength-1);
                }

                int t_byte = dataInputStream.readByte();
                if( t_byte == END_OF_DATA) {

                    return null;
                }

                isDeleted = (  t_byte == '*');
            } while( isDeleted);

            for( int i=0; i<this.header.fieldArray.length; i++) {

                switch( this.header.fieldArray[i].getDataType()) {

                    case 'C':

                        byte b_array[] = new byte[ this.header.fieldArray[i].getFieldLength()];
                        dataInputStream.read( b_array);
                        recordObjects[i] = new String( b_array, characterSetName);
                        break;

                    case 'D':

                        byte t_byte_year[] = new byte[ 4];
                        dataInputStream.read( t_byte_year);

                        byte t_byte_month[] = new byte[ 2];
                        dataInputStream.read( t_byte_month);

                        byte t_byte_day[] = new byte[ 2];
                        dataInputStream.read( t_byte_day);

                        try {

                            GregorianCalendar calendar = new GregorianCalendar(
                                    Integer.parseInt( new String( t_byte_year)),
                                    Integer.parseInt( new String( t_byte_month)) - 1,
                                    Integer.parseInt( new String( t_byte_day))
                            );

                            recordObjects[i] = calendar.getTime();
                        }
                        catch ( NumberFormatException e) {
                                                        /* campo vacío */
                            recordObjects[i] = null;
                        }

                        break;

                    case 'F':

                        try {

                            byte t_float[] = new byte[ this.header.fieldArray[i].getFieldLength()];
                            dataInputStream.read( t_float);
                            t_float = DBFUtil.trimLeftSpaces( t_float);
                            if( t_float.length > 0 && !DBFUtil.contains( t_float, (byte)'?')) {

                                recordObjects[i] = new Float( new String( t_float));
                            }
                            else {

                                recordObjects[i] = null;
                            }
                        }
                        catch( NumberFormatException e) {

                            throw new DBFException( "Ha fallado el parseo del Float: " + e.getMessage());
                        }

                        break;

                    case 'N':

                        try {

                            byte t_numeric[] = new byte[ this.header.fieldArray[i].getFieldLength()];
                            dataInputStream.read( t_numeric);
                            t_numeric = DBFUtil.trimLeftSpaces( t_numeric);

                            if( t_numeric.length > 0 && !DBFUtil.contains( t_numeric, (byte)'?')) {

                                recordObjects[i] = new Double( new String( t_numeric));
                            }
                            else {

                                recordObjects[i] = null;
                            }
                        }
                        catch( NumberFormatException e) {

                            throw new DBFException( "Ha fallado el parseo del número: " + e.getMessage());
                        }

                        break;

                    case 'L':

                        byte t_logical = dataInputStream.readByte();
                        if( t_logical == 'Y' || t_logical == 't' || t_logical == 'T' || t_logical == 't') {

                            recordObjects[i] = Boolean.TRUE;
                        }
                        else {

                            recordObjects[i] = Boolean.FALSE;
                        }
                        break;

                    case 'M':
                        //
                        recordObjects[i] = new String( "null");
                        break;

                    default:
                        recordObjects[i] = new String( "null");
                }
            }
        }
        catch( EOFException e) {

            return null;
        }
        catch( IOException e) {

            throw new DBFException( e.getMessage());
        }

        return recordObjects;
    }
}
