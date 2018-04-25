<%-- 
    Document   : SeatingPlan
    Created on : Apr 15, 2018, 4:17:53 PM
    Author     : jsvollmer2
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="cs4280.Schedule"%>
<%@page import="cs4280.Seat"%>
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
        <title><jsp:getProperty name="movie" property="name" />: Seating Plan</title>
        
    </head><jsp:include page="../include/header.jsp" />
    <body><div class="layout">
        <h1><jsp:getProperty name="movie" property="name" />: Seating Plan</h1><br>
        <h3>Date: <jsp:getProperty name="schedule" property="date" /> Time: 
            <jsp:getProperty name="schedule" property="time" /> Price:
            <jsp:getProperty name="schedule" property="price" /> </h3>
        <form>
            <input name='action' type='hidden' value='buying' />

            <input name='sid' type='hidden' value='<jsp:getProperty name="schedule" property="sid" />' />
            <div style="width: 60%; margin: auto;"><table style='width:100%'>
                    <thead>
                    <th align='left'> Row</th> <th></th><th align='center'> Screen</th>
                    </thead>
                    <tbody>
                        <tr>
                            <% List<Seat> seats = (ArrayList) request.getAttribute("seats");
                                int row = 0;
                                for (Seat seating : seats) {
                                    request.setAttribute("seat", seating);
                                    int seid = Integer.parseInt(seating.getSeid());

                            %><jsp:useBean id="seat" 
                                           type="cs4280.Seat" 
                                           scope="request" />
                            <% if (seid % 4 == 1) {
                                    row++;
                            %>
                        </tr>
                        <tr>
                            <td><%= row%></td><% } %>

                            <td><input type='checkbox' name='seat' value='<jsp:getProperty name="seat" property="seid" />' <jsp:getProperty name="seat" property="value" />><jsp:getProperty name="seat" property="seid" /></td>


                            <% }%>
                    </tbody>
                </table></div>

            <br>
            <INPUT TYPE="SUBMIT" VALUE="Submit Order">
        </form><br/>
        </div></div> <br>
    <jsp:include page="../include/statement.jsp" />
</body>
</html>
