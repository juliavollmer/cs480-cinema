/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs4280;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author yanfewang3
 */


public class HomeServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
     protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String action = request.getParameter("action");

       
        if (action != null) {
            // call different action depends on the action parameter
            if (action.equalsIgnoreCase("refund")) {
                this.doRefundRecord(request, response);
            }
            else if (action.equalsIgnoreCase("authorize")) {
                this.doAuthorizeRequest(request, response);
            }else if(action.equalsIgnoreCase("manageSeat")){
                 this.doManageSeats(request, response);
            }else if(action.equalsIgnoreCase("scheduleMovie")){
                 this.doScheduleMovie(request, response);
            }
           
        }

        HttpSession session = request.getSession();
        User userBean = (User) session.getAttribute("user");
        try{
            if(userBean.getActor().equals("manager")){
                this.showManagerHome(request, response);
            }else if(userBean.getActor().equals("officer")){
                this.showOfficerHome(request, response);
            }else{
            this.showCustomerHome(request, response);
        }
        }catch(NullPointerException e){
            
        }finally{
            
        }

    }
     


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void doRefundRecord(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try{
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Phonebook</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Phonebook (Delete)</h1>");
            out.println("<div style='width:600px'>");
            
            out.println("<fieldset>");
            String page = request.getParameter("page");
            String pid = request.getParameter("pid");
            
            
            if (page != null && !page.equalsIgnoreCase("") ) {

                // Register the JDBC driver, open a connection
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db",
                            "aiad039", "aiad039");

                // Create a preparedstatement to set the SQL statement			 
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO [request] ([PID], [Status]) VALUES (?, 'Waiting')");
                pstmt.setInt(1, Integer.parseInt(pid));
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows>0) {
                    out.println("<legend>The record is successfuly created.</legend>");
                    // display the information of the record just added including UID
                     Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    // execute the SQL statement
                    ResultSet rs = stmt.executeQuery("SELECT * FROM [request] WHERE [pid] ="+pid);
                   
                    if (rs != null && rs.next() != false) {
                            out.println("<p>UID: " + rs.getInt(1) + "</p>");
                            rs.close();
                    }
                    if (stmt != null) {
                            stmt.close();
                    }
                    out.println("<h3>We have accept your request!</h3>");
                    
                }
                else {
                    out.println("<legend>ERROR: New record is failed to create.</legend>");
                }
               
                
            }else {
                
                 // Register the JDBC driver, open a connection
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db",
                            "aiad039", "aiad039");
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
               
                // execute the SQL statement
                ResultSet rs = stmt.executeQuery("SELECT * FROM [request] WHERE [pid] ="+pid);
                int numRow = 0;
                if (rs != null && rs.last() != false) {
                    numRow = rs.getRow();
                    rs.beforeFirst();
                }
               
                if(numRow == 0){//if the record hasn't been refunded

                    rs = stmt.executeQuery("SELECT * FROM [purchase] WHERE [pid] ="+pid);
                    String method = "";
                    if (rs != null && rs.next() != false) {
                        method = rs.getString("PayMethod");
         
                            
                    }
                    if(method.equals("point")){
                        out.println("<h3>Sorry, You cannot refund purchase with points.</h3>");
                    }else{
                        out.println("<h3>Do you want to refund this purchase?</h3>");
                        while(rs !=null &&rs.next()!=false){
                           out.println("<form method='POST' action='" + request.getRequestURI() + "'>");
                           out.println("<input name='action' type='hidden' value='refund' />");
                           out.println("<input name='pid' type='hidden' value='"
                                   + pid+"' />");
                           out.println("<input name='page' type='hidden' value='1' />");
                           int sid = rs.getInt("sid");
                           String date = rs.getString("time");
                           out.println("<tr>");
                           out.println("<td>" + pid + "</td>");
                           out.println("<td>" + sid + "</td>");
                           out.println("<td>" + date + "</td>");
                           out.println("</tr>");
                           out.println("<br><input type='submit' value='Refund!' />");
                           out.println("</form>");
                       }
                    }
                    
                    
                }else{//if the record has already been refunded, show status
                 
                    while(rs !=null &&rs.next()!=false){
                    
                    int arid = rs.getInt("rid");
                    int executor = rs.getInt("UID");
                    out.println("<P>We have accept your request for refund"
                            + arid+ " on refund has already been approved by "
                            + executor +" </P>");
                }
                }
               
                
            }
            out.println("<br/><a href='" + request.getRequestURI() + "'>Back to Home Page</a>");
            out.println("</fieldset>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            
        }catch(ClassNotFoundException e){
            out.println("<div style='color: red'>" + e.toString() + "</div>");
        }catch(SQLException e){
            out.println("<div style='color: red'>" + e.toString() + "</div>");
        }finally{
            out.close();
        }
    }

    private void showCustomerHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        try {
            HttpSession session = request.getSession();
            User userBean = (User) session.getAttribute("user");
            
            //session.invalidate();
            out.println("<html>");
            out.println("<head>");
            out.println(" <title>Home Page</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Home Page</h1>");
            out.println("<fieldset>");
            out.println("<p>Welcome! Dear "
                    +userBean.getName() +"</p>");
            
            
            
            //print all purchase record
                out.println("<fieldset>");
                out.println("<legend>Purchase Record</legend>");
                
                // make connection to db and retrieve data from the table			
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db",
                                "aiad039", "aiad039");
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                ResultSet rs = stmt.executeQuery("SELECT * FROM [purchase] WHERE [uid]="+userBean.getUID());
                
                //print all purchase record
                out.println("<div><table style='width:100%'>");
                out.println("<thead>");
                out.println("<th align='left'>Purchas ID</th><th align='left'>Movie</th><th align='left'>Purchase Date</th><th align='left'>Action</th>");
                out.println("</thead>");
                out.println("<tbody>");
                
                int numRow = 0;
                if (rs != null && rs.last() != false) {
                    numRow = rs.getRow();
                    rs.beforeFirst();
                }
               
                if(numRow == 0){//if there is no record
                    out.println("<h3>You have no purchase record. <a href = ''>Go to buy ticket.</a></h3>");
                }else{
                    while (rs != null && rs.next() != false) {
                        int pid = rs.getInt("pid");
                        int sid = rs.getInt("sid");
                        String date = rs.getString("time");



                        out.println("<tr>");
                        out.println("<td>" + pid + "</td>");
                        out.println("<td>" + sid + "</td>");
                        out.println("<td>" + date + "</td>");

                        out.println("<td>");
                        out.println("<a href='" + request.getRequestURI() + "?action=refund&pid=" + pid + "'>Refund</a>");
                        out.println("</td>");
                        out.println("</tr>");

                    }
                    out.println("</fieldset>");
                }
                    
                
                
                  //get the current time
