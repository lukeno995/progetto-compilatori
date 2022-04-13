package nodes;

import java.util.ArrayList;
import java.util.Collections;

public class CallFunNode extends StatNode{
    private LeafNode leafNode;
    private ArrayList<ExprNode> exprRefsNode;

    public CallFunNode(String name,LeafNode leafNode, ArrayList<ExprNode> exprRefsNode) {
        super(name);
        this.leafNode = leafNode;
        this.exprRefsNode = exprRefsNode;
    }

    public LeafNode getLeafNode() {
        return leafNode;
    }

    public void setLeafNode(LeafNode leafNode) {
        this.leafNode = leafNode;
    }

    public ArrayList<ExprNode> getExprRefsNodeReverse() {
        Collections.reverse(exprRefsNode);
        return exprRefsNode;
    }
    public ArrayList<ExprNode> getExprRefsNode() {
        return exprRefsNode;
    }

    public void setExprRefsNode(ArrayList<ExprNode> exprRefsNode) {
        this.exprRefsNode = exprRefsNode;
    }
}
