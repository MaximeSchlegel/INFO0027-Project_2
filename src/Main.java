import montefiore.ulg.ac.be.graphics.NullHandlerException;

public class Main {
    public static void main(String[] args) {

        Node node1 = new Node("node1");
        Node node2 = null;
        try {
            node2 = node1.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        System.out.println(node1);
        System.out.println(node2);


        if (args.length > 1) {
            System.err.println("Max one argument is allowed");
            System.exit(1);
        }

        try {
            new GuiHandler(args);
        } catch (NullHandlerException e) {
            e.printStackTrace();
        }
    }
}