//                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                Date date = new Date();
//                String d = dateFormat.format(date);
            out.println("</fieldset>");
            

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

    private void showManagerHome(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        try {
            HttpSession session = request.getSession();
            User userBean = (User) session.getAttribute("user");
            
            //session.invalidate();
            out.println("<html>");
            out.println("<head>");
            out.println(" <title>Home Page</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Home Page</h1>");
            out.println("<fieldset>");
            out.println("<p>Welcome! Dear "
                    +userBean.getName() +"</p>");
            
            
            
            //print all purchase record
                out.println("<fieldset>");
                out.println("<legend>Manipulation</legend>");
                out.println("<a href='" + request.getRequestURI() + "?action=manageSeats'>Mange Seats</a>");
                out.println("<br><a href='" + request.getRequestURI() + "?action=scheduleMovie'>Manage Movie Schedule</a>");
                out.println("</fieldset>");
                
                  //get the current time
//                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                Date date = new Date();
//                String d = dateFormat.format(date);
            out.println("</fieldset>");

        } finally { 
            
            out.close();
        }
    }

    private void showOfficerHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        try {
            HttpSession session = request.getSession();
            User userBean = (User) session.getAttribute("user");
            
            //session.invalidate();
            out.println("<html>");
            out.println("<head>");
            out.println(" <title>Home Page</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Home Page</h1>");
            out.println("<fieldset>");
            out.println("<p>Welcome! Dear "
                    +userBean.getName() +"</p>");
            
            
            
            //print all purchase record
                out.println("<fieldset>");
                out.println("<legend>Request of refunds</legend>");
                
                // make connection to db and retrieve data from the table			
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db",
                                "aiad039", "aiad039");
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                ResultSet rs = stmt.executeQuery("SELECT * FROM [request] ");
                
                //print all purchase record
                out.println("<div><table style='width:100%'>");
                out.println("<thead>");
                out.println("<th align='left'>Request ID</th><th align='left'>Purchase</th><th align='left'>Status</th><th align='left'>Action</th>");
                out.println("</thead>");
                out.println("<tbody>");
                
                int numRow = 0;
                if (rs != null && rs.last() != false) {
                    numRow = rs.getRow();
                    rs.beforeFirst();
                }
               
                if(numRow == 0){//if there is no record
                    out.println("<h3>You have no purchase record. <a href = ''>Go to buy ticket.</a></h3>");
                }else{
                    while (rs != null && rs.next() != false) {
                        int rid = rs.getInt("rid");
                        int pid = rs.getInt("pid");
                        String status = rs.getString("status");
                        out.println("<tr>");
                        out.println("<td>" + rid + "</td>");
                        out.println("<td>" + pid + "</td>");
                        out.println("<td>" + status + "</td>");
                        out.println("<td>");
                        out.println("<a href='" + request.getRequestURI() + "?action=authorize&rid=" + rid + "'>process</a>");
                        out.println("</td>");
                        out.println("</tr>");

                    }
                    out.println("</fieldset>");
                }
                    
                
                
                  //get the current time
