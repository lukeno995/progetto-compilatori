package nodes;

import visitor.xml.AbstractSyntaxNode;

import java.util.ArrayList;
import java.util.Collections;

public class VarDeclNode extends AbstractSyntaxNode {
    private String nameNode;
    private ArrayList<InitNode> initNodes;
    private String typeVar;

    public VarDeclNode(String nameNode,  String typeVar,ArrayList<InitNode> initNodes) {
        this.nameNode = nameNode;
        this.initNodes = initNodes;
        this.typeVar = typeVar;
    }

    public String getNameNode() {
        return nameNode;
    }

    public void setNameNode(String nameNode) {
        this.nameNode = nameNode;
    }

    public ArrayList<InitNode> getInitNodes() {
        Collections.reverse(initNodes);
        return initNodes;
    }

    public void setInitNodes(ArrayList<InitNode> initNodes) {
        this.initNodes = initNodes;
    }

    public String getTypeVar() {
        return typeVar;
    }

    public void setTypeVar(String typeVar) {
        this.typeVar = typeVar;
    }
}
