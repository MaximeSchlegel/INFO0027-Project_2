public class AliasNode extends Node {
    private Object target;

    public AliasNode(String name, Object target) {
        super(name);
        this.target = target;
    }
}
