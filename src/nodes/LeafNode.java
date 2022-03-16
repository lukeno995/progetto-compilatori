package nodes;

public class LeafNode extends ExprNode {
    private String nameId;

    public LeafNode(String name, String nameId) {
        super(name);
        this.nameId = nameId;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }
}
