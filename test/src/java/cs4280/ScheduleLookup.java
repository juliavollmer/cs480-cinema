package cs4280;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/* emulate database lookup */
public class ScheduleLookup {
 private static java.util.Map<String, cs4280.Schedule> schedule;

  /* Makes a small table of banking customers. */
  static {
    schedule = new HashMap();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db", "aiad039", "aiad039");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [schedule] ORDER BY [MID] ASC");
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

  /** Finds the customer with the given ID.
   *  Returns null if there is no match.
   */

  public static Schedule getSchedule(java.lang.String sid) {
    return((cs4280.Schedule)schedule.get(sid));
  }
}