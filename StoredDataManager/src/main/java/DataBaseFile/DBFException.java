package DataBaseFile;

/**
 *
 *   DBFException
 *   Representa una excepcion en el manejo del archivo de la base de datos
 *
 *   autor original: anil@linuxense.com
 *   modificado por:  manzumbado el 9/15/15.
 *   license: LGPL (http://www.gnu.org/copyleft/lesser.html)
 *
*/

import java.io.IOException;

public class DBFException extends IOException {

    public DBFException() {

        super();
    }

    public DBFException( String msg) {

        super( msg);
    }
}
