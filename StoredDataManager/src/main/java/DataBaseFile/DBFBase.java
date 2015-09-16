package DataBaseFile;

/**
 *
 * Esta clase se toma como base para la clase DBFReader y DBFWriter.
 * Se define la codificacion de caracteres latinos
 *
 *   autor original: anil@linuxense.com
 *   modificado por:  manzumbado el 9/15/15.
 *   license: LGPL (http://www.gnu.org/copyleft/lesser.html)
 *
 */
public abstract class DBFBase {

    protected final String characterSetName="8859_1";
    protected final int END_OF_DATA=0x1A;

    public String getCharactersetName() {

        return this.characterSetName;
    }


}
