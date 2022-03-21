package nodes;

import util.SymbolTable;
import visitor.xml.AbstractSyntaxNode;

import java.util.ArrayList;
import java.util.Collections;

public class FunNode extends AbstractSyntaxNode {
    private LeafNode leafNode;
    private ArrayList<ParamDeclNode> paramDeclListNodes;
    private String typeVar;
    private ArrayList<VarDeclNode>  varDeclListNode;
    private ArrayList<StatNode> statListNode;
    private SymbolTable symbolTable;

    public FunNode(LeafNode leafNode, ArrayList<ParamDeclNode> paramDeclListNodes, String typeVar, ArrayList<VarDeclNode> varDeclListNode, ArrayList<StatNode> statListNode) {
        this.leafNode = leafNode;
        this.paramDeclListNodes = paramDeclListNodes;
        this.typeVar = typeVar;
        this.varDeclListNode = varDeclListNode;
        this.statListNode = statListNode;
    }

    public LeafNode getLeafNode() {
        return leafNode;
    }

    public void setLeafNode(LeafNode leafNode) {
        this.leafNode = leafNode;
    }

    public ArrayList<ParamDeclNode> getParamDeclListNodes() {
        return paramDeclListNodes;
    }

    public void setParamDeclListNodes(ArrayList<ParamDeclNode> paramDeclListNodes) {
        this.paramDeclListNodes = paramDeclListNodes;
    }

    public String getTypeVar() {
        return typeVar;
    }

    public void setTypeVar(String typeVar) {
        this.typeVar = typeVar;
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
