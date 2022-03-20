package nodes;

import util.RecordTable;
import util.SymbolTable;

import java.util.ArrayList;
import java.util.Collections;

public class IfStatNode extends StatNode{
    private ExprNode exprNode;
    private ArrayList<VarDeclNode>  varDeclListNode;
    private ArrayList<StatNode> statListNode;
    private ElseNode elseNode;
    private SymbolTable<String, RecordTable> symbolTable;


    public IfStatNode(String name, ExprNode exprNode, ArrayList<VarDeclNode> varDeclListNode, ArrayList<StatNode> statListNode, ElseNode elseNode) {
        super(name);
        this.exprNode = exprNode;
        this.varDeclListNode = varDeclListNode;
        this.statListNode = statListNode;
        this.elseNode = elseNode;
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

    public ElseNode getElseNode() {
        return elseNode;
    }

    public void setElseNode(ElseNode elseNode) {
        this.elseNode = elseNode;
    }
    public SymbolTable<String, RecordTable> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable<String, RecordTable> symbolTable) {
        this.symbolTable = symbolTable;
    }
}
