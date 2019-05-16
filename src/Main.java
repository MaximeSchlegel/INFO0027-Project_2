import Observer.EventSource;
import montefiore.ulg.ac.be.graphics.NullHandlerException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.err.println("Max one argument is allowed");
            System.exit(1);
        }

        try {
            EventSource eventSource = new EventSource();
            if(args.length  == 0){
                eventSource.addObserver(event -> {
                    System.out.println("Received response: " + event);
                });
            }
            else{
                PrintWriter writer1 =null;
                try {
                    writer1 = new PrintWriter(new File("./log.txt"));
                    PrintWriter finalWriter = writer1;
                    eventSource.addObserver(event -> {
                        finalWriter.write("Received response: " + event + "\n");
                        finalWriter.flush();
                    });
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return;
                }
            }
            new GuiHandler(args, eventSource);
        } catch (NullHandlerException e) {
            e.printStackTrace();
        }
    }
}
