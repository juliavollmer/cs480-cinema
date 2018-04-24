package cs4280;

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
                this.doBuy(request, response);
            }
        }

        this.doRetrieveEntry(request, response);
    }

    private void doRetrieveEntry(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Movies</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Current Movies</h1>");
            out.println("<div style='width:600px'>");
            out.println("<fieldset>");
            out.println("<legend>Movies now showing</legend>");
            // make connection to db and retrieve data from the table

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db", "aiad039", "aiad039");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [movie] ORDER BY [Release] ASC");
            if (rs != null && rs.last() != false) {
                int numRow = rs.getRow();
                rs.beforeFirst();
                out.println("<p>Total " + numRow + "  entries.</p>");
            }

            // total number of records
            out.println("<div><table style='width:100%'>");
            out.println("<thead>");
            out.println("<th align='left'>Name</th><th align='left'>Cover</th><th align='left'>Details</th>");
            out.println("</thead>");
            out.println("<tbody>");
            while (rs != null && rs.next() != false) {
                String name = rs.getString("Name");
                String cover = rs.getString("Cover");
                Integer mid = rs.getInt("MID");
                // list of data 
                out.println("<tr>");
                out.println("<td>" + this.htmlEncode(name) + "</td>");
                out.println("<td>" + cover + "</td>");
                out.println("<td>");
                out.println("<a href='" + request.getRequestURI() + "?action=details&mid=" + mid + "'>Details</a>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("</table></div>");
            out.println("<br/><a href='" + request.getRequestURI() + "?action=create'>Add a New Phonebook Entry</a>");
            out.println("</fieldset>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (ClassNotFoundException e) {
            out.println("<div style='color: red'>" + e.toString() + "</div>");
        } catch (SQLException e) {
            out.println("<div style='color: red'>" + e.toString() + "</div>");
        } finally {

            out.close();
        }
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
        MovieDetail movie = MovieLookup.getMovie(schedule.getMid());
        request.setAttribute("movie", movie);
        request.setAttribute("schedule", schedule);
        request.setAttribute("seats", seats);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
        dispatcher.forward(request, response);
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        try {
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Movie</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Seating Plan</h1>");
//            out.println("<div style='width:600px'>");
//            out.println("<fieldset>");
//            out.println("<legend>Seating Plan</legend>");
//            // make connection to db and retrieve data from the table
//
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db", "aiad039", "aiad039");
//            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            ResultSet rs = stmt.executeQuery("SELECT * FROM [seating] ORDER BY [SEID] ASC");
//            if (rs != null && rs.last() != false) {
//                int numRow = rs.getRow();
//                rs.beforeFirst();
//                out.println("<p>Total " + numRow + "  entries.</p>");
//            }
//
//            // total number of records
//            out.println("<div><table style='width:100%'>");
//            out.println("<thead>");
//            out.println("<th align='left'> Row</th> <th></th><th align='center'> Screen</th>");
//            out.println("</thead>");
//            out.println("<tbody>");
//            out.println("<tr>");
//            while (rs != null && rs.next() != false) {
//                String name = rs.getString("row_id");
//                String phone = rs.getString("seat_id");
//                Integer uid = rs.getInt("SEID");
//                // list of data 
//                if (uid % 4 == 1) {
//                    out.println("</tr>");
//                    out.println("<tr>");
//                    out.println("<td>" + name + "</td>");
//                }
//
//                out.println("<td><input type='checkbox' name='" + uid + "' value='" + uid + "'>" + uid + "</td>");
//
//            }
//            out.println("</tbody>");
//            out.println("</table></div>");
//            out.println("<br/><a href='" + request.getRequestURI() + "?action=create'>Add a New Phonebook Entry</a>");
//            out.println("</fieldset>");
//            out.println("</div>");
//            out.println("</body>");
//            out.println("</html>");
//
//            if (rs != null) {
//                rs.close();
//            }
//            if (stmt != null) {
//                stmt.close();
//            }
//            if (con != null) {
//                con.close();
//            }
//        } catch (ClassNotFoundException e) {
//            out.println("<div style='color: red'>" + e.toString() + "</div>");
//        } catch (SQLException e) {
//            out.println("<div style='color: red'>" + e.toString() + "</div>");
//        } finally {
//
//            out.close();
//        }
    }

    private void doShowDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String address = "/WEB-INF/showMovie.jsp";
        String mid = request.getParameter("mid");
        MovieDetail movie = MovieLookup.getMovie(mid);
        request.setAttribute("movie", movie);
        List<Schedule> times = new ArrayList();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db", "aiad039", "aiad039");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [schedule] WHERE [MID] ='" + mid + "'");
            while (rs != null && rs.next() != false) {
                String sid = Integer.toString(rs.getInt("SID"));
                 ScheduleLookup.ScheduleCreate();
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
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    private void doBuy(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String seat[] = request.getParameterValues("seat");
        String sid = request.getParameter("sid");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db", "aiad039", "aiad039");
            for (String seat1 : seat) {
                String update = "UPDATE [schedule] SET [Seats] = [Seats] + ' " + seat1 + "' WHERE [SID] = " + sid + "";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(update);
                stmt.close();
                
                con.close();
            }
        } catch (ClassNotFoundException e) {
        } catch (SQLException e) {
        }

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
