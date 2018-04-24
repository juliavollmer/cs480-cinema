/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs4280;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 *
 * @author jsvollmer2
 */
public class PurchaseLookup {
 private static Map<String, cs4280.PurchaseRec> purchases;
public static int length;
 public static void PurchaseCreate(){
    purchases = new java.util.HashMap();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db", "aiad039", "aiad039");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [purchase]");
            while (rs != null && rs.next() != false) {
                String pid = Integer.toString(rs.getInt("PID"));
                purchases.put(pid, new cs4280.PurchaseRec(pid, Integer.toString(rs.getInt("SID")), Integer.toString(rs.getInt("RID")), Integer.toString(rs.getInt("UID")), rs.getString("Time"),
                        rs.getString("Seat"), rs.getString("Price"), rs.getString("GainedPoints"), rs.getString("PayMethod"),
                        rs.getString("Actor"), rs.getString("Status")));
            }
            if (rs != null) {
                rs.close();
            }
        } catch (ClassNotFoundException e) {
        } catch (SQLException e) {
        }
        length = purchases.size();
  }

  public static PurchaseRec getPurchase(java.lang.String pid) {
    return((cs4280.PurchaseRec)purchases.get(pid));
  }
}

