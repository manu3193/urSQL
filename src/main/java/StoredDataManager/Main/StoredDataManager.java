package StoredDataManager.Main; /**
 * Created by manzumbado on 22/09/15.
 */


import StoredDataManager.BPlusTree.ArbolBMas;
import StoredDataManager.DBFile.DBField;
import StoredDataManager.DBFile.DBWriter;
import Shared.Structures.Field;
import Shared.Structures.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * Esta clase es la encargada de mantener las instancias de los arboles
 * que pertenecen a la base de datos
 */
public class StoredDataManager {


    private HashMap<String, ArbolBMas> mHashBtrees;
    private String mCurrentDataBase;
    private boolean isInitialized = false;
    //private CacheModule=null;

    /**
     * Constante que almacena la direccion de la carpeta en el sistema donde se almacenan las bases
     * de datos
     */
    protected static final String  DIRECTORIO_DATOS = "Databases";
    protected static final String EXTENSION_ARCHIVO_TABLA =".db";
    protected static final String EXTENSION_ARCHIVO_ARBOL=".tree";
    protected static final String EXTENSION_ARCHIVO_INDICE=".index";


    public StoredDataManager(){
        mHashBtrees= new HashMap<String, ArbolBMas>();
    }

    /**
     * Metodo encargado de inicializar el stored Data Manager, carga las tablas (en caso de existir) al
     * hasmap de arboles
     */
    public void initStoredDataManager(String databaseName){
        try{
            setCurrentDataBase(databaseName);
            String[] currentIndexes = getCurrentTreeName();
            if(currentIndexes!=null){
                if(currentIndexes.length>0){
                    for(int i=0; i<currentIndexes.length; i++){
                        mHashBtrees.put(currentIndexes[i], deserealizateBtree(DIRECTORIO_DATOS+File.separator+ mCurrentDataBase+File.separator+currentIndexes[i]+EXTENSION_ARCHIVO_ARBOL));
                    }
                }
            }
             setIsInitialized(true);
        }catch (IOException e){
            System.err.println("Error al inicializar StoredDataManager: " + e.getMessage());
        }
    }

    /**
     * Metodo encargado de establecer la carpeta de bases de datos en la que realiza acciones el StoredDataManager
     * @param currentDataBase
     */
    public void setCurrentDataBase(String currentDataBase){
        this.mCurrentDataBase=currentDataBase;
    }

    public String getmCurrentDataBase() {
        return mCurrentDataBase;
    }

