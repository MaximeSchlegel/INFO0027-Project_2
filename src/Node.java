import com.sun.xml.internal.ws.api.ha.StickyFeature;
import org.omg.CORBA.PUBLIC_MEMBER;

public class Node implements Cloneable{
    private String name;
    private String nameClone;
    private int NbrClonning = 0;
    public Node(String name) {
        this.name = name;
        this.nameClone = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameClone() {
        return nameClone;
    }

    public void setNameClone(String nameClone) {
        this.nameClone = nameClone;
    }

    @Override
    public Node clone() throws CloneNotSupportedException {
        this.NbrClonning++;
        Node mNode = (Node) super.clone();
        mNode.setNameClone(mNode.getName() + " (copy_" + NbrClonning + ")");
        return mNode;
    }

    @Override
    public String toString() {
//        return "Node{" +
//                "name='" + name + '\'' +
//                '}';
        return nameClone;
    }
}
