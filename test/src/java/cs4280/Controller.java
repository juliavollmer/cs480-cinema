package cs4280;

/**
 *
 * @author jsvollmer2
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action != null) {
            // call different action depends on the action parameter
            if (action.equalsIgnoreCase("details")) {
                this.doShowDetails(request, response);
            } else if (action.equalsIgnoreCase("seating")) {
                this.doShowSeat(request, response);
            } else if (action.equalsIgnoreCase("buying")) {
                this.doShowTicketsale(request, response);
            } else if (action.equalsIgnoreCase("buy")) {
                this.doBuy(request, response);
            }
        } else {
            this.doRetrieveEntry(request, response);
        }
    }

    private void doRetrieveEntry(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String address = "/WEB-INF/MovieIndex.jsp";
        List<MovieDetail> movies = new ArrayList();
        MovieLookup.MoviesCreate();
        for (int i = 1; i <= MovieLookup.length; i++) {
            MovieDetail movie = MovieLookup.getMovie(Integer.toString(i));
            movies.add(movie);
        }
        request.setAttribute("movies", movies);
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    private void doShowSeat(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String address = "/WEB-INF/SeatingPlan.jsp";
        String sid = request.getParameter("sid");
        ScheduleLookup.ScheduleCreate();
        Schedule schedule = ScheduleLookup.getSchedule(sid);
        String check = schedule.getSeats();

        String[] taken = {""};
        if (check != null) {
            taken = schedule.getSeats().split(" ");
        }
        List<Seat> seats = new ArrayList();
        for (int i = 1; i <= 16; i++) {
            String seatid = Integer.toString(i);
            String value = "abled";
            if (Arrays.asList(taken).contains(seatid)) {
                value = "disabled";
            }
            Seat seat = new Seat(seatid, value);
            seats.add(seat);
        }
        MovieLookup.MoviesCreate();
        MovieDetail movie = MovieLookup.getMovie(schedule.getMid());
        request.setAttribute("movie", movie);
        request.setAttribute("schedule", schedule);
        request.setAttribute("seats", seats);
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    private void doShowDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String address = "/WEB-INF/showMovie.jsp";
        String mid = request.getParameter("mid");
        MovieLookup.MoviesCreate();
        MovieDetail movie = MovieLookup.getMovie(mid);
        request.setAttribute("movie", movie);
        List<Schedule> times = new ArrayList();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db", "aiad039", "aiad039");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [schedule] WHERE [MID] ='" + mid + "'");
            ScheduleLookup.ScheduleCreate();
            while (rs != null && rs.next() != false) {
                String sid = Integer.toString(rs.getInt("SID"));
                Schedule schedule = ScheduleLookup.getSchedule(sid);
                times.add(schedule);
            }

            if (rs != null) {
                rs.close();
            }
            con.close();
        } catch (ClassNotFoundException e) {
        } catch (SQLException e) {
        }
        request.setAttribute("times", times);
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    private void doBuy(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String seats[] = request.getParameterValues("seat");
        String sid = request.getParameter("sid");
        Float total = Float.parseFloat(request.getParameter("total"));
        String paymethod = request.getParameter("paymethod");
        String seat = "";
        // I haven't implemented any user yet
        String time ="2018";
        int uid = 3; 
        String actor = "Customer";
        int points = 2;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db", "aiad039", "aiad039");
            for (int i=0;i<seats.length;i++) {
                String update = "UPDATE [schedule] SET [Seats] = [Seats] + ' " + seats[i] + "' WHERE [SID] = " + sid + "";
                seat = seat +" "+ seats[i];
                Statement stmt = con.createStatement();
                stmt.executeUpdate(update);
                stmt.close();

                
            }
            String query = "INSERT INTO [purchase] ([SID], [UID], [Status], [Seat], [Time], [Price], [GainedPoints], [PayMethod], [Actor]) "
                    + "VALUES("+ Integer.parseInt(sid) + "," + uid +" 'Completed', " + seat + ", " + time + ", "+ total + ", "+ points + ", " + paymethod + ", "+ actor + ")";
            Statement stmt2 = con.createStatement();
                stmt2.executeUpdate(query);
                stmt2.close();
            con.close();
        } catch (ClassNotFoundException e) {
        } catch (SQLException e) {
        }
        this.doRetrieveEntry(request, response);
    }

    private void doShowTicketsale(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String address = "/WEB-INF/showTicketsale.jsp";
        String seat[] = request.getParameterValues("seat");

        String sid = request.getParameter("sid");
        ScheduleLookup.ScheduleCreate();
        Schedule schedule = ScheduleLookup.getSchedule(sid);

        MovieLookup.MoviesCreate();
        MovieDetail movie = MovieLookup.getMovie(schedule.getMid());
        request.setAttribute("movie", movie);
        request.setAttribute("schedule", schedule);
        request.setAttribute("seat", seat);
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    private String htmlEncode(String s) {

        StringBuffer sb = new StringBuffer(s.length() * 2);

        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if ((ch >= '?' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch == ' ') || (ch == '\n')) {
                sb.append(ch);
            } else {
                switch (ch) {
                    case '>':
                        sb.append("&gt;");
                        break;
                    case '<':
                        sb.append("&lt;");
                        break;
                    case '&':
                        sb.append("&amp;");
                        break;
                    case '\'':
                        sb.append("&#039;");
                        break;
                    case '"':
                        sb.append("&quot;");
                        break;
                    default:
                        sb.append("&#");
                        sb.append(new Integer(ch).toString());
                        sb.append(';');
                }
            }
        }

        return sb.toString();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
