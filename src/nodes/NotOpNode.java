package nodes;

public class NotOpNode extends ExprNode {
    private ExprNode exprNode1;

    public NotOpNode(String name, ExprNode exprNode1) {
        super(name);
        this.exprNode1 = exprNode1;
    }

    public ExprNode getExprNode1() {
        return exprNode1;
    }

    public void setExprNode1(ExprNode exprNode1) {
        this.exprNode1 = exprNode1;
    }

}
