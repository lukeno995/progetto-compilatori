package nodes;

public class AssignStatNode extends StatNode{
    private LeafNode leafNode;
    private ExprNode exprNode;

    public AssignStatNode(String name, LeafNode leafNode, ExprNode exprNode) {
        super(name);
        this.leafNode = leafNode;
        this.exprNode = exprNode;
    }

    public LeafNode getLeafNode() {
        return leafNode;
    }

    public void setLeafNode(LeafNode leafNode) {
        this.leafNode = leafNode;
    }

    public ExprNode getExprNode() {
        return exprNode;
    }

    public void setExprNode(ExprNode exprNode) {
        this.exprNode = exprNode;
    }
}
