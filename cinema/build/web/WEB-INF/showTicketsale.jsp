<%-- 
    Document   : showTicketsale
    Created on : Apr 24, 2018, 5:02:26 PM
    Author     : jsvollmer2
--%>
<%@page import="java.util.*"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:useBean id="schedule" 
                     type="cs4280.Schedule" 
                     scope="request" />
        <jsp:useBean id="movie" 
                     type="cs4280.MovieDetail" 
                     scope="request" />
        <title>Purchase Ticket</title>
        <jsp:include page="../include/header.jsp" />
        <% String seat[] = (String[])request.getAttribute("seat");
        int totalseats = seat.length;
        String seats = "";
        for (String seat1 : seat){
            seats = seats +" "+ seat1;
        }
        float price = Float.parseFloat(schedule.getPrice());
        float total = price *totalseats;
      %>
    </head>
    <body><div class="layout">
        <h1><jsp:getProperty name="movie" property="name" /> at <jsp:getProperty name="schedule" property="time" /> on <jsp:getProperty name="schedule" property="date" /></h1>
        <br><fieldset>
            <p>Seats: <%= seats %></p>
            <p>Price per Ticket: <jsp:getProperty name="schedule" property="price" />HK$</p>
            <p>Total price: <%= total %>HK$</p>
        <form action="">
            <input name='action' type='hidden' value='buy' />
            <input name='sid' type='hidden' value='<jsp:getProperty name="schedule" property="sid" />'/>
            <%
            for (String seat1 : seat){
            %>
            <input name='seat' type='hidden' value='<%= seat1 %>'/><%
        }%>
            <input name='total' type='hidden' value='<%= total %>'/>
            
                   <select name="paymethod">
                <option value="VISA">VISA</option>           
                <option value="master">MasterCard</option>
                <option value="points">Loyalty Points</option>
            </select>

            <input type="SUBMIT" value="Proceed to Payment">
            <a href="Login.do">Login</a></form><br> </fieldset></div>
            <jsp:include page="../include/statement.jsp" />
    </body>
</html>