    /**
     * Metodo encargado de obterner los nombres de los archivos que contirnen los arboles serializados
     * @return
     * @throws IOException
     */
    private String[] getCurrentTreeName() throws IOException {
        File directorio = new File(DIRECTORIO_DATOS + File.separator + mCurrentDataBase);
        return directorio.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                if (name.toLowerCase().endsWith(EXTENSION_ARCHIVO_ARBOL)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    /**
     * Metodo encargado de crear el directorio de la base de datos requerida segun el nombre indicado
     *
     * @return 1 si logra crear el directorio, de otra manera retorna el codigo de error respectivo
     */
    public int createDatabase(String nombre){
        int result;
        File directorio = new File(DIRECTORIO_DATOS+File.separator+nombre);

        if(directorio.exists()){
            result= -1;
        }
        else {
            if (directorio.mkdir()) {
                result= 1;
            }else{
                System.err.println("Error al crear base de datos ");
                result= -1;
            }

    }return result;
    }


    /**
     * Metodo encargado de insertar una fila en el sistema de archivos.
     *
     * @param row
     * @return
     */
    public int insertIntoTable(Row row){
        int result=-1;
        if(getisInitialized()){
            String targetTable= row.getTableName();
            ArrayList<Field> fields = row.getColumns();
            DBField dbField;
            ArbolBMas Btree;
            LinkedHashMap<String,Long> keyHash;
            String rowPKValue=null;
            long rowPKIndex;
            long lastRowPKIndex;
            String valueToInsert;
            try{
                DBWriter writer= new DBWriter();
                writer.setTableFile(DIRECTORIO_DATOS + File.separator + getmCurrentDataBase() + File.separator + targetTable + EXTENSION_ARCHIVO_TABLA);
                keyHash= deserealizateIndex(DIRECTORIO_DATOS + File.separator + getmCurrentDataBase() + File.separator + targetTable + EXTENSION_ARCHIVO_INDICE);
                int keyHashSize=keyHash.size();
                if(keyHashSize>0){
                    lastRowPKIndex=keyHashSize;
                }else{
                    lastRowPKIndex=0;
                }
                long[] offsets= new long[fields.size()-1];
                if(this.mHashBtrees.containsKey(targetTable)){
                    Btree=this.mHashBtrees.get(targetTable);
                }else{
                    System.err.println("Error al ingresar datos, la tabla no existe");
                    return -1;
                }
                for(int i=0; i<fields.size();i++){
                    if(fields.get(i).isPrimaryKey()){
                        rowPKValue=fields.get(i).getContent();
                        keyHash.put(rowPKValue,lastRowPKIndex);
                        lastRowPKIndex++;
                    }else{
                        valueToInsert= fields.get(i).getContent();
                        dbField= new DBField(valueToInsert, valueToInsert.getBytes().length);
                        offsets[i-1]= writer.writeToDBFile(dbField);
                    }
                }
                writer.closeFile();
                Btree.insertar(keyHash.get(rowPKValue),offsets);
                serializateIndex(keyHash,DIRECTORIO_DATOS + File.separator + getmCurrentDataBase() + File.separator + targetTable + EXTENSION_ARCHIVO_INDICE);
                result= 1;
            }catch(Exception ex){
                System.err.println("Ha ocurrido un problema al ingresar datos, error: " +ex.getMessage());
                result= -1;
            }
        }
        return result;
    }

    public int dropTable(String name) {
        int result=0;
        try{
            if (isInitialized){
                File archivoArbol = new File(DIRECTORIO_DATOS+File.separator+getmCurrentDataBase()+File.separator+name+EXTENSION_ARCHIVO_ARBOL);
                File archivoDatos = new File(DIRECTORIO_DATOS+File.separator+getmCurrentDataBase()+File.separator+name+EXTENSION_ARCHIVO_TABLA;
                File archivoIndex = new File(DIRECTORIO_DATOS+File.separator+getmCurrentDataBase()+File.separator+name+EXTENSION_ARCHIVO_INDICE);
                if(archivoArbol.delete()){
                    result= 1;
                }
                if(archivoDatos.delete()){
                    result=1;
                }if(archivoIndex.delete()) {
                    result = 1;
                }
            }
        }catch(IOException e){
            System.err.print("Hubo un problema al eliminar la tabla, error: "+ e.getMessage());
            result=-1;
        }
        return result;
    }



    public int dropDatabase(String name){
        int result;
        if(!name.equals(this.getmCurrentDataBase())){
            File directorio = new File(DIRECTORIO_DATOS+File.separator+name);

            if(directorio.exists()){
                directorio.delete();
                result=1;
            }
            else {
                System.err.println("Error al crear base de datos ");
                result= -1;
            }
        }else{
            System.err.println("Error al crear base de datos ");
            result= -1;
        }
        return result;
    }


    private int serializateBtree(ArbolBMas Btree, String pathFile){
        try{
            FileOutputStream outputFile= new FileOutputStream(pathFile);
            ObjectOutputStream outputStream = new ObjectOutputStream(outputFile);
            outputStream.writeObject(Btree);
            outputStream.close();
            outputFile.close();
            return 1;
        } catch(IOException e){
            System.err.println("No se ha podido guardar el arbol en disco, error: " + e.getMessage());
            return -1;
        }
    }

    private ArbolBMas deserealizateBtree(String filepath){
        ArbolBMas deserializedBtree=null;
        try{
            FileInputStream inputFile= new FileInputStream(filepath);
            ObjectInputStream inputStream = new ObjectInputStream(inputFile);
            deserializedBtree= (ArbolBMas) inputStream.readObject();
            inputStream.close();
            inputFile.close();
        } catch (IOException e) {
            System.err.println("No se ha podido deserealizar el arbol, error: "+ e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return deserializedBtree;
    }

    /**
     *
     * @param hashkey
     * @param pathfile
     * @return
     */
    private int serializateIndex(LinkedHashMap<String,Long> hashkey, String pathfile){
        try{
            FileOutputStream outputFile= new FileOutputStream(pathfile);
            ObjectOutputStream outputStream = new ObjectOutputStream(outputFile);
            outputStream.writeObject(hashkey);
            outputStream.close();
            outputFile.close();
            return 1;
        } catch(IOException e){
            System.err.println("No se ha podido guardar el 'indice en disco, error: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Metodo encargado de deserializar un indice almacenado en disco, segun el nombre
     * @param filepath ubicacion y nombre del archivo
     * @return Carga el hash que almacena el indice
     */
    private LinkedHashMap<String,Long> deserealizateIndex(String filepath){
        LinkedHashMap<String,Long> deserializedBtree=null;
        try{
            FileInputStream inputFile= new FileInputStream(filepath);
            ObjectInputStream inputStream = new ObjectInputStream(inputFile);
            deserializedBtree= (LinkedHashMap<String,Long>) inputStream.readObject();
            inputStream.close();
            inputFile.close();
        } catch (IOException e) {
            System.err.println("No se ha podido deserealizar el indice, error: "+ e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return deserializedBtree;
    }

    /**
     * Metodo encargado de escribir en disco el contenido de los arboles
     * @return
     */
    public int flushToDisk(){
        try{
            File[] listaTablas= getNombreTablas();
            for(int i=0; i<listaTablas.length;i++){
                serializateBtree(listaTablas[i], DIRECTORIO_DATOS + File.separator + getmCurrentDataBase() + File.separator + listaTablas[i].getName() + EXTENSION_ARCHIVO_ARBOL);
            }
            return 1;
        } catch(IOException e){
            System.err.println("No se ha podido guardar el arbol en disco, error: " + e.getMessage());
            return -1;
        }
    }




    /**
     * Metodo encargado de obtener los nombres de las tablas para el directorio actual de la base de datos
     * @return Arreglo de objetos File que representan las tablas existentes
     */
    private File[] getNombreTablas() throws IOException{
            File directorio = new File(DIRECTORIO_DATOS + File.separator + mCurrentDataBase);
            File[] listaTablas = directorio.listFiles();
            return listaTablas;
        }


    /**
     * Metodo encargado de crear un archivo en blanco que almacena los campos de la tabla
     */

    public int createTableFile(String name){
        int result = 0;
        ArbolBMas Btree= new ArbolBMas();
        LinkedHashMap<String,Long> hashKeys= new LinkedHashMap<String, Long>();
        if(getisInitialized()){
            try{
                RandomAccessFile file= new RandomAccessFile(DIRECTORIO_DATOS+File.separator+mCurrentDataBase+File.separator+name+EXTENSION_ARCHIVO_TABLA, "rw");
                file.close();
                Btree.setNombreArbol(name);
                this.mHashBtrees.put(name,Btree);
                serializateIndex(hashKeys,DIRECTORIO_DATOS+File.separator+mCurrentDataBase+File.separator+name+EXTENSION_ARCHIVO_INDICE);
                result=1;
            } catch (IOException exc){
                System.err.println("No se ha podido crear la tabla "+name+", error: "+ exc.getMessage());
                result =-1;
            }
        }else
        result = -1;
    return result;
    }


    public boolean getisInitialized() {
        return this.isInitialized;
    }

    public void setIsInitialized(boolean isInitialized) {
        this.isInitialized = isInitialized;
    }

}

