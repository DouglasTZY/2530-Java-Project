import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Parent class to store basic booking info
public class Booking {
    // Protected variables so subclasses can access them
    protected String name;
    protected String date;

    // Constructor
    public Booking(String name) {
        this.name = name;

        // Go get the current time
        LocalDateTime now = LocalDateTime.now();
        // Simple format: Year-Month-Day Hour:Minute
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.date = now.format(myFormat);
    }

    public String getName() {
        return name;
    }

    // Display booking info
    public String toString() {
        return "Date: " + date + "\nName: " + name;
    }
}
