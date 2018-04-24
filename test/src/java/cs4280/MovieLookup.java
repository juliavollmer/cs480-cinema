package cs4280;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/* emulate database lookup */
public class MovieLookup {
 private static java.util.Map<String, cs4280.MovieDetail> movies;

  /* Makes a small table of banking customers. */
  static {
    movies = new java.util.HashMap();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db", "aiad039", "aiad039");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [movie] ORDER BY [Release] ASC");
            while (rs != null && rs.next() != false) {
                String mid = Integer.toString(rs.getInt("MID"));
                movies.put(mid, new cs4280.MovieDetail(mid, rs.getString("Name"), rs.getString("Intro"), rs.getString("Genre"), rs.getString("Runtime"),
                        rs.getString("Category"), rs.getString("Language"), rs.getString("Director"), rs.getString("Cast"),
                        rs.getString("Cover"), rs.getString("Release")));
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

  public static MovieDetail getMovie(java.lang.String mid) {
    return((cs4280.MovieDetail)movies.get(mid));
  }
}