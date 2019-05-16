package Observer;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EventSource {
    public interface Observer{
        void update(String event);
    }
    private final ArrayList<Observer> observers = new ArrayList<>();

    private void notifyObservers(String event){
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",  Locale.US);
        String operatingSystem = System.getProperty("os.name");
        String user = System.getProperty("user.name");
        String dateNow = dateFormat.format(new Date());
        observers.forEach(observer -> observer.update(dateNow + " - " + user + " - " + operatingSystem + " - " + event));
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void scanUserIntraction(String interaction){
        notifyObservers(interaction);
    }

}
