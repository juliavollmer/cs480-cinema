<%-- 
    Document   : MovieIndex
    Created on : Apr 24, 2018, 1:46:22 PM
    Author     : jsvollmer2
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="cs4280.MovieDetail"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Movies</title>
        
    </head><jsp:include page="../include/header.jsp" />
    <body>
        <h1>Current Movies</h1>
        <div style='width:600px'>
            <fieldset>
                <legend>Movies now showing</legend>
                <div><table style='width:100%'>
                        <thead>
                        <th align='left'>Name</th><th align='left'>Cover</th><th align='left'>Details</th>
                        </thead>
                        <tbody>
                            <% List<MovieDetail> movies = (ArrayList<MovieDetail>) request.getAttribute("movies");
                                for (MovieDetail moviei : movies) {
                                    request.setAttribute("movie", moviei);
                            %>
                            <jsp:useBean id="movie" 
                                         type="cs4280.MovieDetail" 
                                         scope="request" />
                            <tr>
                                <td> <a href='./?action=details&mid=<jsp:getProperty name="movie" property="mid" />'><div class="container">
                                        <img src="css/images/<jsp:getProperty name='movie' property='cover' />" class="image">
                                        <div class="overlay"><jsp:getProperty name="movie" property="name" /></div>
                                    </div></a></td>
                            </tr>
                            <%
                                }

                            %>

                        </tbody>
                    </table></div>
            </fieldset>
        </div>
        <br>
        <jsp:include page="../include/statement.jsp" />                     
    </body>
</html>