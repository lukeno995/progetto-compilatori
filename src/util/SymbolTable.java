package util;

import nodes.*;
import visitor.xml.AbstractSyntaxNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//HashMap per gli identificatori
public class SymbolTable <String, RecordTable> extends HashMap<String, RecordTable> {
    private SymbolTable<String, RecordTable> parent;
    private final List<SymbolTable<String, RecordTable>> children = new ArrayList<>();
    private AbstractSyntaxNode scopingNode;

    public SymbolTable(SymbolTable<String, RecordTable> parent) {
        this.setParent(parent);
        if (parent != null) {
            parent.addChild(this);
        }
    }


    public void setParent(SymbolTable<String, RecordTable> parent) {
        this.parent = parent;
    }

    public void addChild(SymbolTable<String, RecordTable> child) {
        children.add(child);
    }

    public SymbolTable<String, RecordTable> getParent() {
        return parent;
    }

    public List<SymbolTable<String, RecordTable>> getChildren() {
        return children;
    }

    public RecordTable lookup(String key) {
        if (this.containsKey(key))
            return (RecordTable) this.get(key);
        else {
            if (this.parent != null) {
                return parent.lookup(key);
            } else {
                return null;
            }
        }
    }

    public AbstractSyntaxNode getScopingNode() {
        return scopingNode;
    }

    public void setScopingNode(AbstractSyntaxNode scopingNode) {
        this.scopingNode = scopingNode;
    }


    @Override
    public java.lang.String toString() {
        java.lang.String string = "";
        string += "\nGlobal : \n";
        for (RecordTable tmp : this.values())
            string += tmp + "\n";
        int number = 0;
        for (SymbolTable tmp : this.getChildren()) {
            if(tmp.getScopingNode() instanceof FunNode){
                string += "\n" + ((FunNode) tmp.getScopingNode()).getLeafNode().getNameId() + " : \n";
            }
            if(tmp.getScopingNode() instanceof WhileStatNode){
                string += "\nWhileOpNode_" + number +  " : \n";
                number++;
            }
            if(tmp.getScopingNode() instanceof IfStatNode){
                string += "\nIfStatNode_" + number +  " : \n";
                number++;
            }
            if(tmp.getScopingNode() instanceof ElseNode){
                string += "\nElseNode_" + number +  " : \n";
                number++;
            }
            for (Object tmp1 : tmp.values())
                string += tmp1 + "\n";
        }
        return string;
    }
}
