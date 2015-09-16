
import java.util.ArrayList;

/*
 * Clase que se encarga de realizar el plan de ejecucion.
 * Lo guarda por ahora con WRFiles.
 */
/**
 *
 * @author nicolasjimenez
 */
public class PlanEjecuccion {

    private final String comando;
    private final ArrayList<String> instruccion;
    private final ArrayList<String> plan;

    public PlanEjecuccion(String comando, ArrayList<String> instruccion) {

        this.comando = comando;
        this.instruccion = instruccion;
        this.plan = new ArrayList<>();
    }

    /**
     * Procesa segun el tipo de comando introducido.
     *
     * @return
     */
    public ArrayList<String> procesar() {

        switch (comando) {

            case "createDatabase":

                createDatabase();
                break;

            case "dropDatabase":

                dropDatabase();
                break;

            case "listDatabase":

                listDatabases();
                break;

            case "stop":

                stop();
                break;

            case "start":

                start();
                break;

            case "getStatus":

                getStatus();
                break;

            case "display":

                display();
                break;

            case "set":

                set();
                break;

            case "createTable":

                createTable();
                break;

            case "alter":

                alter();
                break;

            case "dropTable":

                dropTable();
                break;

            case "createIndex":

                createIndex();
                break;

            case "select":

                select();
                break;

            case "update":

                update();
                break;

            case "delete":

                delete();
                break;

            case "insert":

                insert();
                break;

            default:
                System.out.println("Error. This is odd, please explain me what's going on.");
                return null;
        }
        System.out.println("Error");
        return null;
    }

    /**
     * @return
     */
    private ArrayList<String> createDatabase() {

        String line1 = "Se analiza que la instruccion de create database este bien formulada.";
        String line2 = "Se crea una carpeta en el sistema de archivos para guardar las tablas de la base de datos: "
                + instruccion.get(2) + ".";
        String line3 = "Se guarda la informacion de la base de datos la metadata del urSQL.";
        plan.add(line1);
        plan.add(line2);
        plan.add(line3);
        return plan;
    }

    /**
     * @return
     */
    private ArrayList<String> dropDatabase() {

        String line1 = "Se analiza que la instruccion de drop database este bien formulada.";
        String line2 = "Se elimina la  carpeta en el sistema de archivos que almacena las tablase de la base de datos: "
                + instruccion.get(2) + ".";
        String line3 = "Se elimina la iinformacion relacionada de " + instruccion.get(2) + " de la metadata.";
        plan.add(line1);
        plan.add(line2);
        plan.add(line3);
        return plan;
    }

    /**
     * @return
     */
    private ArrayList<String> listDatabases() {

        String line1 = "Se analiza que la instruccion de list databases este bien formulada";
        String line2 = "Se busca en la metadata las bases de datos creadas";
        String line3 = "Con esa información se genera un listado de todos los esquemas existentes. ";
        plan.add(line1);
        plan.add(line2);
        plan.add(line3);
        return plan;
    }

    /**
     * Wish I know.
     */
    private ArrayList<String> stop() {

    }

    /**
     * @return
     */
    private ArrayList<String> getStatus() {

    }

    /**
     * @return
     */
    private ArrayList<String> display() {

        String line1 = "Se analiza que la instruccion de display database este bien formulada";
        String line2 = "Se busca en la metadata del System Catalog la informacion sobre la base de datos: "
                + instruccion.get(2);
        String line3 = "Con base en la informacion obtenida se muestra de alguna forma las tablas y columnas del "
                + "esquema " + instruccion.get(2);
        plan.add(line1);
        plan.add(line2);
        plan.add(line3);
        return plan;
    }

    /**
     *
     */
    private ArrayList<String> set() {

        String line1 = "Se analiza que la instruccion de set este bien formulada";
        String line2 = "Se busca en la metadata del System Catalog la informacion sobre la base de datos: "
                + instruccion.get(2);
        String line3 = "Con base en la informacion obtenida se muestra de alguna forma las tablas y columnas del "
                + "esquema " + instruccion.get(2);
        plan.add(line1);
        plan.add(line2);
        plan.add(line3);
        return plan;
    }

