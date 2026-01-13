import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Superclass representing a generic Booking
 * Satisfies Inheritance/Association requirement (1 superclass)
 */
public class Booking {
    protected String customerName;
    protected LocalDateTime bookingDate;

    public Booking(String customerName) {
        this.customerName = customerName;
        this.bookingDate = LocalDateTime.now();
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return bookingDate.format(formatter);
    }

    @Override
    public String toString() {
        return "Booking Date: " + getFormattedDate() + "\nCustomer: " + customerName;
    }
}
