package DataBaseFile;

/**
 * DBFField
 *      Esta clase representa un campo o una columna en el archivo
 *
 *   autor original: anil@linuxense.com
 *   modificado por:  manzumbado el 9/15/15.
 *   license: LGPL (http://www.gnu.org/copyleft/lesser.html)
 *
*/

import java.io.*;

/**
 DBFField representa la especificacion de un campo o columna en el archivo de la base de datos

 Los objetos DBFField son creados y a;adidos a un objeto DBFWrite o bien obtenidos
 desde los objetos DBFReader por medio del m'etodo getField(int)

 */
public class DBFField {

    public static final byte FIELD_TYPE_C = (byte)'C';
    public static final byte FIELD_TYPE_L = (byte)'L';
    public static final byte FIELD_TYPE_N = (byte)'N';
    public static final byte FIELD_TYPE_F = (byte)'F';
    public static final byte FIELD_TYPE_D = (byte)'D';
    public static final byte FIELD_TYPE_M = (byte)'M';

    /* Field struct variables start here */
    byte[] fieldName = new byte[ 11]; /* 0-10*/
    byte dataType;                    /* 11 */
    int reserv1;                      /* 12-15 */
    int fieldLength;                 /* 16 */
    byte decimalCount;                /* 17 */
    short reserv2;                    /* 18-19 */
    byte workAreaId;                  /* 20 */
    short reserv3;                    /* 21-22 */
    byte setFieldsFlag;               /* 23 */
    byte[] reserv4 = new byte[ 7];    /* 24-30 */
    byte indexFieldFlag;              /* 31 */
        /* Field struct variables end here */

    /* other class variables */
    int nameNullIndex = 0;

    /**
     * Se crea un objeto DBFField desde los datos le'idos por el DataInputStream dado
     *
     * El dato en el objeto DataInputStream se supone que est'a organizado de manera correcta
     * y el "puntero" del stream se supone que est'a posicionado correctamente

     @param in DataInputStream
     @return Retorna el objeto creado DBFField.
     @throws IOException Si ocurren problemas en la lectura.
     */
    protected static DBFField createField( DataInput in)
            throws IOException {

        DBFField field = new DBFField();

        byte t_byte = in.readByte(); /* 0 */
        if( t_byte == (byte)0x0d) {

            //System.out.println( "End of header found");
            return null;
        }

        in.readFully( field.fieldName, 1, 10);  /* 1-10 */
        field.fieldName[0] = t_byte;

        for( int i=0; i<field.fieldName.length; i++) {

            if( field.fieldName[ i] == (byte)0) {

                field.nameNullIndex = i;
                break;
            }
        }

        field.dataType = in.readByte(); /* 11 */
        field.reserv1 = DBFUtil.readLittleEndianInt( in); /* 12-15 */
        field.fieldLength = in.readUnsignedByte();  /* 16 */
        field.decimalCount = in.readByte(); /* 17 */
        field.reserv2 = DBFUtil.readLittleEndianShort( in); /* 18-19 */
        field.workAreaId = in.readByte(); /* 20 */
        field.reserv2 = DBFUtil.readLittleEndianShort( in); /* 21-22 */
        field.setFieldsFlag = in.readByte(); /* 23 */
        in.readFully( field.reserv4); /* 24-30 */
        field.indexFieldFlag = in.readByte(); /* 31 */

        return field;
    }

    /**
     * Escribe el contenido del objeto DBField en el stream seg'un
     * la especificacion del formato BDF.

     @param out OutputStream
     @throws IOException Si ocurren problemas relacionados con el stream.
     */
    protected void write( DataOutput out)
            throws IOException {

        //DataOutputStream out = new DataOutputStream( os);

        // Field Name
        out.write( fieldName);        /* 0-10 */
        out.write( new byte[ 11 - fieldName.length]);

        // data type
        out.writeByte(dataType); /* 11 */
        out.writeInt( 0x00);   /* 12-15 */
        out.writeByte( fieldLength); /* 16 */
        out.writeByte( decimalCount); /* 17 */
        out.writeShort( (short)0x00); /* 18-19 */
        out.writeByte( (byte)0x00); /* 20 */
        out.writeShort( (short)0x00); /* 21-22 */
        out.writeByte( (byte)0x00); /* 23 */
        out.write( new byte[7]); /* 24-30*/
        out.writeByte( (byte)0x00); /* 31 */
    }

