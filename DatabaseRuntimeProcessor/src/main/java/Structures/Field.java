package Structures;

/**
 * Clase encargada de guardar el contenido de una columna específica. Posee
 * atributos que describen nombre de la columna, tipo de dato que almacena,
 * tabla al que pertenece, esquema al que pertenece , constraint que posee, y si
 * el campo es llave primaria.
 *
 * @author Kevin
 */
public class Field {

    private final String content;
    private final String type;
    private String tableName;
    private String schemaName;
    private final boolean isNull;
    private final boolean primaryKey;

    public Field(String content, String type, boolean isNull, boolean primaryKey) {
        this.content = content;
        this.type = type;
        this.isNull = isNull;
        this.primaryKey = primaryKey;
    }

    public Field(String content, String type, boolean isNull, String tableName, String schemaName, boolean primaryKey) {
        this.content = content;
        this.type = type;
        this.isNull = isNull;
        this.tableName = tableName;
        this.schemaName = schemaName;
        this.primaryKey = primaryKey;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @return the schemaName
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * @return the isNull
     */
    public boolean getIsNull() {
        return isNull;
    }

    /**
     * @return the primaryKey
     */
    public boolean isPrimaryKey() {
        return primaryKey;
    }
    
}
