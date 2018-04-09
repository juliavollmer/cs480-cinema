package cs4280;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LogServlet extends HttpServlet {
   
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try{
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Cinema - Login</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Log In Page</h1>");
            out.println("<div style='width:600px'>");
            
            out.println("<fieldset>");
            String name = request.getParameter("name");
            String pwd = request.getParameter("pwd");
            
            
            if (name != null && !name.equalsIgnoreCase("") &&
                pwd != null && !pwd.equalsIgnoreCase("")) {

                // Register the JDBC driver, open a connection
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db",
                            "aiad039", "aiad039");
                // Create a preparedstatement to set the SQL statement			 
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery("SELECT * FROM [user] WHERE [Name] ='"+name+"'");

                int numRow = 0;
                if (rs != null && rs.last() != false) {
                    numRow = rs.getRow();
                    rs.beforeFirst();
                }
                if(numRow>0){
                        while (rs != null && rs.next() != false) {
                           int uid = rs.getInt("uid");
                           String username = rs.getString("name");
                           String password = rs.getString("password");
                           String actor = rs.getString("actor");
                       if(password.equals(pwd)){
                           out.println("<h3>Dear "
                                   + username+ ", Welcome!</h3>");
                           User userBean = new User();
                           userBean.setUID(uid);
                           userBean.setName(username);
                           userBean.setActor(actor);
                           request.getSession().setAttribute("user", userBean);
                           out.println("<br/><a href='home.do'>Go to home page!</a>");
                       }else{
                           out.println("<h3 color = 'red'>Invalid Password!</h3>");
                           
                       }
                    }
                }else{
                    out.println("<h3 color = 'red'>Invalid Username!</h3>");
                    
                }

            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
            
            
         
            out.println("</fieldset>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

                
            }
            else {
                if (name == null) {
                    name = "";
                }
                if (pwd == null) {
                    pwd = "";
                }				
                out.println("<legend>Please Input Your Username and Password</legend>");
                out.println("<form method='POST' action='" + request.getRequestURI() + "'>");
                out.println("<p>Name:");
                out.println("<input name='name' type='text' size='25' maxlength='255' value='" + name + "' /></p>");
                out.println("<p>Password:");
                out.println("<input name='pwd' type='password' size='25' maxlength='255' value='" + pwd + "' /></p>");
                
                
                out.println("<input type='submit' value='Log In' />");
                out.println("</form>");
                
            }
            out.println("<br/><a href='" + request.getRequestURI() + "'>Back to Phonebook Directory</a>");
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
