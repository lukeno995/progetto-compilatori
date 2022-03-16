package visitor.xml;

import visitor.xml.AbstractSyntaxNode;

public interface Visitor {
    String visit(AbstractSyntaxNode ast);
}
