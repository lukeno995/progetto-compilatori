package nodes;

public class WriteStatNode extends StatNode {
    private ExprNode exprNode;

    public WriteStatNode(String name, ExprNode exprNode) {
        super(name);
        this.exprNode = exprNode;
    }

    public ExprNode getExprNode() {
        return exprNode;
    }

    public void setExprNode(ExprNode exprNode) {
        this.exprNode = exprNode;
    }
}
