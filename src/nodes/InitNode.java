package nodes;

import visitor.AbstractSyntaxNode;

public class InitNode extends AbstractSyntaxNode {
    private LeafNode leafNode;
    private ExprNode exprNode;
    private String constant;

    public InitNode(LeafNode leafNode, ExprNode exprNode, String constant) {
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

    public String getConstant() {
        return constant;
    }

    public void setConstant(String constant) {
        this.constant = constant;
    }
}
