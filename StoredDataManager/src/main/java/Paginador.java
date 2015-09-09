/**
 * Created by manzumbado on 9/7/15.
 */


/**
 * Cada base de datos es manejada por un solo objeto paginador independiente.
 * Este objeto se encarga de manejar y aplicar todas las operaciones de paginación
 * para accesar a los archivos de la base de datos
 */
public class Paginador {


    /**
     * Almacena el estado del paginador
     */
    private short mEstadoPag;

    /**
     * Almacena el numero de paginas de la base de datos
     */
    private int mNumPagDB;

    /**
     * Almacena el numero de paginas del archivo de la base de datos
     */
    private int mNumPagArchivoDB;

    /**
     * Almacena el tama;o de los sectores del disco
     */
    private int mSectorSize;

    /**
     * Almacena el numero de bytes sin uso al final de cada pagina
     */
    private short mReservado;

    /**
     * Referencia de la referencia a PaginadorCache
     */
    private ManejoPaginadorCache mManejoPcache;

    /**
     * Referencia
     */
    private String mDescFicheroDB;


    public Paginador() {
    }



}
