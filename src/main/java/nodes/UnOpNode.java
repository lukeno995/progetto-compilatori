package nodes;

public class UnOpNode extends ExprNode{
    private ExprNode exprNode1;

    public UnOpNode(String name, ExprNode exprNode1) {
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
