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

        EventSource eventSource = new EventSource();

        if(args.length  == 0){
            eventSource.addObserver(
                    event -> {
                        System.out.println(event);
                    });
        } else {
            PrintWriter writer = null;

            try {
                writer = new PrintWriter(new File("./log.txt"));
                PrintWriter finalWriter = writer;

                eventSource.addObserver(
                        event -> {
                            finalWriter.write( event + "\n");
                            finalWriter.flush();
                        });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
        try {
            new GuiHandler(args, eventSource);
        } catch (NullHandlerException e) {
            e.printStackTrace();
        }
    }
}
