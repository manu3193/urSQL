/**
 * Created by manzumbado on 9/7/15.
 */
public final class ConstantesPaginador {


    /**
     * Constante que describe el estado de una pagina abierta
     */
    public static final int PAGINA_ABIERTA=0;

    /**
     * Constante que describe el estado de una pagina en modo lectura
     */
    public static final int PAGINA_LECTURA=1;

    /**
     * Constante que describe el estado de una pagina en modo escritura en cache
     */
    public static final int PAGINA_ESCRITURA_CACHE=2;

    /**
     * Constante que describe el estado de una pagina en modo escritura en disco
     */
    public static final int PAGINA_ESCRITURA_DISCO=3;

    /**
     * Constante que describe el estado de una pagina en modo escritura finalizada
     */
    public static final int PAGINA_ESCRITURA_FINALIZADA=4;

    /**
     * Constante que describe el estado de una pagina cuando se da un error de lectura o escritura
     */
    public static final int PAGINA_ERROR=5;
}
