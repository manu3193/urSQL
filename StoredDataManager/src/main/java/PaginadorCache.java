/**
 * Created by manzumbado on 9/7/15.
 */
public class PaginadorCache {
    
    /**
     * Atributo que describe la cantidad total de referencias a páginas
     */
    private int mNumRef;
    
    
    /**
     * Atributo que describe el tamaño máximo de la caché
     */
    private int mCacheSize;
    
    /**
     * Atributo que determina el tamaño del slot de los datos de la página 
     * en el hash
     */
    private int mPaginaSize;
    
    /**
     * Atributo que determina el tamaño extra para la página en caché
     */
    private int mPaginaExtraSize;
    
    /**
     * Atributo que mantiene una referencia al primer elemento de la
     * lista de páginas modificadas en memoria
     */    
    private EncabezadoPagina mDirty;
    
    /**
     * Atributo que mantiene una referencia al ultimo elemento de la
     * lista de encabezados de paginas modificadas en memoria
     */
    private EncabezadoPagina mDirtyFinal;
    
    /**
     * Atributo que mantiene una referencia al encabezado de la última página
     * sincronizada a disco
     */
    private EncabezadoPagina mSincronizado;
    
   
    
    
}
