/**
 * Subclass extending Booking
 * Satisfies Inheritance requirement (1 subclass)
 */
public class MovieBooking extends Booking {
    private String movieName;
    private int seatNumber;
    private String email;

    public MovieBooking(String customerName, String movieName, int seatNumber, String email) {
        super(customerName); // Call superclass constructor
        this.movieName = movieName;
        this.seatNumber = seatNumber;
        this.email = email;
    }

    public String getMovieName() {
        return movieName;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    @Override
    public String toString() {
        return super.toString() + "\nMovie: " + movieName + "\nSeat: " + seatNumber + "\nEmail: " + email;
    }

    // Format for file writing
    public String toFileString() {
        return String.format("[%s] Customer: %s, Movie: %s, Seat: %d, Email: %s",
                getFormattedDate(), customerName, movieName, seatNumber, email);
    }
}
