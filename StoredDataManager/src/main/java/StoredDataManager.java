/**
 * Created by manzumbado on 22/09/15.
 */


import java.io.*;
import java.util.HashMap;
import DBFile.*;

/**
 * Esta clase es la encargada de mantener las instancias de los arboles
 * que pertenecen a la base de datos
 */
public class StoredDataManager {


    private HashMap<String,ArbolBMas> mHashBtrees;
    private String mCurrentDataBase;
    private boolean isInitialized = false;

    /**
     * Constante que almacena la direccion de la carpeta en el sistema donde se almacenan las bases
     * de datos
     */
    protected static final String  DIRECTORIO_DATOS = "StoredDataManager"+File.separator+"Databases";
    protected static final String EXTENSION_ARCHIVO_TABLA =".db";
    protected static final String EXTENSION_ARCHIVO_ARBOL=".index";


    public StoredDataManager(){
    }

    /**
     * Metodo encargado de inicializar el stored Data Manager, carga las tablas (en caso de existir) al
     * hasmap de arboles
     */
    public void initStoredDataManager(String databaseName){
        try{
            setCurrentDataBase(databaseName);
            String[] currentIndexes = getCurrentIndexName();
            if(currentIndexes.length>0){
                for(int i=0; i<currentIndexes.length; i++){
                    mHashBtrees.put(currentIndexes[i], deserealizateBtree(DIRECTORIO_DATOS+File.separator+ mCurrentDataBase+File.separator+currentIndexes[i]+EXTENSION_ARCHIVO_ARBOL));
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


    /**
     * Metodo encargado de obterner los nombres de los archivos que contirnen los arboles serializados
     * @return
     * @throws IOException
     */
    private String[] getCurrentIndexName() throws IOException {
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



    public int insertIntoTable()
















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
        if(getisInitialized()){
            try{
                RandomAccessFile file= new RandomAccessFile(DIRECTORIO_DATOS+File.separator+mCurrentDataBase+File.separator+name+EXTENSION_ARCHIVO_TABLA, "rw");
                file.close();
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






    public static void main(String[] args){


        StoredDataManager storedDataManager = new StoredDataManager();
        storedDataManager.createDatabase("prueba");
        storedDataManager.setCurrentDataBase("prueba");
        storedDataManager.createTableFile("Tabla1");

        DBField[] field= new DBField[3];
        field[0]= new DBField("campo1","valor1",10);
        field[1]= new DBField("campo2","valor2",10);
        field[2]= new DBField("campo3","valor3",10);


        DBWriter writer = new DBWriter();
        writer.setTableFile(DIRECTORIO_DATOS+File.separator+storedDataManager.mCurrentDataBase+File.separator+"Tabla1"+EXTENSION_ARCHIVO_TABLA);

        long[] offsets= new long[3];

        for (int i=0; i<3;i++){
           offsets[i]= writer.writeToDBFile(field[i]);
            System.out.println("Value of offset  "+ offsets[i]);
        }
        writer.closeFile();



        storedDataManager.createTableFile("Tabla2");

        field= new DBField[3];
        field[0]= new DBField("campo1","cacabubu",10);
        field[1]= new DBField("campo2","123456778890",10);
        field[2]= new DBField("campo3","ayercomipescado",10);


        writer = new DBWriter();
        writer.setTableFile(DIRECTORIO_DATOS+File.separator+storedDataManager.mCurrentDataBase+File.separator+"Tabla2"+EXTENSION_ARCHIVO_TABLA);

        offsets= new long[3];

        for (int i=0; i<3;i++){
            offsets[i]= writer.writeToDBFile(field[i]);
            System.out.println("Value of offset  "+ offsets[i]);
        }
        writer.closeFile();




        storedDataManager.createTableFile("Tabla3");

        field= new DBField[3];
        field[0]= new DBField("campo1","laconcha",10);
        field[1]= new DBField("campo2","requeson",10);
        field[2]= new DBField("campo3","lacasadeallado",10);


         writer = new DBWriter();
        writer.setTableFile(DIRECTORIO_DATOS+File.separator+storedDataManager.mCurrentDataBase+File.separator+"Tabla3"+EXTENSION_ARCHIVO_TABLA);

        offsets= new long[3];

        for (int i=0; i<3;i++){
            offsets[i]= writer.writeToDBFile(field[i]);
            System.out.println("Value of offset  "+ offsets[i]);
        }
        writer.closeFile();

        DBReader reader = new DBReader();
        reader.setTableFile(DIRECTORIO_DATOS+File.separator+storedDataManager.mCurrentDataBase+File.separator+"Tabla3"+EXTENSION_ARCHIVO_TABLA);

        for(int i=0; i<3; i++){

            DBField field1 = reader.readFromDBFile(offsets[i]);
            System.out.println(field1.toString());

        }
        reader.closeFile();



        //StoredDataManager sdm = new StoredDataManager();
        //sdm.initStoredDataManager("prueba");




    }


}

