package nodes;

import visitor.xml.AbstractSyntaxNode;

public class ParamDeclNode extends AbstractSyntaxNode {
    private String mode;
    private String typeParam;
    private LeafNode leafNode;

    public ParamDeclNode(String mode, String typeParam, LeafNode leafNode) {
        this.mode = mode;
        this.typeParam = typeParam;
        this.leafNode = leafNode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTypeParam() {
        return typeParam;
    }

    public void setType(String typeParam) {
        this.typeParam = typeParam;
    }

    public LeafNode getLeafNode() {
        return leafNode;
    }

    public void setLeafNode(LeafNode leafNode) {
        this.leafNode = leafNode;
    }
}
