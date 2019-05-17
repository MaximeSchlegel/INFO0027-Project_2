public class NodeFile extends Node {
    private String content;

    public NodeFile(String name, String content, FactoryNodeFile factory) {
        super(name, factory);
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitFileNode(this);
    }
}
