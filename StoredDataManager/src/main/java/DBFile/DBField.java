package DBFile;

/**
 * Created by manzumbado on 25/09/15.
 */

/**
 * Clase que representa un campo escrito en disco
 */

public class DBField {



    private int mFieldLength;
    private String mValue;
    private String mFieldName;

    public DBField(){
    }

    public DBField(String fieldName, String value, int length){
        this.mFieldName=fieldName;
        this.mValue=value;
        this.mFieldLength=length;
    }

    public int getFieldLength() {
        return mFieldLength;
    }

    public String getValue() {
        return mValue;
    }

    public String getFieldName() {
        return mFieldName;
    }
    public void setValue(String mValue) {
        this.mValue = mValue;
    }

    public void setFieldName(String mFieldName) {
        this.mFieldName = mFieldName;
    }

    public void setFieldLength(int mFieldLength) {

        this.mFieldLength = mFieldLength;
    }

    public String toString(){
        String length =  Integer.toString(this.getFieldLength());
        String value = this.getValue();
        String fieldname=this.getFieldName();
        return "Length: "+ length+" Field Name: "+fieldname+ " Value: "+value;
    }
}
