package nodes;

import visitor.xml.AbstractSyntaxNode;

import java.util.ArrayList;
import java.util.Collections;

public class ProgramNode extends AbstractSyntaxNode {
    private ArrayList<VarDeclNode>  varDeclListNode;
    private ArrayList<FunNode> funListNode;
    private MainNode mainNode;

    public ProgramNode(ArrayList<VarDeclNode> varDeclListNode, ArrayList<FunNode> funListNode, MainNode mainNode) {
        this.varDeclListNode = varDeclListNode;
        this.funListNode = funListNode;
        this.mainNode = mainNode;
    }

    public ArrayList<VarDeclNode> getVarDeclListNode() {
        Collections.reverse(varDeclListNode);
        return varDeclListNode;
    }

    public void setVarDeclListNode(ArrayList<VarDeclNode> varDeclListNode) {
        this.varDeclListNode = varDeclListNode;
    }

    public ArrayList<FunNode> getFunListNode() {
        Collections.reverse(funListNode);
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
}
