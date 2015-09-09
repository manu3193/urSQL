/**
 * Created by manzumbado on 9/7/15.
 */
public class ManejoPaginador {

    /**
     * Metodo que abre un paginador para realizar operaciones
     * @return Retorna la referencia al paginador de la nueva base de datos
     */
    public Paginador abrirPaginador(){
        Paginador A = new Paginador();
        return A;
    }

    /**
     * Metodo encargado de cerrar el paginador
     * @return 0 si cierra el paginador, de otra forma retorna 0
     */
    public int cerrarPaginador(){
        return 1;
    }

    /**
     * Metodo encargado de copiar la pagina al cache y devolver una referencia a esta
     * @return Retorna la referencia a la página en caché
     */
    public Pagina obtenerPagina(int numeroPagina){
        Pagina A = new Pagina();
        return A;
    }

    /**
     * Metodo encargado de habilitar la escritura en una pagina
     */
    public void escrituraPaginador(){

    }

    /**
     * Método encargado de verificar si la página requerida se encuentra en caché
     */







}
