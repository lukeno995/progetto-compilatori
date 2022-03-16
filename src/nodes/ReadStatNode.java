package nodes;

import java.util.ArrayList;
import java.util.Collections;

public class ReadStatNode extends StatNode{
    private ArrayList<LeafNode> idListNode;
    private ExprNode exprNode;

    public ReadStatNode(String name, ArrayList<LeafNode> idListNode, ExprNode exprNode) {
        super(name);
        this.idListNode = idListNode;
        this.exprNode = exprNode;
    }

    public ArrayList<LeafNode> getIdListNode() {
        Collections.reverse(idListNode);
        return idListNode;
    }

    public void setIdListNode(ArrayList<LeafNode> idListNode) {
        this.idListNode = idListNode;
    }

    public ExprNode getExprNode() {
        return exprNode;
    }

    public void setExprNode(ExprNode exprNode) {
        this.exprNode = exprNode;
    }
}
