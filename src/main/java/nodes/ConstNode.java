package nodes;

public class ConstNode extends ExprNode{
    private String nameID;
    private Object value;

    public ConstNode(String nameID, Object value){
        super(nameID);
        this.nameID = nameID;
        this.value = value;
    }

    public String getNameID(){
        return this.nameID;
    }

    public Object getValue(){
        return this.value;
    }

    public void setNameID(String nameID){
        this.nameID = nameID;
    }

    public void setValue(Object value){
        this.value = value;
    }

    public String toString(){
        return "ConstNode{" +
                "nameID='" + nameID + '\'' +
                ", value=" + value +
                '}';
    }
}
