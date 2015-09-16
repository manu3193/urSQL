package DataBaseFile;

/**
 * Created by manzumbado on 9/15/15.
 *
 * DBFHeader
 * Clase encargada de leer los metadatos asumiendo que el
 * InputStream brindado maneja el archivo DBF
 *
 *   autor original: anil@linuxense.com
 *   modificado por:  manzumbado el 9/15/15.
 *   license: LGPL (http://www.gnu.org/copyleft/lesser.html)
 *
*/

        import java.io.*;
        import java.util.*;

public class DBFHeader {

    static final byte SIG_DBASE_III = (byte)0x03;
        /* La estructura del DBF empieza aquí */

    byte signature;              /* 0 */
    byte year;                   /* 1 */
    byte month;                  /* 2 */
    byte day;                    /* 3 */
    int numberOfRecords;         /* 4-7 */
    short headerLength;          /* 8-9 */
    short recordLength;          /* 10-11 */
    short reserv1;               /* 12-13 */
    byte incompleteTransaction;  /* 14 */
    byte encryptionFlag;         /* 15 */
    int freeRecordThread;        /* 16-19 */
    int reserv2;                 /* 20-23 */
    int reserv3;                 /* 24-27 */
    byte mdxFlag;                /* 28 */
    byte languageDriver;         /* 29 */
    short reserv4;               /* 30-31 */
    DBFField []fieldArray;       /* each 32 bytes */
    byte terminator1;            /* n+1 */

    //byte[] databaseContainer; /* 263 bytes */
        /* La estructura DBF termina aquí */

    DBFHeader() {

        this.signature = SIG_DBASE_III;
        this.terminator1 = 0x0D;
    }

    void read( DataInput dataInput) throws IOException {

        signature = dataInput.readByte(); /* 0 */
        year = dataInput.readByte();      /* 1 */
        month = dataInput.readByte();     /* 2 */
        day = dataInput.readByte();       /* 3 */
        numberOfRecords = DBFUtil.readLittleEndianInt( dataInput); /* 4-7 */

        headerLength = DBFUtil.readLittleEndianShort( dataInput); /* 8-9 */
        recordLength = DBFUtil.readLittleEndianShort( dataInput); /* 10-11 */

        reserv1 = DBFUtil.readLittleEndianShort( dataInput);      /* 12-13 */
        incompleteTransaction = dataInput.readByte();           /* 14 */
        encryptionFlag = dataInput.readByte();                  /* 15 */
        freeRecordThread = DBFUtil.readLittleEndianInt( dataInput); /* 16-19 */
        reserv2 = dataInput.readInt();                            /* 20-23 */
        reserv3 = dataInput.readInt();                            /* 24-27 */
        mdxFlag = dataInput.readByte();                           /* 28 */
        languageDriver = dataInput.readByte();                    /* 29 */
        reserv4 = DBFUtil.readLittleEndianShort( dataInput);        /* 30-31 */

        Vector v_fields = new Vector();

        DBFField field = DBFField.createField( dataInput); /* 32 each */
        while( field != null) {

            v_fields.addElement( field);
            field = DBFField.createField( dataInput);
        }

        fieldArray = new DBFField[ v_fields.size()];

        for( int i=0; i<fieldArray.length; i++) {

            fieldArray[ i] = (DBFField)v_fields.elementAt( i);
        }
        //System.out.println( "Number of fields: " + fieldArray.length);

    }

    void write( DataOutput dataOutput) throws IOException {

        dataOutput.writeByte( signature);                       /* 0 */

        GregorianCalendar calendar = new GregorianCalendar();
        year = (byte)( calendar.get( Calendar.YEAR) - 1900);
        month = (byte)( calendar.get( Calendar.MONTH)+1);
        day = (byte)( calendar.get( Calendar.DAY_OF_MONTH));

        dataOutput.writeByte( year);  /* 1 */
        dataOutput.writeByte( month); /* 2 */
        dataOutput.writeByte( day);   /* 3 */

        //System.out.println( "Number of records in O/S: " + numberOfRecords);
        numberOfRecords = DBFUtil.littleEndian( numberOfRecords);
        dataOutput.writeInt( numberOfRecords); /* 4-7 */

        headerLength = findHeaderLength();
        dataOutput.writeShort( DBFUtil.littleEndian( headerLength)); /* 8-9 */

        recordLength = findRecordLength();
        dataOutput.writeShort( DBFUtil.littleEndian( recordLength)); /* 10-11 */

        dataOutput.writeShort( DBFUtil.littleEndian( reserv1)); /* 12-13 */
        dataOutput.writeByte( incompleteTransaction); /* 14 */
        dataOutput.writeByte( encryptionFlag); /* 15 */
        dataOutput.writeInt( DBFUtil.littleEndian( freeRecordThread));/* 16-19 */
        dataOutput.writeInt( DBFUtil.littleEndian( reserv2)); /* 20-23 */
        dataOutput.writeInt( DBFUtil.littleEndian( reserv3)); /* 24-27 */

        dataOutput.writeByte( mdxFlag); /* 28 */
        dataOutput.writeByte( languageDriver); /* 29 */
        dataOutput.writeShort( DBFUtil.littleEndian( reserv4)); /* 30-31 */

        for( int i=0; i<fieldArray.length; i++) {

            //System.out.println( "Length: " + fieldArray[i].getFieldLength());
            fieldArray[i].write( dataOutput);
        }

        dataOutput.writeByte( terminator1); /* n+1 */
    }

    private short findHeaderLength() {

        return (short)(
                1+
                        3+
                        4+
                        2+
                        2+
                        2+
                        1+
                        1+
                        4+
                        4+
                        4+
                        1+
                        1+
                        2+
                        (32*fieldArray.length)+
                        1
        );
    }

    private short findRecordLength() {

        int t_recordLength = 0;
        for( int i=0; i<fieldArray.length; i++) {

            t_recordLength += fieldArray[i].getFieldLength();
        }

        return (short)(t_recordLength + 1);
    }
}
