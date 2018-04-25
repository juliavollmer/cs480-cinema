package cs4280;

/**
 *
 * @author jsvollmer2
 */
public class Schedule {

    private String sid, mid, date, time, venue, seats, price;

    public Schedule(String sid, String mid, String date,
            String time, String venue, String seats,
            String price) {
        setMid(mid);
        setSid(sid);
        setDate(date);
        setTime(time);
        setVenue(venue);
        setSeats(seats);
        setPrice(price);
    }

    public Schedule() {
    }

    public String getMid() {
        return (mid);
    }

    private void setMid(String mid) {
        this.mid = mid;
    }

    public String getSid() {
        return (sid);
    }

    private void setSid(String sid) {
        this.sid = sid;
    }

    public String getDate() {
        return (date);
    }

    private void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return (time);
    }

    private void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return (venue);
    }

    private void setVenue(String venue) {
        this.venue = venue;
    }

    public String getSeats() {
        return (seats);
    }

    private void setSeats(String seats) {
        this.seats = seats;
    }

    public String getPrice() {
        return (price);
    }

    private void setPrice(String price) {
        this.price = price;
    }
}
