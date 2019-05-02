import com.sun.xml.internal.ws.api.ha.StickyFeature;
import org.omg.CORBA.PUBLIC_MEMBER;

public class Node implements Cloneable{
    private String name;

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Node clone() throws CloneNotSupportedException {
        return (Node) super.clone();
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                '}';
    }
}
