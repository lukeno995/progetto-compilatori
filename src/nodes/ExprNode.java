package nodes;

import visitor.AbstractSyntaxNode;

public class ExprNode extends AbstractSyntaxNode {
    private String name;

    public ExprNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
