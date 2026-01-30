// Group 1 
// Movie Booking System 
// Douglas Tan Tze Yu 1221206532 
// Joel Nithyananthan Paramananthan 1211205630

public class MovieBooking extends Booking {

    private String movieTitle;
    private int seatNumber;
    private String emailAddress;

    // Constructor for movie ticket
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

    // Format data for saving to text file
    public String getSaveLine() {
        return "[" + chosenDate + "] " + customerName + " | " + movieTitle + " | Seat: " + seatNumber + " | "
                + emailAddress;
    }
}
