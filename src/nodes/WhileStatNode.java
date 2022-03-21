package nodes;

import util.SymbolTable;

import java.util.ArrayList;
import java.util.Collections;

public class WhileStatNode extends StatNode {
    private ExprNode exprNode;
    private ArrayList<VarDeclNode> varDeclListNode;
    private ArrayList<StatNode> statListNode;
    private SymbolTable symbolTable;

    public WhileStatNode(String name, ExprNode exprNode, ArrayList<VarDeclNode> varDeclListNode, ArrayList<StatNode> statListNode) {
        super(name);
        this.exprNode = exprNode;
        this.varDeclListNode = varDeclListNode;
        this.statListNode = statListNode;
    }

    public ExprNode getExprNode() {
        return exprNode;
    }

    public void setExprNode(ExprNode exprNode) {
        this.exprNode = exprNode;
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
