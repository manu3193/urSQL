
import Structures.Field;
import Structures.Row;
import Structures.Table;
import java.util.ArrayList;
import ursql.systemcatalog.FetchMetadata;
import ursql.systemcatalog.WriteMetadata;

/*
 * Drop Table
 *  Elimina una tabla de un esquema
 * 
 */

/**
 *
 * @author Nicolas Jimenez
 */
public class DropTable {
   
    /**
     * 
     * @param nombreEsquema
     * @param nombreTabla 
     */
    public void dropTable( String nombreEsquema, String nombreTabla ) {
        
        if ( !verifyIntegrity( nombreEsquema, nombreTabla))
            return;
        
        deleteFromMetadata( nombreEsquema, nombreTabla );
        deleteFromDisc();
    }
    
    /**
     * 
     * @param nombreEsquema
     * @param nombreTabla
     * @return 
     */
    public boolean verifyIntegrity (  String nombreEsquema, String nombreTabla){
        
        FetchMetadata fetcher = new FetchMetadata();
        Table tablasForeign = fetcher.fetchForeignKey();
        
        ArrayList<Row> filas = tablasForeign.getRows();
        
        for ( Row fila : filas  ){
            
            ArrayList<Field> campos = fila.getColumns();
            
            String tablaOrigen = campos.get(3).getContent();
            String esquema = campos.get(4).getContent();
            if (tablaOrigen.equals(nombreTabla) &&  esquema.equals(nombreEsquema) ){
       
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     * @param nombreEsquema
     * @param nombreTabla 
     */
    public void deleteFromMetadata ( String nombreEsquema, String nombreTabla){
        
        FetchMetadata fetcher = new FetchMetadata();
        Table tablas = fetcher.fetchTables();
        Table columnas = fetcher.fetchColumns();
        Table foreign = fetcher.fetchForeignKey();
        WriteMetadata editer = new WriteMetadata();

        ArrayList<Row> filasTablas = tablas.getRows();
        
        for (Row fila: filasTablas ) {
            
            ArrayList<Field> campos = fila.getColumns();

            String esquema = campos.get(0).getContent();
            String tabla = campos.get(1).getContent();
            
            if ( esquema.equals(nombreEsquema) && tabla.equals(nombreTabla) ){
            
                editer.deleteTabla(nombreEsquema, nombreTabla);
            }
        }

        ArrayList<Row> filasColumnas = columnas.getRows();
        
        for (Row fila: filasColumnas ) {
            
            ArrayList<Field> campos = fila.getColumns();

            String esquema = campos.get(0).getContent();
            String tabla = campos.get(1).getContent();
            
            if ( esquema.equals(nombreEsquema) && tabla.equals(nombreTabla) ){
                
   //             editer.deleteColumna(columna, nombreTabla, nombreEsquema);
            }
        }
        
        ArrayList<Row> filasForeign = foreign.getRows();
        
        for (Row fila: filasForeign ) {
            
            ArrayList<Field> campos = fila.getColumns();

            String esquema = campos.get(4).getContent();
            String tabla = campos.get(2).getContent();
            
            if ( esquema.equals(nombreEsquema) && tabla.equals(nombreTabla) ){
                
         //       editer.deleteForeignKey(esquema);
            }
        }        
    }
    
    public void deleteFromDisc(){
        
    }
    
}
