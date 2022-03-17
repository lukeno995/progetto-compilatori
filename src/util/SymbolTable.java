package util;

import nodes.LeafNode;
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
}
