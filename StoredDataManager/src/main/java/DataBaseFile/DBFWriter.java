package DataBaseFile;

/**
 *
 * DBFWriter
 *
 * Clase encargada de definir la estructura del archivo y añadir los datos a esa
 * estructura para finalmente escribir en disco
 *
 * autor original: anil@linuxense.com
 * Modificado por manzumbado on 9/15/15.
 * license: LGPL (http://www.gnu.org/copyleft/lesser.html)
 *
 *
*/

import java.io.*;
import java.util.*;

/**
 Un objeto de esta clase puede crear archivos DBF

 Crea un objeto <br>
 luego define los campos creando los objetos DBFField<br>
 los agrega al objeto DBFWritter<br>
 añade los datos con el método addRecords()<br>
 llama al método write()
 */
public class DBFWriter extends DBFBase {

    /* other class variables */
    DBFHeader header;
    Vector v_records = new Vector();
    int recordCount = 0;
    RandomAccessFile raf = null; /* Open and append records to an existing DBF */
    boolean appendMode = false;

    /**
     Crea un objeto vacío
     */
    public DBFWriter() {

        this.header = new DBFHeader();
    }

    /**
     Crea un objeto DBFWriter que puede pegar datos en el archivo existente
     @param dbfFile El archivo debe ser un archivo DBF válido
     @exception DBFException  si el archivo no existe, no es válido o bien cuando hay problemas de E/L.
     */
    public DBFWriter( File dbfFile)
            throws DBFException {

        try {

            this.raf = new RandomAccessFile( dbfFile, "rw");

                        /* Antes de proceder verifica que el archivo sea vàlido
                         */
            if( !dbfFile.exists() || dbfFile.length() == 0) {

                this.header = new DBFHeader();
                return;
            }

            header = new DBFHeader();
            this.header.read( raf);

                        /* puntero de posición de archivo del raf */
            this.raf.seek( this.raf.length()-1 /* para ignorar el byte END_OF_DATA en EoF */);
        }
        catch( FileNotFoundException e) {

            throw new DBFException( "El archivo no se encontró " + e.getMessage());
        }
        catch( IOException e) {

            throw new DBFException( e.getMessage() + " mientras se leía el archivo.");
        }

        this.recordCount = this.header.numberOfRecords;
    }

    /**
     Setea los campos.
     */
    public void setFields( DBFField[] fields)
            throws DBFException {

        if( this.header.fieldArray != null) {

            throw new DBFException( "Los campos ya han sido definidos");
        }

        if( fields == null || fields.length == 0) {

            throw new DBFException( "Debería existir al menos un campo");
        }

        for( int i=0; i<fields.length; i++) {

            if( fields[i] == null) {

                throw new DBFException( "Campo " + (i+1) + " es nulo");
            }
        }

        this.header.fieldArray = fields;

        try {

            if( this.raf != null && this.raf.length() == 0) {

                                /*
                                Al ser un archivo nuevo debe escribir el encabezado
                                */
                this.header.write( this.raf);
            }
        }
        catch( IOException e) {

            throw new DBFException( "Error al accesar al archivo");
        }
    }

    /**
     Añade un registro.
     */
    public void addRecord( Object[] values)
            throws DBFException {

        if( this.header.fieldArray == null) {

            throw new DBFException( "Los campos deben ser definidos antes de añadir registros");
        }

        if( values == null) {

            throw new DBFException( "No se permite añadir una fila nula");
        }

        if( values.length != this.header.fieldArray.length) {

            throw new DBFException( "Registro in válido. Inválido número de registros en la fila");
        }

        for( int i=0; i<this.header.fieldArray.length; i++) {

            if( values[i] == null) {

                continue;
            }

            switch( this.header.fieldArray[i].getDataType()) {

                case 'C':
                    if( !(values[i] instanceof String)) {
                        throw new DBFException( "Valor inválido para el campo " + i);
                    }
                    break;

                case 'L':
                    if( !( values[i] instanceof Boolean)) {
                        throw new DBFException( "Valor inválido para el campo" + i);
                    }
                    break;

                case 'N':
                    if( !( values[i] instanceof Double)) {
                        throw new DBFException( "Valor inválido para el campo " + i);
                    }
                    break;

                case 'D':
                    if( !( values[i] instanceof Date)) {
                        throw new DBFException( "Valor inválido para el campo " + i);
                    }
                    break;

                case 'F':
                    if( !(values[i] instanceof Double)) {

                        throw new DBFException( "Valor inválido para el campo " + i);
                    }
                    break;
            }
        }

        if( this.raf == null) {

            v_records.addElement( values);
        }
        else {

            try {

                writeRecord( this.raf, values);
                this.recordCount++;
            }
            catch( IOException e) {

                throw new DBFException( "Ha ocurrido un error al intentar escribir el registro " + e.getMessage());
            }
        }
    }