//                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                Date date = new Date();
//                String d = dateFormat.format(date);
            out.println("</fieldset>");
            

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

    private void doAuthorizeRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
               
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try{
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Authorize Refund</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Authorize Refund</h1>");
            out.println("<div style='width:600px'>");
          
            out.println("<fieldset>");
            
            String page = request.getParameter("page");
            String rid = request.getParameter("rid");
            
            
            if (page != null && !page.equalsIgnoreCase("") ) {

                out.println("<p>authorize or not</p>");
                String cmt = request.getParameter("cmt");
                String decs = request.getParameter("decs");
                

                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db",
                            "aiad039", "aiad039");
                // Create a preparedstatement to set the SQL statement			 
                PreparedStatement pstmt = con.prepareStatement("UPDATE [request]SET [Status] = (?), [comment] = (?) WHERE [rid] =(?)");
                
                pstmt.setString(1,decs);
                pstmt.setString(2,cmt);
                pstmt.setInt(3,Integer.parseInt(rid));

                int rows = pstmt.executeUpdate();

                if (rows>0) {
                    out.println("<legend>The requesr is successfuly authorized.</legend>");
                    out.println("<h3>You have "
                            + decs+" the request "
                                    + rid +".</h3>");
                }
                else {
                    out.println("<legend>ERROR: New record is failed to create.</legend>");
                }
               
                out.println("</fieldset>");
            }else {
                
                 // Register the JDBC driver, open a connection
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db",
                            "aiad039", "aiad039");
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
               
                // execute the SQL statement
                ResultSet rs = stmt.executeQuery("SELECT * FROM [request] WHERE [rid] ="+rid);
                
                int pid;
                if (rs != null && rs.last() != false) {
                    pid = rs.getInt("pid");
                    rs = stmt.executeQuery("SELECT * FROM [purchase] WHERE [pid] ="+pid);
                }
               
                //print the relative purchase record
                out.println("<h3>Do you want to refund this purchase?</h3>");
                out.println("<form method='POST' action='" + request.getRequestURI() + "'>");
                out.println("<input name='action' type='hidden' value='authorize' />");
                out.println("<input name='rid' type='hidden' value='"
                                   + rid+"' />");
                out.println("<input name='page' type='hidden' value='1' />");
                           
                        while(rs !=null &&rs.next()!=false){
                          
                           int apid = rs.getInt("pid");
                           int sid = rs.getInt("sid");
                           String date = rs.getString("time");
                           out.println("<tr>");
                           out.println("<td>" + rid + "</td>");
                           out.println("<td>" + apid + "</td>");
                           out.println("<td>" + sid + "</td>");
                           out.println("<td>" + date + "</td>");
                           out.println("</tr>");
                           
                       }
                out.println("<p>Decision<textarea name='cmt' rows='5' style='width:100%;'></textarea></p>");        
                out.println("<p>Comments<br/>Approve  <input type= 'radio' name= 'decs' value = 'Approved'><br>Reject  <input type= 'radio' name= 'decs' value = 'Rejected'></p>");
                
                out.println("<br><input type='submit' value='Authorize' />");
                out.println("</form>");
                //process it
                
                
               
                
            }
            out.println("<br/><a href='" + request.getRequestURI() + "'>Back to Home Page</a>");
            out.println("</fieldset>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            
        }catch(ClassNotFoundException e){
            out.println("<div style='color: red'>" + e.toString() + "</div>");
        }catch(SQLException e){
            out.println("<div style='color: red'>" + e.toString() + "</div>");
        }finally{
            out.close();
        }
    }

    private void doManageSeats(HttpServletRequest request, HttpServletResponse response) throws IOException {
       response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        try{
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Phonebook</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Phonebook (Delete)</h1>");
            out.println("<div style='width:600px'>");
            
            out.println("<fieldset>");
            String page = request.getParameter("page");
            String pid = request.getParameter("pid");
            
            
            if (page != null && !page.equalsIgnoreCase("") ) {

                // Register the JDBC driver, open a connection
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db",
                            "aiad039", "aiad039");

                // Create a preparedstatement to set the SQL statement			 
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO [request] ([PID], [Status]) VALUES (?, 'Waiting')");
                pstmt.setInt(1, Integer.parseInt(pid));
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows>0) {
                    out.println("<legend>The record is successfuly created.</legend>");
                    // display the information of the record just added including UID
                     Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    // execute the SQL statement
                    ResultSet rs = stmt.executeQuery("SELECT * FROM [request] WHERE [pid] ="+pid);
                   
                    if (rs != null && rs.next() != false) {
                            out.println("<p>UID: " + rs.getInt(1) + "</p>");
                            rs.close();
                    }
                    if (stmt != null) {
                            stmt.close();
                    }
                    out.println("<h3>We have accept your request!</h3>");
                    
                }
                else {
                    out.println("<legend>ERROR: New record is failed to create.</legend>");
                }
               
                
            }else {
                
                 // Register the JDBC driver, open a connection
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db",
                            "aiad039", "aiad039");
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
               
                // execute the SQL statement
                ResultSet rs = stmt.executeQuery("SELECT * FROM [request] WHERE [pid] ="+pid);
                int numRow = 0;
                if (rs != null && rs.last() != false) {
                    numRow = rs.getRow();
                    rs.beforeFirst();
                }
               
                if(numRow == 0){//if the record hasn't been refunded

                    rs = stmt.executeQuery("SELECT * FROM [purchase] WHERE [pid] ="+pid);
                    String method = "";
                    if (rs != null && rs.next() != false) {
                        method = rs.getString("PayMethod");
         
                            
                    }
                    if(method.equals("point")){
                        out.println("<h3>Sorry, You cannot refund purchase with points.</h3>");
                    }else{
                        out.println("<h3>Do you want to refund this purchase?</h3>");
                        while(rs !=null &&rs.next()!=false){
                           out.println("<form method='POST' action='" + request.getRequestURI() + "'>");
                           out.println("<input name='action' type='hidden' value='refund' />");
                           out.println("<input name='pid' type='hidden' value='"
                                   + pid+"' />");
                           out.println("<input name='page' type='hidden' value='1' />");
                           int sid = rs.getInt("sid");
                           String date = rs.getString("time");
                           out.println("<tr>");
                           out.println("<td>" + pid + "</td>");
                           out.println("<td>" + sid + "</td>");
                           out.println("<td>" + date + "</td>");
                           out.println("</tr>");
                           out.println("<br><input type='submit' value='Refund!' />");
                           out.println("</form>");
                       }
                    }
                    
                    
                }else{//if the record has already been refunded, show status
                 
                    while(rs !=null &&rs.next()!=false){
                    
                    int arid = rs.getInt("rid");
                    int executor = rs.getInt("UID");
                    out.println("<P>We have accept your request for refund"
                            + arid+ " on refund has already been approved by "
                            + executor +" </P>");
                }
                }
               
                
            }
            out.println("<br/><a href='" + request.getRequestURI() + "'>Back to Home Page</a>");
            out.println("</fieldset>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            
        }catch(ClassNotFoundException e){
            out.println("<div style='color: red'>" + e.toString() + "</div>");
        }catch(SQLException e){
            out.println("<div style='color: red'>" + e.toString() + "</div>");
        }finally{
            out.close();
        }
    }

    private void doScheduleMovie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Manage Movie Schedule</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Manage Movie Schedule</h1>");
            out.println("<div style='width:600px'>");
            
            out.println("<fieldset>");
            
            
            String page = request.getParameter("page");
            String pid = request.getParameter("pid");
            
            
            if (page != null && !page.equalsIgnoreCase("") ) {

            }else {
                // make connection to db and retrieve data from the table			
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db",
                                "aiad039", "aiad039");
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                ResultSet rs = stmt.executeQuery("SELECT * FROM [movie]");
            
                int numRow = 0;
                if (rs != null && rs.last() != false) {
                    numRow = rs.getRow();
                    rs.beforeFirst();
                }
                
                out.println("<p>Select movie<br/><select name='movie' style='width:100%;'>");
                if(numRow == 0){//if there is no record
                    out.println("<option><no movie></option>");
                }else{//
                    
                    while (rs != null && rs.next() != false) {
                        int mid = rs.getInt("mid");
                        String name = rs.getString("Name");
                        out.println("<option value = "
                                + mid+">"
                                + name+"</option>");
                    }
                }
                out.println("</select></p>");
                out.println("<p>Select venue<br/><select name='venue' style='width:100%;'>");
                out.println("<option>Hall A</option>");
                out.println("<option>Hall B</option>");
                out.println("</select></p>");
                
                out.println("<p>Select show time<br/><select name='time' style='width:100%;'>");
                out.println("<option>14:00~16:00</option>");
                out.println("<option>20:00~22:00</option>");
                out.println("</select></p>");
                
                out.println("<p>Please enter price: <input type='text' name='price' /></p>");
               
                out.println("<p><input type='submit' value='Create Schedule' /></p>");

                
            }
            out.println("<br/><a href='" + request.getRequestURI() + "'>Back to Home Page</a>");
            out.println("</fieldset>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            
        }catch(ClassNotFoundException e){
            out.println("<div style='color: red'>" + e.toString() + "</div>");
        }catch(SQLException e){
            out.println("<div style='color: red'>" + e.toString() + "</div>");
        }finally{
            out.close();
        }
    }




}
