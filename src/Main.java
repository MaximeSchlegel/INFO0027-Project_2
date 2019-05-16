import Observer.EventSource;
import montefiore.ulg.ac.be.graphics.NullHandlerException;

public class Main {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.err.println("Max one argument is allowed");
            System.exit(1);
        }

        try {
            EventSource eventSource = new EventSource();
            eventSource.addObserver(event -> {
                System.out.println("Received response: " + event);
            });
            new GuiHandler(args, eventSource);
        } catch (NullHandlerException e) {
            e.printStackTrace();
        }
    }
}
