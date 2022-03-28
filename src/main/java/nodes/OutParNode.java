package nodes;

public class OutParNode extends ExprNode {
    private LeafNode leafNode;

    public OutParNode(String name, LeafNode leafNode) {
        super(name);
        this.leafNode = leafNode;
    }

    public LeafNode getLeafNode() {
        return leafNode;
    }

    public void setLeafNode(LeafNode leafNode) {
        this.leafNode = leafNode;
    }
}
