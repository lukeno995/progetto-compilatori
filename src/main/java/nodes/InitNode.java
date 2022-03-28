package nodes;

import visitor.xml.AbstractSyntaxNode;

public class InitNode extends AbstractSyntaxNode {
    private LeafNode leafNode;
    private ExprNode exprNode;
    private ConstNode constant;

    public InitNode(LeafNode leafNode, ExprNode exprNode, ConstNode constant) {
        this.leafNode = leafNode;
        this.exprNode = exprNode;
        this.constant = constant;
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

    public ConstNode getConstant() {
        return constant;
    }

    public void setConstant(ConstNode constant) {
        this.constant = constant;
    }
}