    /**
     Escribe los datos definidos al OutputStream
     */
    public void write( OutputStream out)
            throws DBFException {

        try {

            if( this.raf == null) {

                DataOutputStream outStream = new DataOutputStream( out);

                this.header.numberOfRecords = v_records.size();
                this.header.write( outStream);

                                /* Añade todos los registros */
                int t_recCount = v_records.size();
                for( int i=0; i<t_recCount; i++) { /* itera sobre los registros */

                    Object[] t_values = (Object[])v_records.elementAt( i);

                    writeRecord( outStream, t_values);
                }

                outStream.write( END_OF_DATA);
                outStream.flush();
            }
            else {

                                /* se logró escribir. Se procede a actualizar el encabezado con la cantidad de registros y END_OF_DATA  */
                this.header.numberOfRecords = this.recordCount;
                this.raf.seek( 0);
                this.header.write( this.raf);
                this.raf.seek( raf.length());
                this.raf.writeByte( END_OF_DATA);
                this.raf.close();
            }

        }
        catch( IOException e) {

            throw new DBFException( e.getMessage());
        }
    }

    public void write()
            throws DBFException {

        this.write( null);
    }

    private void writeRecord( DataOutput dataOutput, Object []objectArray)
            throws IOException {

        dataOutput.write( (byte)' ');
        for( int j=0; j<this.header.fieldArray.length; j++) { /* itera sobre los campos */

            switch( this.header.fieldArray[j].getDataType()) {

                case 'C':
                    if( objectArray[j] != null) {

                        String str_value = objectArray[j].toString();
                        dataOutput.write( DBFUtil.textPadding( str_value, characterSetName, this.header.fieldArray[j].getFieldLength()));
                    }
                    else {

                        dataOutput.write( DBFUtil.textPadding( "", this.characterSetName, this.header.fieldArray[j].getFieldLength()));
                    }

                    break;

                case 'D':
                    if( objectArray[j] != null) {

                        GregorianCalendar calendar = new GregorianCalendar();
                        calendar.setTime( (Date)objectArray[j]);
                        dataOutput.write( String.valueOf( calendar.get( Calendar.YEAR)).getBytes());
                        dataOutput.write( DBFUtil.textPadding( String.valueOf( calendar.get( Calendar.MONTH)+1), this.characterSetName, 2, DBFUtil.ALIGN_RIGHT, (byte)'0'));
                        dataOutput.write( DBFUtil.textPadding( String.valueOf( calendar.get( Calendar.DAY_OF_MONTH)), this.characterSetName, 2, DBFUtil.ALIGN_RIGHT, (byte)'0'));
                    }
                    else {

                        dataOutput.write( "        ".getBytes());
                    }

                    break;

                case 'F':

                    if( objectArray[j] != null) {

                        dataOutput.write( DBFUtil.doubleFormating( (Double)objectArray[j], this.characterSetName, this.header.fieldArray[j].getFieldLength(), this.header.fieldArray[j].getDecimalCount()));
                    }
                    else {

                        dataOutput.write( DBFUtil.textPadding( " ", this.characterSetName, this.header.fieldArray[j].getFieldLength(), DBFUtil.ALIGN_RIGHT));
                    }

                    break;

                case 'N':

                    if( objectArray[j] != null) {

                        dataOutput.write(
                                DBFUtil.doubleFormating( (Double)objectArray[j], this.characterSetName, this.header.fieldArray[j].getFieldLength(), this.header.fieldArray[j].getDecimalCount()));
                    }
                    else {

                        dataOutput.write(
                                DBFUtil.textPadding( " ", this.characterSetName, this.header.fieldArray[j].getFieldLength(), DBFUtil.ALIGN_RIGHT));
                    }

                    break;
                case 'L':

                    if( objectArray[j] != null) {

                        if( (Boolean)objectArray[j] == Boolean.TRUE) {

                            dataOutput.write( (byte)'T');
                        }
                        else {

                            dataOutput.write((byte)'F');
                        }
                    }
                    else {

                        dataOutput.write( (byte)'?');
                    }

                    break;

                case 'M':

                    break;

                default:
                    throw new DBFException( "Tipo de campo desconocido " + this.header.fieldArray[j].getDataType());
            }
        }
    }
}
