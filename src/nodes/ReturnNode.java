package nodes;

public class ReturnNode extends StatNode {
    private ExprNode exprNode;

    public ReturnNode(String name, ExprNode exprNode) {
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
