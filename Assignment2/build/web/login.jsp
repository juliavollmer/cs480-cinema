<%-- 
    Document   : index
    Created on : Mar 22, 2018, 2:54:42 PM
    Author     : yanfewang3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CS4280 Cinema Log In Page</title>
    </head>
    <body>
        <%
           
           try{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad039_db",
                            "aiad039", "aiad039");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [phonebook] ORDER BY [Name] ASC");
           }catch(java.lang.ClassNotFoundException cnfe){
               
           }catch(SQLException sqlex){
               
           }
        %> 
        <table width = "75%" border = "0" align = "center">
            <tr>
                <td colspan="2"><div align="center"> <font size = 3 color = #FFFFFF face="Arial, Helvetica, sans-serif"><b>
                            Login Status</b></font> </div></td>

                            </tr>
            
        </table>
    </body>
</html>