    /**
     Retorna el nombre del campo o columna.

     @return Nombre del campo como un String.
     */
    public String getName() {

        return new String( this.fieldName, 0, nameNullIndex);
    }

    /**
     Retorna el tipo de dato del campo o columna.

     @return Tipo de dato como byte
     */
    public byte getDataType() {

        return dataType;
    }

    /**
     Returns field length.

     @return field length as int.
     */
    public int getFieldLength() {

        return fieldLength;
    }

    /**
     * Retorna la parte decimal. Esto es aplicable solamente
     * si el tipo de campo o columna es num'erico
     *
     * Si el campo es especificado para mantener valores integrales
     * el valor retornado por este m'etodo ser'a cero.
     *
     * @return El tama;o del campo decimal como un int.
     */
    public int getDecimalCount() {

        return decimalCount;
    }

    // Setter methods

    // byte[] fieldName = new byte[ 11]; /* 0-10*/
    // byte dataType;                    /* 11 */
    // int reserv1;                      /* 12-15 */
    // byte fieldLength;                 /* 16 */
    // byte decimalCount;                /* 17 */
    // short reserv2;                    /* 18-19 */
    // byte workAreaId;                  /* 20 */
    // short reserv3;                    /* 21-22 */
    // byte setFieldsFlag;               /* 23 */
    // byte[] reserv4 = new byte[ 7];    /* 24-30 */
    // byte indexFieldFlag;              /* 31 */

    /**
     * @deprecated Este m'etodo est'a obsoleto y es reemplazado por {@link #setName( String)}.
     */
    public void setFieldName( String value) {

        setName( value);
    }

    /**
     Setea el nombre del campo como un String
     */
    public void setName( String value) {

        if( value == null) {

            throw new IllegalArgumentException( "El nombre del campo no puede ser nulo");
        }

        if( value.length() == 0 || value.length() > 10) {

            throw new IllegalArgumentException( "El nombre del campo d ebe tener un valor de ente 0 y 10 caracteres");
        }

        this.fieldName = value.getBytes();
        this.nameNullIndex = this.fieldName.length;
    }

    /**
     Setea el tipo de dato del campo

     @param tipo de dato, uno de los siguientes:<br>
     C, L, N, F, D, M
     */
    public void setDataType( byte tipo) {

        switch( tipo) {

            case 'D':
                this.fieldLength = 8; /* fall through */
            case 'C':
            case 'L':
            case 'N':
            case 'F':
            case 'M':

                this.dataType = tipo;
                break;

            default:
                throw new IllegalArgumentException( "Tipo de dato desconocido");
        }
    }

    /**
     Tamaño del campo
     Este método debe ser llamado antes de llamar a setDecimalCount().

     @param Tamaño del campo como un entero.
     */
    public void setFieldLength( int Tamaño) {

        if( Tamaño <= 0) {

            throw new IllegalArgumentException( "El tamaño del campo debe ser un entero positivo");
        }

        if( this.dataType == FIELD_TYPE_D) {

            throw new UnsupportedOperationException( "No procede en un campo tipo Date");
        }

        fieldLength = Tamaño;
    }

    /**
     * Establece el tamaño de decimales del campo
     * Antes de llamar este método se debe setear el tamaño
     * del campo por medio del método setFieldLenght().
     *
     * @param Tamaño del campo decimal.
     */
    public void setDecimalCount( int Tamaño) {

        if( Tamaño < 0) {

            throw new IllegalArgumentException( "El tamaño del decimal debe ser un número positivo");
        }

        if( Tamaño > fieldLength) {

            throw new IllegalArgumentException( "El tamaño del decimal debe ser menor al tamaño del campo");
        }

        decimalCount = (byte)Tamaño;
    }

}
