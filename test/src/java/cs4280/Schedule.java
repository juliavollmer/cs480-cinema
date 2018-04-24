package cs4280;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

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


   private static HashMap schedule;
   static {
        schedule = new HashMap();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db", "aiad039", "aiad039");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [schedule] WHERE [MID] ASC");
            while (rs != null && rs.next() != false) {
                String sid = Integer.toString(rs.getInt("SID"));
                String mid = Integer.toString(rs.getInt("MID"));
                schedule.put(sid, new Schedule(sid, mid, rs.getString("Date"), rs.getString("Time"), rs.getString("Venue"),
                        rs.getString("Seats"), rs.getString("Price")));
            }

            if (rs != null) {
                rs.close();
            }
        } catch (ClassNotFoundException e) {
        } catch (SQLException e) {
        }
    } 
    public static Schedule getSchedule(String sid) {
    return((Schedule)schedule.get(sid));}
}
