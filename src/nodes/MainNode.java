package nodes;

import util.SymbolTable;
import visitor.xml.AbstractSyntaxNode;

import java.util.ArrayList;
import java.util.Collections;

public class MainNode extends AbstractSyntaxNode {
    private ArrayList<VarDeclNode> varDeclListNode;
    private ArrayList<StatNode> statListNode;
    private SymbolTable symbolTable;

    public MainNode(ArrayList<VarDeclNode> varDeclListNode, ArrayList<StatNode> statListNode) {
        this.varDeclListNode = varDeclListNode;
        this.statListNode = statListNode;
    }

    public ArrayList<VarDeclNode> getVarDeclListNode() {
        Collections.reverse(varDeclListNode);
        return varDeclListNode;
    }

    public void setVarDeclListNode(ArrayList<VarDeclNode> varDeclListNode) {
        this.varDeclListNode = varDeclListNode;
    }

    public ArrayList<StatNode> getStatListNode() {
        Collections.reverse(statListNode);
        return statListNode;
    }

    public void setStatListNode(ArrayList<StatNode> statListNode) {
        this.statListNode = statListNode;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}