    /**
     * @return
     */
    private ArrayList<String> createTable() {

        String line1 = "Se analiza que la instruccion de create table este bien formulada";
        String line2 = "Se procede a crear una tabla en el esquema establecido anteriormente para esto se extrae "
                + "informacion de la instruccion como las columnas, la llave primaria y las restricciones y se guarda en la metadata";
        String line3 = "Tambien se crea un arbol en donde se almacena toda la informacion de la tabla "
                + instruccion.get(2);
        plan.add(line1);
        plan.add(line2);
        plan.add(line3);
        return plan;
    }

    /**
     * @return
     */
    private ArrayList<String> alter() {
        String line1 = "Se analiza que la instruccion de alter  este bien formulada";
        String line2 = "Se busca en la metadata del System Catalog la informacion sobre la base de datos: "
                + instruccion.get(2);
        String line3 = "Con base en la informacion obtenida se agrega el constraint de una llave foranea en la metadata"
                + " de la tabla " + instruccion.get(2);
        plan.add(line3);
        return plan;
    }

    /**
     * @return
     */
    private ArrayList<String> createIndex() {

        String line1 = "Se analiza que la instruccion de create index este bien formulada";
        String line2 = "Se busca en la metadata del System Catalog la informacion sobre la tabla: "
                + instruccion.get(4) + ". Tambien se crea un nuevo HASH sobre esa tabla ";
        String line3 = "Finalmente se guarda con el Stored Data Manager el nuevo indice y tambien en el System Manager "
                + " para realizar las busquedas sobre la columna  " + instruccion.get(6);
        plan.add(line1);
        plan.add(line2);
        plan.add(line3);
        return plan;
    }

    /**
     * @return
     */
    private ArrayList<String> select() {

        String line1 = "Se analiza que la instruccion de select  este bien formulada";
        String line2 = "Se busca en la metadata del System Catalog la informacion sobre la tabla y/o conjunto de tablas"
                + " con join, tambien se revisan las condiciones";
        String line3 = "Luego se revisa toda la informacion necesaria en el arbol B+ gracias al Stored Data Manager "
                + "para cumplir con las condiciones";
        String line4 = "Se ejecuta la creacion de un xml o de un json dependiendo del caso";
        plan.add(line1);
        plan.add(line2);
        plan.add(line3);
        plan.add(line4);
        return plan;
    }

    /**
     * @return
     */
    private ArrayList<String> update() {

        String line1 = "Se analiza que la instruccion de update este bien formulada";
        String line2 = "Se busca en la metadata del System Catalog la informacion sobre la tabla: "
                + instruccion.get(1) + ". Se busca la columna especificada " + instruccion.get(3);
        String line3 = "Dependiendo de la declaracion where se actualizan las filas necesarias en el arbol B+";
        plan.add(line1);
        plan.add(line2);
        plan.add(line3);
        return plan;
    }

    /**
     * @return
     */
    private ArrayList<String> delete() {

        String line1 = "Se analiza que la instruccion de delete este bien formulada";
        String line2 = "Se busca en la metadata del System Catalog la informacion sobre la tabla: "
                + instruccion.get(1);
        String line3 = "Dependiendo de la declaracion where se actualizan las filas necesarias en el arbol B+";
        plan.add(line1);
        plan.add(line2);
        plan.add(line3);
        return plan;
    }

    /**
     * @return
     */
    private ArrayList<String> insert() {

        String line1 = "Se analiza que la instruccion de insert into este bien formulada";
        String line2 = "Se busca en la metadata del System Catalog la informacion sobre la tabla: "
                + instruccion.get(2) + ". Se buscan las columnas especificadas ";
        String line3 = "Se busca la tabla y las columnas en el arbol B+ y se crea una nueva fila con los valores indicados";
        plan.add(line1);
        plan.add(line2);
        plan.add(line3);
        return plan;
    }

    /**
     * @return
     */
    private ArrayList<String> start() {

    }

    /**
     * @return
     */
    private ArrayList<String> dropTable() {

        String line1 = "Se analiza que la instruccion de drop table este bien formulada";
        String line2 = "Se busca en la metadata del System Catalog la informacion sobre la tabla: "
                + instruccion.get(2) ;
        String line3 = "Se elimina el arbol B+ asociado a esta tabla " + instruccion.get(2);
        plan.add(line1);
        plan.add(line2);
        plan.add(line3);
        return plan;        
    }
}
