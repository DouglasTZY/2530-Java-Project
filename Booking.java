
public class Booking {
    protected String customerName;
    protected String chosenDate;

    public Booking(String n, String d) {
        this.customerName = n;
        this.chosenDate = d;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String toString() {
        return "Date: " + chosenDate + "\nName: " + customerName;
    }
}
