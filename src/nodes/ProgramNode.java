package nodes;

import util.SymbolTable;
import visitor.xml.AbstractSyntaxNode;

import java.util.ArrayList;
import java.util.Collections;

public class ProgramNode extends AbstractSyntaxNode {
    private ArrayList<VarDeclNode>  varDeclListNode;
    private ArrayList<FunNode> funListNode;
    private MainNode mainNode;
    private SymbolTable symbolTable;

    public ProgramNode(ArrayList<VarDeclNode> varDeclListNode, ArrayList<FunNode> funListNode, MainNode mainNode) {
        this.varDeclListNode = varDeclListNode;
        this.funListNode = funListNode;
        this.mainNode = mainNode;
    }

    public ArrayList<VarDeclNode> getVarDeclListNodeReverse() {
        Collections.reverse(varDeclListNode);
        return varDeclListNode;
    }
    public ArrayList<VarDeclNode> getVarDeclListNode() {
        return varDeclListNode;
    }

    public void setVarDeclListNode(ArrayList<VarDeclNode> varDeclListNode) {
        this.varDeclListNode = varDeclListNode;
    }

    public ArrayList<FunNode> getFunListNodeReverse() {
        Collections.reverse(funListNode);
        return funListNode;
    }

    public ArrayList<FunNode> getFunListNode() {
        return funListNode;
    }

    public void setFunListNode(ArrayList<FunNode> funListNode) {
        this.funListNode = funListNode;
    }

    public MainNode getMainNode() {
        return mainNode;
    }

    public void setMainNode(MainNode mainNode) {
        this.mainNode = mainNode;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}
