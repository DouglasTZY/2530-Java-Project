
public class MovieBooking extends Booking {

    private String movieTitle;
    private int seatNumber;
    private String emailAddress;

    public MovieBooking(String name, String title, int seat, String mail, String date) {
        super(name, date);
        this.movieTitle = title;
        this.seatNumber = seat;
        this.emailAddress = mail;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nMovie: " + movieTitle +
                "\nSeat: " + seatNumber +
                "\nEmail: " + emailAddress;
    }

    public String getSaveLine() {
        return "[" + chosenDate + "] " + customerName + " | " + movieTitle + " | Seat: " + seatNumber + " | "
                + emailAddress;
    }
}
