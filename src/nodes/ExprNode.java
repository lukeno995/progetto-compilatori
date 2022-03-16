package nodes;

import util.RecordTable;
import util.SymbolTable;
import visitor.xml.AbstractSyntaxNode;

public class ExprNode extends AbstractSyntaxNode {
    private String name;
    private SymbolTable<String, RecordTable> symbolTable;

    public ExprNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SymbolTable<String, RecordTable> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable<String, RecordTable> symbolTable) {
        this.symbolTable = symbolTable;
    }
}
