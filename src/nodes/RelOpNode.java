package nodes;

public class RelOpNode extends ExprNode {
    private ExprNode exprNode1;
    private ExprNode exprNode2;

    public RelOpNode(String name, ExprNode exprNode1, ExprNode exprNode2) {
        super(name);
        this.exprNode1 = exprNode1;
        this.exprNode2 = exprNode2;
    }

    public ExprNode getExprNode1() {
        return exprNode1;
    }

    public void setExprNode1(ExprNode exprNode1) {
        this.exprNode1 = exprNode1;
    }

    public ExprNode getExprNode2() {
        return exprNode2;
    }

    public void setExprNode2(ExprNode exprNode2) {
        this.exprNode2 = exprNode2;
    }
}
