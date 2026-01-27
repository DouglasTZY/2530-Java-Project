// Child class that extends Booking
// This handles the specific details for a movie ticket

public class MovieBooking extends Booking {

    // Extra variables for movie details
    private String movieTitle;
    private int seatNo;
    private String userEmail;

    // Constructor to set all the data
    public MovieBooking(String name, String movie, int seat, String email) {
        super(name); // Send name to parent class (Booking)
        this.movieTitle = movie;
        this.seatNo = seat;
        this.userEmail = email;
    }

    // Override toString to show full details
    @Override
    public String toString() {
        // combine parent info with this class info
        return super.toString() +
                "\nMovie: " + movieTitle +
                "\nSeat Number: " + seatNo +
                "\nEmail: " + userEmail;
    }

    // Format data for writing to text file
    public String getDataForFile() {
        // Format: [Date] Name | Movie | Seat | Email
        return "[" + date + "] " + name + " | " + movieTitle + " | Seat: " + seatNo + " | " + userEmail;
    }
}
